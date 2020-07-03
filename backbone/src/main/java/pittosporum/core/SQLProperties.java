package pittosporum.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.LinkedHashMap;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public abstract class SQLProperties implements Comparator<SQLProperties>, Comparable<SQLProperties> {
    abstract int getPriority();

    public static final String ASCENDING    = "ASC";
    public static final String DESCENDING   = "DESC";

    private String tableName;
    private String sql;
    private int pageSize;
    private int pageNo;
    private LinkedHashMap<String, String> params;
    private LinkedHashMap<String, Object> filter;

    @Override
    public int compare(SQLProperties o1, SQLProperties o2) {
        return o1.compareTo(o2);
    }

    @Override
    public int compareTo(SQLProperties o) {
        return this.getPriority() - o.getPriority();
    }
}
