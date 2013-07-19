package net.panda.backend.security;

import net.panda.core.security.SecurityRoles;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class RunAsAdminAuthentication extends AbstractAuthenticationToken {

    public RunAsAdminAuthentication() {
        super(AuthorityUtils.createAuthorityList(SecurityRoles.ADMINISTRATOR));
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
