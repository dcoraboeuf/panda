package net.panda.web.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import net.panda.core.model.*;
import net.panda.service.AccountService;
import net.panda.web.resource.Resource;
import net.panda.web.support.AbstractUIController;
import net.panda.web.support.ErrorHandler;
import net.sf.jstring.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class UIAdminController extends AbstractUIController {

    private final AccountService accountService;
    private final Function<Account, Resource<Account>> accountResourceStubFn = new Function<Account, Resource<Account>>() {
        @Override
        public Resource<Account> apply(Account o) {
            return new Resource<>(o)
                    .withLink(linkTo(methodOn(UIAdminController.class).accountGet(o.getId())).withSelfRel());
        }
    };
    private final Function<Account, Resource<Account>> accountResourceFn = new Function<Account, Resource<Account>>() {
        @Override
        public Resource<Account> apply(Account o) {
            return accountResourceStubFn.apply(o);
            // TODO GUI links
        }
    };

    @Autowired
    public UIAdminController(ErrorHandler errorHandler, Strings strings, AccountService accountService) {
        super(errorHandler, strings);
        this.accountService = accountService;
    }

    /**
     * List of users
     */
    @RequestMapping(value = "/ui/account/user", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Resource<AccountSummary>> accountUsers() {
        return Lists.transform(
                accountService.getUserAccounts(),
                new Function<AccountSummary, Resource<AccountSummary>>() {
                    @Override
                    public Resource<AccountSummary> apply(AccountSummary o) {
                        return new Resource<>(o);
                    }
                }
        );
    }

    @RequestMapping(value = "/ui/account/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Resource<Account> accountGet(@PathVariable int id) {
        return accountResourceFn.apply(accountService.getAccount(id));
    }

    @RequestMapping(value = "/ui/account", method = RequestMethod.POST)
    public
    @ResponseBody
    Resource<Account> accountCreate(@RequestBody AccountCreationForm form) {
        return accountResourceFn.apply(accountService.createAccount(form));
    }

    /**
     * Actual update of an account
     */
    @RequestMapping(value = "/ui/account/{id}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Resource<Account> accountUpdate(@PathVariable int id, @RequestBody AccountUpdateForm form) {
        return accountResourceFn.apply(accountService.updateAccount(id, form));
    }

    /**
     * Deleting an account
     */
    @RequestMapping(value = "/ui/account/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Resource<Account> accountDelete(@PathVariable int id) {
        Resource<Account> old = accountResourceStubFn.apply(accountService.getAccount(id));
        accountService.deleteAccount(id);
        return old;
    }

    /**
     * Password reset
     */
    @RequestMapping(value = "/ui/account/{id}/password", method = RequestMethod.PUT)
    public
    @ResponseBody
    Resource<Account> accountPasswordReset(@PathVariable int id, @RequestBody AccountPasswordResetForm form) {
        return accountResourceFn.apply(
                accountService.resetPassword(
                        id,
                        form.getPassword()
                )
        );
    }
}
