package pittosporum.utils;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLExprUtils;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.mchange.v2.sql.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import pittosporum.exception.BaseRunException;

import java.text.ParseException;
import java.util.List;

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

}
