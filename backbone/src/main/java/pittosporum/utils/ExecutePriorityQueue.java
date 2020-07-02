package pittosporum.utils;

import pittosporum.core.Delete;
import pittosporum.core.Insert;
import pittosporum.core.SQLProperties;
import pittosporum.core.Update;

import java.util.PriorityQueue;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class ExecutePriorityQueue{



    public static void main(String[] args) {
        PriorityQueue<SQLProperties> priorityQueue = new PriorityQueue();
        Insert insert = new Insert();
        Update update = new Update();
        Delete delete = new Delete();

        priorityQueue.add(update);
        priorityQueue.add(insert);
        priorityQueue.add(delete);
        priorityQueue.add(insert);
        priorityQueue.add(update);


        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
    }
}
