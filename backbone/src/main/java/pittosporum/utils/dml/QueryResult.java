package pittosporum.utils.dml;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class QueryResult<T>{
    private List<T> result;
    private int rowCount;
}
