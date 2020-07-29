package pittosporum.annotation;

import java.lang.annotation.*;

/**
 * @author yichen(graffitidef @ gmail.com)
 */
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface DisplayName {
    String value();
}
