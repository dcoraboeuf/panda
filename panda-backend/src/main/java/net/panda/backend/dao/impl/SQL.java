package net.panda.backend.dao.impl;

public interface SQL {

    // Pipeline
    String PIPELINE_ALL = "SELECT * FROM PIPELINE ORDER BY NAME";
    String PIPELINE_BY_ID = "SELECT * FROM PIPELINE WHERE ID = :id";
    String PIPELINE_BY_NAME = "SELECT * FROM PIPELINE WHERE NAME = :name";
    String PIPELINE_CREATE = "INSERT INTO PIPELINE (NAME, DESCRIPTION) VALUES (:name, :description)";
    // Configuration
    String CONFIGURATION_GET = "SELECT VALUE FROM CONFIGURATION WHERE NAME = :name";
    String CONFIGURATION_DELETE = "DELETE FROM CONFIGURATION WHERE NAME = :name";
    String CONFIGURATION_INSERT = "INSERT INTO CONFIGURATION (NAME, VALUE) VALUES (:name, :value)";
    // Accounts
    String ACCOUNT_AUTHENTICATE = "SELECT ID, NAME, FULLNAME, EMAIL, ROLENAME, MODE, LOCALE FROM ACCOUNTS WHERE MODE = 'builtin' AND NAME = :user AND PASSWORD = :password";
    String ACCOUNT_ROLE = "SELECT ROLENAME FROM ACCOUNTS WHERE MODE = :mode AND NAME = :user";
    String ACCOUNT_BY_NAME = "SELECT ID, NAME, FULLNAME, EMAIL, ROLENAME, MODE, LOCALE FROM ACCOUNTS WHERE MODE = :mode AND NAME = :user";
    String ACCOUNT = "SELECT * FROM ACCOUNTS WHERE ID = :id";
    String ACCOUNT_LIST = "SELECT ID, NAME, FULLNAME, EMAIL, ROLENAME, MODE, LOCALE FROM ACCOUNTS ORDER BY NAME";
    String ACCOUNT_USER_LIST = "SELECT ID, NAME, FULLNAME, EMAIL, ROLENAME, MODE, LOCALE FROM ACCOUNTS WHERE ROLENAME = 'ROLE_USER' ORDER BY NAME";
    String ACCOUNT_CREATE = "INSERT INTO ACCOUNTS (NAME, FULLNAME, EMAIL, ROLENAME, MODE, PASSWORD) VALUES (:name, :fullName, :email, :roleName, :mode, :password)";
    String ACCOUNT_DELETE = "DELETE FROM ACCOUNTS WHERE ID = :id";
    String ACCOUNT_UPDATE = "UPDATE ACCOUNTS SET NAME = :name, FULLNAME = :fullName, EMAIL = :email, ROLENAME = :roleName WHERE ID = :id";
    String ACCOUNT_CHANGE_PASSWORD = "UPDATE ACCOUNTS SET PASSWORD = :newPassword WHERE ID = :id AND MODE = 'builtin' AND PASSWORD = :oldPassword";
    String ACCOUNT_RESET_PASSWORD = "UPDATE ACCOUNTS SET PASSWORD = :password WHERE ID = :id AND MODE = 'builtin'";
    String ACCOUNT_CHANGE_EMAIL = "UPDATE ACCOUNTS SET EMAIL = :email WHERE ID = :id AND MODE = 'builtin' AND PASSWORD = :password";
    String ACCOUNT_CHANGE_LOCALE = "UPDATE ACCOUNTS SET LOCALE = :locale WHERE ID = :id";
    // Parameters
    String PARAMETER_BY_PIPELINE = "SELECT * FROM PARAMETER WHERE PIPELINE = :pipeline ORDER BY NAME ASC";
    String PARAMETER_BY_ID = "SELECT * FROM PARAMETER WHERE ID = :id";
    String PARAMETER_CREATE = "INSERT INTO PARAMETER (PIPELINE, NAME, DESCRIPTION, DEFAULT_VALUE, OVERRIDDABLE) VALUES (:pipeline, :name, :description, :defaultValue, :overriddable)";
    String PARAMETER_UPDATE = "UPDATE PARAMETER SET NAME = :name, DESCRIPTION = :description, DEFAULT_VALUE = :defaultValue, OVERRIDDABLE = :overriddable WHERE ID = :parameter";
    String PARAMETER_DELETE = "DELETE FROM PARAMETER WHERE ID = :parameter";
    // Pipeline authorizations
    String PIPELINE_AUTHORIZATION_DELETE = "DELETE FROM PIPELINE_AUTHORIZATION WHERE PIPELINE = :pipeline AND ACCOUNT = :account";
    String PIPELINE_AUTHORIZATION_INSERT = "INSERT INTO PIPELINE_AUTHORIZATION (PIPELINE, ACCOUNT, ROLE) VALUES (:pipeline, :account, :role)";
    String PIPELINE_AUTHORIZATION_BY_PIPELINE = "SELECT * FROM PIPELINE_AUTHORIZATION WHERE PIPELINE = :pipeline";
    String PIPELINE_AUTHORIZATION_BY_ACCOUNT = "SELECT * FROM PIPELINE_AUTHORIZATION WHERE ACCOUNT = :account";
    // Branches
    String BRANCH_CREATE = "INSERT INTO BRANCH (PIPELINE, NAME, DESCRIPTION) VALUES (:pipeline, :name, :description)";
    String BRANCH_BY_ID = "SELECT * FROM BRANCH WHERE ID = :id";
    String BRANCH_BY_PIPELINE = "SELECT * FROM BRANCH WHERE PIPELINE = :pipeline ORDER BY NAME ASC";
    // Branch parameters
    String BRANCH_PARAMETER_BY_BRANCH = "SELECT * FROM BRANCH_PARAMETER WHERE BRANCH = :branch";
    String BRANCH_PARAMETER_BY_BRANCH_AND_PARAMETER = "SELECT * FROM BRANCH_PARAMETER WHERE BRANCH = :branch AND PARAMETER = :parameter";
    String BRANCH_PARAMETER_DELETE = "DELETE FROM BRANCH_PARAMETER WHERE BRANCH = :branch AND PARAMETER = :parameter";
    String BRANCH_PARAMETER_INSERT = "INSERT INTO BRANCH_PARAMETER (BRANCH, PARAMETER, VALUE) VALUES (:branch, :parameter, :value)";
    // Instances
    String INSTANCE_CREATE = "INSERT INTO INSTANCE (PIPELINE, PARAMETERS, STATUS, RESULT) VALUES (:pipeline, :parameters, 'CREATED', 'NONE')";
}
