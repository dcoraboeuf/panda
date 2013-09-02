-- Initial schema

-- Versionning
CREATE TABLE DBVERSION (
  VALUE INTEGER NOT NULL,
  UPDATED TIMESTAMP NOT NULL
);

-- Configuration table
CREATE TABLE CONFIGURATION (
	NAME VARCHAR(40) NOT NULL,
	VALUE VARCHAR(200) NOT NULL,
	CONSTRAINT PK_CONFIGURATION PRIMARY KEY (NAME)
);

-- Accounts
CREATE TABLE ACCOUNTS (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(16) NOT NULL,
	FULLNAME VARCHAR(40) NOT NULL,
	EMAIL VARCHAR(80) NOT NULL,
	ROLENAME VARCHAR(40) NOT NULL,
	MODE VARCHAR(10) NOT NULL,
	PASSWORD VARCHAR(140) NULL,
	LOCALE VARCHAR(6) NULL,
	CONSTRAINT PK_ACCOUNTS PRIMARY KEY (ID),
	CONSTRAINT UQ_ACCOUNTS UNIQUE (NAME)
);

-- Pipeline
CREATE TABLE PIPELINE (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(80) NOT NULL,
  DESCRIPTION VARCHAR(1000) NULL,
  CONSTRAINT PK_PIPELINE PRIMARY KEY (ID),
  CONSTRAINT UQ_PIPELINE UNIQUE (NAME)
);

-- Pipeline parameters
CREATE TABLE PARAMETER (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  PIPELINE INTEGER NOT NULL,
  NAME VARCHAR(40) NOT NULL,
  DESCRIPTION VARCHAR(1000) NULL,
  DEFAULT_VALUE VARCHAR(200) NULL,
  OVERRIDDABLE BOOLEAN NOT NULL,
  CONSTRAINT PK_PARAMETER PRIMARY KEY (ID),
  CONSTRAINT UQ_PARAMETER UNIQUE (PIPELINE, NAME),
  CONSTRAINT FK_PARAMETER_PIPELINE FOREIGN KEY (PIPELINE) REFERENCES PIPELINE (ID) ON DELETE CASCADE
);

-- Branches
CREATE TABLE BRANCH (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  PIPELINE INTEGER NOT NULL,
  NAME VARCHAR(40) NOT NULL,
  DESCRIPTION VARCHAR(1000) NULL,
  CONSTRAINT PK_BRANCH PRIMARY KEY (ID),
  CONSTRAINT UQ_BRANCH UNIQUE (PIPELINE, NAME),
  CONSTRAINT FK_BRANCH_PIPELINE FOREIGN KEY (PIPELINE) REFERENCES PIPELINE (ID) ON DELETE CASCADE
);

-- Branch parameters
CREATE TABLE BRANCH_PARAMETER (
  BRANCH INTEGER NOT NULL,
  PARAMETER INTEGER NOT NULL,
  VALUE VARCHAR(200) NOT NULL,
  CONSTRAINT PK_BRANCH_PARAMETER PRIMARY KEY (BRANCH, PARAMETER),
  CONSTRAINT FK_BRANCH_PARAMETER_BRANCH FOREIGN KEY (BRANCH) REFERENCES BRANCH (ID) ON DELETE CASCADE,
  CONSTRAINT FK_BRANCH_PARAMETER_PARAMETER FOREIGN KEY (PARAMETER) REFERENCES PARAMETER (ID) ON DELETE CASCADE
);

-- Instance
CREATE TABLE INSTANCE (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  PIPELINE INTEGER NOT NULL,
  STATUS VARCHAR(20) NOT NULL,
  RESULT VARCHAR(20) NOT NULL,
  RESULT_DETAILS VARCHAR(400) NULL,
  PARAMETERS LONGTEXT NOT NULL,
  CONSTRAINT PK_INSTANCE PRIMARY KEY (ID),
  CONSTRAINT FK_INSTANCE_PIPELINE FOREIGN KEY (PIPELINE) REFERENCES PIPELINE (ID) ON DELETE CASCADE
);

-- Pipeline authorizations
CREATE TABLE PIPELINE_AUTHORIZATION (
  PIPELINE INTEGER NOT NULL,
  ACCOUNT INTEGER NOT NULL,
  ROLE VARCHAR(20) NOT NULL,
  CONSTRAINT PK_PIPELINE_AUTHORIZATION PRIMARY KEY (PIPELINE, ACCOUNT),
  CONSTRAINT FK_PIPELINE_AUTHORIZATION_PIPELINE FOREIGN KEY (PIPELINE) REFERENCES PIPELINE (ID) ON DELETE CASCADE,
  CONSTRAINT FK_PIPELINE_AUTHORIZATION_ACCOUNT FOREIGN KEY (ACCOUNT) REFERENCES ACCOUNTS (ID) ON DELETE CASCADE,
);

-- Initial admin account
INSERT INTO ACCOUNTS (NAME, FULLNAME, EMAIL, ROLENAME, MODE, PASSWORD) VALUES (
    'admin',
    'Administrator',
    '',
    'ROLE_ADMIN',
    'builtin',
    'C7AD44CBAD762A5DA0A452F9E854FDC1E0E7A52A38015F23F3EAB1D80B931DD472634DFAC71CD34EBC35D16AB7FB8A90C81F975113D6C7538DC69DD8DE9077EC');
