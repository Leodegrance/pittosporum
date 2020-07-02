package pittosporum.core;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class Delete implements SQLPriority{


    public int getPriority() {
        return priority;
    }

    private int priority = 1;
}
