package pittosporum.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public final class JDBCTemplateHelper {
    private JDBCTemplateHelper(){}

    public static JdbcTemplate getJdbcTemplateByDataSource(DriverManagerDataSource dataSource){
        log.info("current datasource ", dataSource);
        if (dataSource == null){
            return null;
        }
        return new JdbcTemplate(dataSource);
    }
}
