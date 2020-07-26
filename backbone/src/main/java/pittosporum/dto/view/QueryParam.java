package pittosporum.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@Getter@Setter
public class QueryParam<T> implements Serializable {

    public static final String ASCENDING    = "ASC";

    public static final String DESCENDING   = "DESC";

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Map<String, String> searchParams;

    private LinkedHashMap<String, Object> filterParams;

    private Set<String> sortFieldSet;

    private String sortType = ASCENDING;

    private String mainSql;

    private Class<T> entityClz;

    public void addFilter(String filterName, Object filterValue){
        if (filterParams == null){
            filterParams = new LinkedHashMap<>();
        }

        filterParams.put(filterName, filterValue);
    }

    public void addParam(String param, String value){
        if (searchParams == null){
            searchParams = new LinkedHashMap<>();
        }

        searchParams.put(param, value);
    }

    public void addSortField(String sortField){
        if (sortFieldSet == null){
            sortFieldSet = new HashSet<>();
        }

        sortFieldSet.add(sortField);
    }
}
