package pittosporum.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class ExecutePriorityQueue{
    private PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();

    //Execute SQL List
    @Setter
    @Getter
    private List<String> sqlList;

    private int size;

    public static void main(String[] args) {

    }

}
