package net.panda.web.support.fm;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import net.panda.core.security.SecurityUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FnSecGrant implements TemplateMethodModel {

    private final SecurityUtils securityUtils;

    @Autowired
    public FnSecGrant(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List list)
            throws TemplateModelException {
        // Checks
        Validate.notNull(list, "List of arguments is required");
        Validate.isTrue(list.size() == 3, "3 arguments are needed");
        // Input
        String category = (String) list.get(0);
        int id = Integer.parseInt((String) list.get(1), 10);
        String action = (String) list.get(2);
        // Test
        return securityUtils.isGranted(category, id, action);
    }

}
