package net.jfdf.jfdf.mangement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionWithArgs {
   String name() default "<Not Named>";

   boolean isHidden() default false;

   String iconId() default "lapis_lazuli";

   String iconNbt() default "";
}
