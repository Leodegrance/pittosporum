package pittosporum.core;

import lombok.Getter;
import lombok.Setter;
import pittosporum.entity.BaseEntity;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class DataBaseProfile extends BaseEntity {
    private Integer id;
    private String profileName;

    private List<SQLProperties> sqlProperties;
}
