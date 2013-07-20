package net.panda.backend;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import net.panda.backend.dao.AccountDao;
import net.panda.backend.dao.model.TAccount;
import net.panda.core.model.*;
import net.panda.core.security.SecurityRoles;
import net.panda.core.validation.AccountValidation;
import net.panda.core.validation.Validations;
import net.panda.service.AccountService;
import net.sf.jstring.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class AccountServiceImpl extends AbstractValidatorService implements AccountService {

    private final Strings strings;
    private final AccountDao accountDao;
    private final Function<TAccount, Account> accountFunction = new Function<TAccount, Account>() {
        @Override
        public Account apply(TAccount t) {
            if (t == null) {
                return null;
            } else {
                return new Account(
                        t.getId(),
                        t.getName(),
                        t.getFullName(),
                        t.getEmail(),
                        t.getRoleName(),
                        t.getMode(),
                        strings.getSupportedLocales().filterForLookup(t.getLocale())
                );
            }
        }
    };

    @Autowired
    public AccountServiceImpl(ValidatorService validatorService, Strings strings, AccountDao accountDao) {
        super(validatorService);
        this.strings = strings;
        this.accountDao = accountDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Account authenticate(String user, String password) {
        return accountFunction.apply(accountDao.findByNameAndPassword(user, password));
    }

    @Override
    @Transactional(readOnly = true)
    public String getRole(String mode, String user) {
        return accountDao.getRoleByModeAndName(mode, user);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(String mode, String user) {
        return accountFunction.apply(
                accountDao.findByModeAndName(mode, user)
        );
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(SecurityRoles.ADMINISTRATOR)
    public Account getAccount(int id) {
        return accountFunction.apply(
                accountDao.getByID(id)
        );
    }

    @Override
    @Transactional(readOnly = true)
    @Secured(SecurityRoles.ADMINISTRATOR)
    public List<Account> getAccounts() {
        return Lists.transform(
                accountDao.findAll(),
                accountFunction
        );
    }

    @Override
    @Transactional
    @Secured(SecurityRoles.ADMINISTRATOR)
    public Account createAccount(final AccountCreationForm form) {
        // Validation
        validate(form, AccountValidation.class);
        // Validation: role
        validate(form.getRoleName(),
                Validations.oneOf(SecurityRoles.ALL),
                "net.panda.core.model.Account.roleName.incorrect",
                StringUtils.join(SecurityRoles.ALL, ","));
        // Validation: mode
        // TODO Gets the list of modes from the registered services
        List<String> modes = Arrays.asList("builtin", "ldap");
        validate(form.getMode(),
                Validations.oneOf(modes),
                "net.panda.core.model.Account.mode.incorrect",
                StringUtils.join(modes, ","));
        // Validation: checks the password
        validate(form.getPassword(), new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return !"builtin".equals(form.getMode()) || StringUtils.isNotBlank(input);
            }
        }, "net.panda.core.model.Account.password.requiredForBuiltin");
        // OK
        return getAccount(
                accountDao.createAccount(
                        form.getName(),
                        form.getFullName(),
                        form.getEmail(),
                        form.getRoleName(),
                        form.getMode(),
                        form.getPassword()
                )
        );
    }

    @Override
    @Transactional
    @Secured(SecurityRoles.ADMINISTRATOR)
    public void deleteAccount(int id) {
        accountDao.deleteAccount(id);
    }

    @Override
    @Transactional
    @Secured(SecurityRoles.ADMINISTRATOR)
    public Account updateAccount(int id, AccountUpdateForm form) {
        // Updates the account
        accountDao.updateAccount(
                id,
                form.getName(),
                form.getFullName(),
                form.getEmail(),
                form.getRoleName()
        );
        // OK
        return getAccount(id);
    }

    @Override
    @Transactional
    @Secured(SecurityRoles.ADMINISTRATOR)
    public Ack changePassword(int id, PasswordChangeForm form) {
        // Gets the existing account
        Account account = getAccount(id);
        // Checks the mode
        if ("builtin".equals(account.getMode())) {
            // DAO
            return accountDao.changePassword(id, form.getOldPassword(), form.getNewPassword());
        } else {
            // Cannot change password in this case
            return Ack.NOK;
        }
    }

    @Override
    @Transactional
    @Secured(SecurityRoles.ADMINISTRATOR)
    public Ack changeEmail(int id, EmailChangeForm form) {
        // Gets the existing account
        Account account = getAccount(id);
        // Checks the mode
        if ("builtin".equals(account.getMode())) {
            // DAO
            return accountDao.changeEmail(id, form.getPassword(), form.getEmail());
        } else {
            // Cannot change password in this case
            return Ack.NOK;
        }
    }

    @Override
    @Transactional
    @Secured(SecurityRoles.ADMINISTRATOR)
    public Account resetPassword(int id, String password) {
        // Gets the existing account
        Account account = getAccount(id);
        // Checks the mode
        if ("builtin".equals(account.getMode())) {
            // DAO
            accountDao.resetPassword(id, password);
        }
        // OK
        return getAccount(id);
    }

    @Override
    @Transactional
    @Secured(SecurityRoles.ADMINISTRATOR)
    public Ack changeLanguage(int id, String lang) {
        return accountDao.changeLanguage(id, strings.getSupportedLocales().filterForLookup(new Locale(lang)));
    }
}
