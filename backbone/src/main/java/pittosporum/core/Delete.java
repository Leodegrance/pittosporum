package pittosporum.core;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class Delete extends SQLPriority{
    public int getPriority() {
        return priority;
    }

    private int priority = 1;
}
