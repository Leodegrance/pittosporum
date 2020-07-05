package pittosporum.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class SQLStoreDto {
    private Integer id;
    private String executeSql;
    private String executeResult;
    private Integer profileId;
    private String createBy;
}
