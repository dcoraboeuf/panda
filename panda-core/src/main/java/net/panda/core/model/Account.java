package net.panda.core.model;

import lombok.Data;
import net.panda.core.security.SecurityRoles;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Data
public class Account {

	private final int id;
	private final String name;
	private final String fullName;
	private final String email;
	private final String roleName;
	private final String mode;
	private Locale locale;
	private Set<ACL> acls;

	public Account(int id, String name, String fullName, String email, String roleName, String mode, Locale locale) {
		this.id = id;
		this.name = name;
		this.fullName = fullName;
		this.email = email;
		this.roleName = roleName;
		this.mode = mode;
		this.locale = locale;
	}

	public boolean isGranted(String category, int id, String action) {
        return (SecurityRoles.ADMINISTRATOR.equals(roleName)) || (acls != null && acls.contains(new ACL(category, id, action)));
	}

	public Account withACL (String category, int id, String action) {
        if (acls == null) {
            acls = new HashSet<>();
        }
        acls.add(new ACL(category, id, action));
        return this;
    }
}
