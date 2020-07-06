package pittosporum.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public final class JDBCTemplateMapper {
    private JDBCTemplateMapper(){}

    private static final String path = "datasource/dataSourceInstance.xml";

    private static Map<String, JdbcTemplate> jdbcTemplateMap;

    public static void initJDBCTemplateMapper(List<String> list){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        if (applicationContext != null){

            for (String s : list){
                JdbcTemplate jdbcTemplate = (JdbcTemplate) applicationContext.getBean(s + "JdbcTemplate");
                jdbcTemplateMap.put(s, jdbcTemplate);
            }
        }
    }

    public static JdbcTemplate getJdbcTemplateByDataSource(ComboPooledDataSource dataSource){
        log.info("current datasource ", dataSource);
        if (dataSource == null){
            return null;
        }
        return new JdbcTemplate(dataSource);
    }
}
