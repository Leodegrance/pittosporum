package pittosporum.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class DataSourceManager {
    private static final String path = "datasource/dataSourceInstance.xml";

    private DataSourceManager(){}

    public static ComboPooledDataSource getDriverManagerDataSourceByName(String beanName){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
        if (applicationContext != null){
            return (ComboPooledDataSource ) applicationContext.getBean(beanName);
        }

        return null;
    }
}
