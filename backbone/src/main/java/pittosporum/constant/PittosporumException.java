package pittosporum.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
public class PittosporumException extends RuntimeException{
    @Getter
    @Setter
    private String errorMsgCode;

    public PittosporumException(String message) {
        super(message);
    }

    public PittosporumException(String message, String errorMsgCode) {
        super(message);
        this.errorMsgCode = errorMsgCode;
    }

    public PittosporumException(Throwable cause) {
        super(cause);
    }

    public PittosporumException(String message, Throwable cause) {
        super(message, cause);
    }
}
