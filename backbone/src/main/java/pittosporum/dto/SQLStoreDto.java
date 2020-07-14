package pittosporum.dto;

import lombok.Getter;
import lombok.Setter;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;

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

    @Max(message = "field value cannot be greater than {&amp}", value = 100)
    @Min(message = "field value cannot be greater than {&amp}", value = 1)
    private Integer profileId;

    private String createBy;
}
