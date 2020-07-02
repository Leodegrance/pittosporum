package pittosporum.core;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class Update implements SQLPriority{
    public int getPriority() {
        return 0;
    }

    private int priority = 3;
}
