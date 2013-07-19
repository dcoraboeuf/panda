package net.panda.web.controller;

import net.panda.web.security.CallbackAuthenticationSuccessHandler;
import net.panda.web.support.AbstractGUIController;
import net.panda.web.support.ErrorHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController extends AbstractGUIController {

    @Autowired
    public LoginController(ErrorHandler errorHandler) {
        super(errorHandler);
    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String callbackUrl, Model model) {
        if (StringUtils.isNotBlank(callbackUrl)) {
            model.addAttribute(CallbackAuthenticationSuccessHandler.CALLBACK_URL, callbackUrl);
        }
        return "login";
    }

}
