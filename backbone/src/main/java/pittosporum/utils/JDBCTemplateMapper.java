package pittosporum.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import pittosporum.constant.PittosporumException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public final class JDBCTemplateMapper {
    private JDBCTemplateMapper(){}

    private static final String JDBC_BEAN_NAME_SUFFIX = "JdbcTemplate";
    private static final String PATH = "dataSource.xml";

    private static Map<String, JdbcTemplate> jdbcTemplateMap = new HashMap<>();

    public static void initJDBCTemplateMapper(List<String> list){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(PATH);
        if (applicationContext != null){
            for (String s : list){
                String beanName = s + JDBC_BEAN_NAME_SUFFIX;
                log.error("Database is currently being initialized: " + s);
                JdbcTemplate jdbcTemplate = (JdbcTemplate) applicationContext.getBean(s + JDBC_BEAN_NAME_SUFFIX);
                jdbcTemplateMap.put(beanName, jdbcTemplate);
            }
        }
    }

    public static JdbcTemplate getJdbcTemplateByName (String name) throws PittosporumException{
        if (StringUtils.isEmpty(name)){
            throw new PittosporumException("can not find jdbc template by name", name);
        }

        return jdbcTemplateMap.get(name + JDBC_BEAN_NAME_SUFFIX);
    }
}
