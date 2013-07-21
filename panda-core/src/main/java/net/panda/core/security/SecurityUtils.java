package net.panda.core.security;

import net.panda.core.model.Account;

import java.util.concurrent.Callable;

public interface SecurityUtils {

    boolean isLogged();

    Account getCurrentAccount();

    int getCurrentAccountId();

    boolean isAdmin();

    boolean hasRole(String role);

    void checkIsAdmin();

    void checkIsLogged();

    <T> T asAdmin(Callable<T> call);

    boolean isGranted(String category, int id, String action);

    void checkGrant(String category, int id, String action);
}
