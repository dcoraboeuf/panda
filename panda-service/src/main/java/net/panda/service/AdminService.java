package net.panda.service;

import net.panda.service.model.GeneralConfiguration;
import net.panda.service.model.LDAPConfiguration;
import net.panda.service.model.MailConfiguration;

public interface AdminService {

    GeneralConfiguration getGeneralConfiguration();

    void saveGeneralConfiguration(GeneralConfiguration configuration);

    LDAPConfiguration getLDAPConfiguration();

    void saveLDAPConfiguration(LDAPConfiguration configuration);

    MailConfiguration getMailConfiguration();

    void saveMailConfiguration(MailConfiguration configuration);
}
