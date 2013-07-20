package net.panda.web.controller;

import com.google.common.base.Function;
import net.panda.core.model.Account;
import net.panda.core.model.AccountCreationForm;
import net.panda.service.AccountService;
import net.panda.web.resource.Resource;
import net.panda.web.support.AbstractUIController;
import net.panda.web.support.ErrorHandler;
import net.sf.jstring.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class UIAdminController extends AbstractUIController {

    private final AccountService accountService;
    private final Function<Account, Resource<Account>> accountResourceFn = new Function<Account, Resource<Account>>() {
        @Override
        public Resource<Account> apply(Account o) {
            return new Resource<>(o)
                    .withLink(linkTo(methodOn(UIAdminController.class).accountGet(o.getId())).withSelfRel());
        }
    };

    @Autowired
    public UIAdminController(ErrorHandler errorHandler, Strings strings, AccountService accountService) {
        super(errorHandler, strings);
        this.accountService = accountService;
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
}
