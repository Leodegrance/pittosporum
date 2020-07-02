package pittosporum.core;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class Update extends SQLPriority{
    public int getPriority() {
        return priority;
    }

    private int priority = 3;
}
