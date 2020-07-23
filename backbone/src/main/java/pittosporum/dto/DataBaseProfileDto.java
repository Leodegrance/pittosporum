package pittosporum.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Getter
@Setter
public class DataBaseProfileDto implements Serializable {
    private static final long serialVersionUID = 3459506353179419904L;

    private Integer id;
    private String profileName;
}
