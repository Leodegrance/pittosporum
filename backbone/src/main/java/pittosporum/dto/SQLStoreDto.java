package pittosporum.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class SQLStoreDto implements Serializable {
    private static final long serialVersionUID = -231417890577521615L;

    private Integer id;
    private String executeSql;
    private String executeResult;
    private Integer profileId;
    private String createBy;
}
