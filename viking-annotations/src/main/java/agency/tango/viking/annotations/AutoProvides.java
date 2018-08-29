package agency.tango.viking.annotations;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(value = METHOD)
public @interface AutoProvides {
  String value() default "";
}
