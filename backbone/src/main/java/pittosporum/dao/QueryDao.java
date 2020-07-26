package pittosporum.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pittosporum.dto.view.QueryParam;
import pittosporum.dto.view.QueryResult;
import pittosporum.utils.CommonUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Repository
@Slf4j
public class QueryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <T> QueryResult<T> query(QueryParam<T> queryParam){
        if (jdbcTemplate == null || queryParam == null){
            return null;
        }

        QueryResult<T> result = new QueryResult();

        String whereSql = getQuerySql(queryParam);

        String countSql = getCountSql(whereSql);

        String limitSql = getLimitSql(queryParam);

        String orderBySql = getOrderBySql(queryParam);

        int count = jdbcTemplate.queryForObject(countSql, Integer.TYPE);

        StringBuilder mainSql = new StringBuilder();
        mainSql.append("(").append(whereSql).append(limitSql).append(")").append(orderBySql);
        log.info("query sql " + mainSql);

        LinkedHashMap<String, Object> filterParams = queryParam.getFilterParams();

        List<T> list;
        if (!CommonUtil.isEmpty(filterParams)){
            Object[] paramArray = toValueArray(filterParams);
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
            int itemNo = pageNo == 1 ? 1 : pageNo * pageSize;
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

    /**
     * use for specify parameter of sql
     * @param filterParams
     * @return
     */
    private Object[] toValueArray(LinkedHashMap<String, Object> filterParams){
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
