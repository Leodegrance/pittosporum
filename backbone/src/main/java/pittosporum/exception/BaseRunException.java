package pittosporum.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class BaseRunException extends RuntimeException{
    @Getter
    @Setter
    private String errorMsgCode;

    public BaseRunException(String message) {
        super(message);
    }

    public BaseRunException(String message, String errorMsgCode) {
        super(message);
        this.errorMsgCode = errorMsgCode;
    }

    public BaseRunException(Throwable cause) {
        super(cause);
    }

    public BaseRunException(String message, Throwable cause) {
        super(message, cause);
    }
}
