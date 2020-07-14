package pittosporum.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Setter
@Getter
public class UserDto {
    Integer id;
    String name;
    String email;
    Integer mobileNumber;
}
