package com.pittosporum.utils;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The class encapsulate SQL validation method of alibaba druid
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public final class SQLValidator {
    private static final String MYSQL_DB_TYPE = "mysql";
    private SQLValidator(){}

    public static List<SQLStatement> format(String sql){
        List<SQLStatement> statementList;
        SQLStatementParser parser;
        try {
            parser = SQLParserUtils.createSQLStatementParser(sql, MYSQL_DB_TYPE);
            statementList = parser.parseStatementList();
        } catch (ParserException e) {
            throw e;
        }

        return statementList;
    }

    public static String getTableName(String sql){
        SQLStatement statement;
        try {
            SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, MYSQL_DB_TYPE);
            statement = parser.parseStatement();
        } catch (ParserException e) {
            throw e;
        }

        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        statement.accept(visitor);

        Map<TableStat.Name, TableStat> nameTableStatMap =  visitor.getTables();
        List<String> tableList = nameTableStatMap.entrySet().stream().map(Map.Entry::getKey).map(TableStat.Name::getName).collect(Collectors.toList());
        if (CommonUtil.isEmpty(tableList)){
            return null;
        }

        return tableList.get(0);
    }

    public static String subConditions(String sql) {
        if (StringUtils.isEmpty(sql)){
            return "";
        }

        int i = sql.indexOf("where");
        int j = sql.indexOf("WHERE");

        if (i != -1){
            return sql.substring(i);
        }

        if (j != -1){
            return sql.substring(j);
        }
        return "";
    }

}
