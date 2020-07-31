package com.pittosporum.xmlsql;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class XmlSQLTemplate {
    private String catalog;
    private String sqlKey;
    private String sql;
}
