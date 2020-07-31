package com.pittosporum.dto.view;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class QueryResult<T> implements Serializable {
    private static final long serialVersionUID = 488639237359902356L;

    private List<T> result;
    private int rowCount;
}
