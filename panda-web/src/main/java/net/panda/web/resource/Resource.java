package net.panda.web.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
@EqualsAndHashCode(callSuper = false)
public class Resource<T> extends ResourceSupport {

    public static final String REL_GUI = "gui";

    private final T data;

    public Resource<T> withLink(Link link) {
        add(link);
        return this;
    }

}
