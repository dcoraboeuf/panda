package net.panda.web.support;

import lombok.Data;
import net.panda.core.UserMessageType;

@Data
public class Alert {

    private final UserMessageType type;
    private final String message;

}
