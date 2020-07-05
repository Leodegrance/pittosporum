package pittosporum.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public final class JDBCTemplateHelper {
    private JDBCTemplateHelper(){}

    public static JdbcTemplate getJdbcTemplateByDataSource(ComboPooledDataSource dataSource){
        log.info("current datasource ", dataSource);
        if (dataSource == null){
            return null;
        }
        return new JdbcTemplate(dataSource);
    }
}
