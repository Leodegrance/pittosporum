package pittosporum.core;

import java.util.Comparator;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public abstract class SQLPriority implements Comparator<SQLPriority>, Comparable<SQLPriority> {
    abstract int getPriority();

    @Override
    public int compare(SQLPriority o1, SQLPriority o2) {
        return o1.compareTo(o2);
    }

    @Override
    public int compareTo(SQLPriority o) {
        return this.getPriority() - o.getPriority();
    }
}
