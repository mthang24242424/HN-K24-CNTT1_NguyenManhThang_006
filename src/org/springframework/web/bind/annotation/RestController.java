package org.springframework.web.bind.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@org.springframework.stereotype.Component
public @interface RestController {
    String value() default "";
}
