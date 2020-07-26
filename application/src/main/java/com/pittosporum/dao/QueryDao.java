package com.pittosporum.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pittosporum.utils.CommonUtil;
import pittosporum.utils.dml.QueryParam;
import pittosporum.utils.dml.QueryResult;

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
    public <T> QueryResult<T> query(JdbcTemplate appJdbcTemplate, QueryParam<T> queryParam){
        if (appJdbcTemplate == null || queryParam == null){
            return null;
        }

        String mainSql = getQuerySql(queryParam);

        LinkedHashMap<String, Object> filterParams = queryParam.getFilterParams();

        List<T> result = appJdbcTemplate.query(mainSql, new BeanPropertyRowMapper<>(queryParam.getEntityClz()), toValueArray(filterParams));

        QueryResult<T> queryResult = new QueryResult();
        queryResult.setResult(result);
        queryResult.setRowCount(result.size());
        return queryResult;
    }

    private static String getQuerySql(QueryParam queryParam){
        if (queryParam == null){
            return null;
        }

        StringBuilder rawSqlStat = new StringBuilder("(");
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

        int pageNo = queryParam.getPageNo();
        int pageSize = queryParam.getPageSize();

        String limitSql = "";
        int fromIndex = rawSqlStat.indexOf("from") == -1 ? rawSqlStat.indexOf("FROM") : rawSqlStat.indexOf("from");
        if (fromIndex > -1){
            String tableName = rawSqlStat.substring(fromIndex).trim();
            StringBuilder stb = new StringBuilder();
            stb.append(" AND ID >= ( SELECT ID ");
            stb.append(tableName);
            stb.append(" ").append("LIMIT").append(" ").append(pageNo).append(",")
                    .append(" ").append("1").append(")").append(" ").append("LIMIT").append(" ").append(pageSize);
            limitSql = stb.toString();
        }

        rawSqlStat.append(limitSql).append(") ").append(orderSql);
        return rawSqlStat.toString();
    }

    public Object[] toValueArray(LinkedHashMap<String, Object> filterParams){
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
}
