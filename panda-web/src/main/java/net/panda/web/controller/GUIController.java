package net.panda.web.controller;

import net.panda.core.UserMessage;
import net.panda.core.security.SecurityUtils;
import net.panda.service.AdminService;
import net.panda.service.model.GeneralConfiguration;
import net.panda.service.model.LDAPConfiguration;
import net.panda.service.model.MailConfiguration;
import net.panda.web.support.AbstractGUIController;
import net.panda.web.support.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GUIController extends AbstractGUIController {

    private final SecurityUtils securityUtils;
    private final AdminService adminService;

    @Autowired
    public GUIController(ErrorHandler errorHandler, SecurityUtils securityUtils, AdminService adminService) {
        super(errorHandler);
        this.securityUtils = securityUtils;
        this.adminService = adminService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        ModelAndView model = new ModelAndView("settings");
        // Authorization
        securityUtils.checkIsAdmin();
        // Gets the LDAP configuration
        LDAPConfiguration configuration = adminService.getLDAPConfiguration();
        model.addObject("ldap", configuration);
        // Gets the mail configuration
        model.addObject("mail", adminService.getMailConfiguration());
        // Gets the general configuration
        model.addObject("general", adminService.getGeneralConfiguration());
        // TODO Gets the list of configuration extensions
        // OK
        return model;
    }

    /**
     * General settings
     */
    @RequestMapping(value = "/settings/general", method = RequestMethod.POST)
    public RedirectView general(GeneralConfiguration configuration, RedirectAttributes redirectAttributes) {
        // Saves the configuration
        adminService.saveGeneralConfiguration(configuration);
        // Success
        redirectAttributes.addFlashAttribute("message", UserMessage.success("settings.general.saved"));
        // OK
        return new RedirectView("/settings", true);
    }

    /**
     * LDAP settings
     */
    @RequestMapping(value = "/settings/ldap", method = RequestMethod.POST)
    public RedirectView ldap(LDAPConfiguration configuration, RedirectAttributes redirectAttributes) {
        // Saves the configuration
        adminService.saveLDAPConfiguration(configuration);
        // Success
        redirectAttributes.addFlashAttribute("message", UserMessage.success("ldap.saved"));
        // OK
        return new RedirectView("/settings", true);
    }

    /**
     * Mail settings
     */
    @RequestMapping(value = "/settings/mail", method = RequestMethod.POST)
    public RedirectView mail(MailConfiguration configuration, RedirectAttributes redirectAttributes) {
        // Saves the configuration
        adminService.saveMailConfiguration(configuration);
        // Success
        redirectAttributes.addFlashAttribute("message", UserMessage.success("mail.saved"));
        // OK
        return new RedirectView("/settings", true);
    }

    @RequestMapping(value = "/pipeline/{name:[a-zA-Z0-9_\\.]+}", method = RequestMethod.GET)
    public ModelAndView pipelineGet(@PathVariable String name) {
        // FIXME Pipeline page
        return new ModelAndView("pipeline");
    }

}
