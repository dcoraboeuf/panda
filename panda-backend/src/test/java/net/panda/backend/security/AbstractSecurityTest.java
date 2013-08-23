package net.panda.backend.security;

import net.panda.core.model.Account;
import net.panda.core.model.ParameterSummary;
import net.panda.core.security.SecurityRoles;
import net.panda.service.security.PipelineFunction;
import net.panda.test.AbstractIntegrationTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ContextConfiguration;

import java.util.Locale;
import java.util.concurrent.Callable;

@ContextConfiguration({ "classpath:META-INF/backend-security-aop.xml" })
public abstract class AbstractSecurityTest extends AbstractIntegrationTest {

    protected static interface ContextCall {

        <T> T call(Callable<T> call) throws Exception;
    }

    protected static abstract class AbstractContextCall implements ContextCall {

        @Override
        public <T> T call(Callable<T> call) throws Exception {
            // Gets the current context
            SecurityContext oldContext = SecurityContextHolder.getContext();
            try {
                // Sets the new context
                contextSetup();
                // Call
                return call.call();
            } catch (Exception e) {
                throw e;
            } finally {
                // Restores the context
                SecurityContextHolder.setContext(oldContext);
            }
        }

        protected abstract void contextSetup();
    }

    protected static class UserCall extends AbstractContextCall {

        public UserCall withPipelineGrant(int id, PipelineFunction fn) {
            // FIXME ACL
            return this;
        }

        @Override
        protected void contextSetup() {
            SecurityContext context = new SecurityContextImpl();
            Authentication authentication = new AccountAuthentication(new Account(1, "user", "Normal user",
                    "user@test.com", SecurityRoles.USER, "builtin", Locale.ENGLISH));
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
    }

	protected <T> T asAnonymous(Callable<T> call) throws Exception {
		return withContext(call, new Runnable() {
			@Override
			public void run() {
				SecurityContextHolder.clearContext();
			}
		});
	}

    protected UserCall asUser() {
        return new UserCall();
    }

	protected <T> T asAdmin(Callable<T> call) throws Exception {
		return withContext(call, new Runnable() {
			@Override
			public void run() {
				SecurityContext context = new SecurityContextImpl();
				Authentication authentication = new AccountAuthentication(new Account(0, "admin", "Administrator",
						"admin@test.com", SecurityRoles.ADMINISTRATOR, "builtin", Locale.ENGLISH));
				context.setAuthentication(authentication);
				SecurityContextHolder.setContext(context);
			}
		});
	}

	protected <T> T withContext(Callable<T> call, Runnable contextSetup) throws Exception {
		// Gets the current context
		SecurityContext oldContext = SecurityContextHolder.getContext();
		try {
			// Sets the new context
			contextSetup.run();
			// Call
			return call.call();
		} catch (Exception e) {
			throw e;
		} finally {
			// Restores the context
			SecurityContextHolder.setContext(oldContext);
		}
	}

}
