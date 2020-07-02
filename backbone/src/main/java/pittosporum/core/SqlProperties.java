package pittosporum.core;

import java.util.LinkedHashMap;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public abstract class SqlProperties {
    public static final String ASCENDING    = "ASC";
    public static final String DESCENDING   = "DESC";

    private String tableName;
    private String sql;
    private int pageSize;
    private int pageNo;
    private LinkedHashMap<String, String> params;
    private LinkedHashMap<String, Object> filter;
}
