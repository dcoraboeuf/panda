package net.panda.core.model;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@Data
public class BranchParameter {

    private final ParameterSummary def;
    private final String value;

    public String getActualValue() {
        if (StringUtils.isNotBlank(value)) {
            return value;
        } else {
            return ObjectUtils.toString(def.getDefaultValue(), "");
        }
    }

}
