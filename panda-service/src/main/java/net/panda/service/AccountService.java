package net.panda.service;

import net.panda.core.model.*;

import java.util.List;

public interface AccountService {

    Account authenticate(String user, String password);

    String getRole(String mode, String user);

    Account getAccount(String mode, String user);

    Account getAccount(int id);

    List<Account> getAccounts();

    ID createAccount(AccountCreationForm form);

    void deleteAccount(int id);

    void updateAccount(int id, AccountUpdateForm form);

    Ack changePassword(int id, PasswordChangeForm form);

    Ack changeEmail(int accountId, EmailChangeForm form);

    Ack resetPassword(int id, String password);

    Ack changeLanguage(int id, String lang);
}
