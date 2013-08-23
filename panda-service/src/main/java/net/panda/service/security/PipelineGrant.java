package net.panda.service.security;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PipelineGrant {

    public PipelineFunction value();

}
