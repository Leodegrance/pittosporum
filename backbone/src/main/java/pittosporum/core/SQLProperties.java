package pittosporum.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public abstract class SQLProperties implements Comparator<SQLProperties>, Comparable<SQLProperties> {
    abstract int getPriority();

    private Integer storeId;
    private Integer profileId;
    private String profileName;
    private String sql;

    @Override
    public int compare(SQLProperties o1, SQLProperties o2) {
        return o1.compareTo(o2);
    }

    @Override
    public int compareTo(SQLProperties o) {
        return this.getPriority() - o.getPriority();
    }
}
