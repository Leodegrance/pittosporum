package com.pittosporum.dao;

import com.pittosporum.dto.view.QueryParam;
import com.pittosporum.dto.view.QueryResult;
import com.pittosporum.utils.CommonUtil;
import com.pittosporum.xmlsql.XmlSQLMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@Repository
@Slf4j
public class RepositoryHelper {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static String getSql(String catalog, String key){
        return XmlSQLMapper.receiveSql(catalog, key);
    }

    public <T> List<T> queryForList(String sql, Class<T> clz, @Nullable Object... args){
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(clz), args);
    }

    public <T> T queryForObject(String sql, Class<T> clz, @Nullable Object... args){
        List<T> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(clz), args);
        return CommonUtil.isEmpty(list) ? null : list.get(0);
    }

    public <T> T querySimpleObject(String sql, Class<T> clz, @Nullable Object... args){
        return jdbcTemplate.queryForObject(sql, clz, args);
    }

    public int update(String sql, @Nullable Object... args){
        return jdbcTemplate.update(sql, args);
    }

    /**
     * use for paging
     * @param queryParam
     * @param <T>
     * @return
     */
    public <T> QueryResult<T> query(QueryParam<T> queryParam){
        if (jdbcTemplate == null || queryParam == null){
            return null;
        }

        LinkedHashMap<String, Object> filterParams = queryParam.getFilterParams();
        Object[] paramArray = null;
        if (!CommonUtil.isEmpty(filterParams)) {
            paramArray = toValueArray2(filterParams);
        }

        QueryResult<T> result = new QueryResult();

        String whereSql = getQuerySql(queryParam);

        String countSql = getCountSql(whereSql);

        queryParam.setMainSql(whereSql);

        String limitSql = getLimitSql(queryParam);

        String orderBySql = getOrderBySql(queryParam);

        int count;
        if (CommonUtil.isEmpty(filterParams)){
            count = jdbcTemplate.queryForObject(countSql, Integer.TYPE);
        }else {
            count = jdbcTemplate.queryForObject(countSql, Integer.TYPE, toValueArray(filterParams));
        }

        StringBuilder mainSql = new StringBuilder();
        mainSql.append("(").append(whereSql).append(limitSql).append(")").append(orderBySql);
        log.info("query sql " + mainSql);


        List<T> list;
        if (paramArray != null){
            list = jdbcTemplate.query(mainSql.toString(), new BeanPropertyRowMapper<>(queryParam.getEntityClz()), paramArray);
        }else {
            list = jdbcTemplate.query(mainSql.toString(), new BeanPropertyRowMapper<>(queryParam.getEntityClz()));
        }

        result.setResult(list);
        result.setRowCount(count);
        return result;
    }

    private String getQuerySql(QueryParam queryParam){
        if (queryParam == null){
            return null;
        }

        StringBuilder rawSqlStat = new StringBuilder();
        rawSqlStat.append(queryParam.getMainSql());
        int whIndex = rawSqlStat.indexOf("where") == -1 ? rawSqlStat.indexOf("WHERE") : rawSqlStat.indexOf("where");
        if (whIndex == -1){
            rawSqlStat.append(" WHERE 1 = 1");
        }

        String whereSql = "";
        LinkedHashMap<String, Object> filter = queryParam.getFilterParams();
        if (!CommonUtil.isEmpty(filter)){
            StringBuilder stb = new StringBuilder();
            for (Map.Entry<String, Object> entry : filter.entrySet()){
                stb.append(" AND").append(" ").append(entry.getKey()).append(" = ").append("?");
            }
            whereSql = stb.toString();
        }

        rawSqlStat.append(whereSql);
        return rawSqlStat.toString();
    }


    private String getLimitSql(QueryParam queryParam){
        if (queryParam == null){
            return null;
        }

        StringBuilder rawSqlStat = new StringBuilder();
        rawSqlStat.append(queryParam.getMainSql());
        int pageNo = queryParam.getPageNo();
        int pageSize = queryParam.getPageSize();

        String limitSql = "";
        int fromIndex = rawSqlStat.indexOf("from") == -1 ? rawSqlStat.indexOf("FROM") : rawSqlStat.indexOf("from");
        if (fromIndex > -1){
            String tableName = rawSqlStat.substring(fromIndex).trim();
            StringBuilder stb = new StringBuilder();
            stb.append(" AND ID >= ( SELECT ID ");
            stb.append(tableName);
            int itemNo = (pageNo - 1 ) * pageSize;
            stb.append(" ").append("LIMIT").append(" ").append(itemNo).append(",")
                    .append(" ").append("1").append(")").append(" ").append("LIMIT").append(" ").append(pageSize);
            limitSql = stb.toString();
        }

        return limitSql;
    }

    private String getOrderBySql(QueryParam queryParam){
        if (queryParam == null){
            return null;
        }

        Set<String> sortFieldSet = queryParam.getSortFieldSet();
        String sortType = queryParam.getSortType();
        String orderSql = "";
        if (!CommonUtil.isEmpty(sortFieldSet)) {
            StringBuilder stb = new StringBuilder();
            stb.append(" ORDER BY ");
            for (String field : sortFieldSet){
                stb.append(field).append(' ').append(',');
            }

            orderSql = stb.substring(0, stb.length() - 1) + sortType;
        }
        return orderSql;
    }


    private Object[] toValueArray(LinkedHashMap<String, Object> filterParams){
        Object[] array = new Object[filterParams.size()];
        int j = 0;

        for (Map.Entry<String, Object> entry : filterParams.entrySet()){
            array[j++] = entry.getValue();
        }
        return array;
    }

    /**
     * use for specify parameter of sql
     * @param filterParams
     * @return
     */
    private Object[] toValueArray2(LinkedHashMap<String, Object> filterParams){
        Object[] array = new Object[filterParams.size() * 2];
        Object[] array2 = new Object[filterParams.size()];
        int i = 0;
        int j = 0;
        for (Map.Entry<String, Object> entry : filterParams.entrySet()){
            array[i++] = entry.getValue();
            array2[j++] = entry.getValue();
        }

        System.arraycopy(array2, 0, array, array.length / 2, array2.length);
        return array;
    }

    public String getCountSql(String mainSql){
        return "SELECT COUNT(*) FROM (" + mainSql + ") b";
    }
}
