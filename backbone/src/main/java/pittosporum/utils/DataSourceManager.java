package pittosporum.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public final class DataSourceManager {
    private static final String path = "datasource/dataSourceInstance.xml";

    private DataSourceManager(){}

    public static DriverManagerDataSource getDriverManagerDataSourceByName(String beanName){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        if (applicationContext != null){
            return (DriverManagerDataSource) applicationContext.getBean(beanName);
        }

        return null;
    }
}
