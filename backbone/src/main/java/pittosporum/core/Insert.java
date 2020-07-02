package pittosporum.core;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class Insert extends SQLPriority {
    public int getPriority() {
        return priority;
    }

    private int priority = 2;
}
