/***********************************************************
* Create the database named prim, its tables, and a user
* that is used to login to MySQL
************************************************************/

DROP DATABASE IF EXISTS prim;

CREATE DATABASE prim;

USE prim;

/*
 * Schema Version table so that we can determine the version
 * currently in use.
 */
CREATE TABLE SCHEMA_VERSION(
	id INT NOT NULL AUTO_INCREMENT,
	major INT NOT NULL,
	minor INT NOT NULL,
	revision INT NOT NULL,
	PRIMARY KEY (id)
);

/*
 * Populate the version table
 */
DELETE FROM SCHEMA_VERSION;
INSERT INTO SCHEMA_VERSION(major, minor, revision)
VALUES (1, 0, 0);

/*
 * User table used by Prim for login purposes
 */ 
CREATE TABLE USER(
   id BIGINT NOT NULL AUTO_INCREMENT,
   username VARCHAR(64) NOT NULL,
   password VARCHAR(100) NOT NULL,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   email VARCHAR(64) NOT NULL,
   sso_id VARCHAR(64) NOT NULL,
   status INT NOT NULL DEFAULT 1,
   failed_logins INT NOT NULL DEFAULT 0,
   last_visited_on DATETIME NULL,
   last_visited_from VARCHAR(32) NULL,
   user_key VARCHAR(100) NULL,
   activated_on DATETIME NULL,
   PRIMARY KEY (id),
   UNIQUE (username),
   UNIQUE (email),
   UNIQUE (sso_id)
);

/*
 * Role table to determine what actions a user can perform
 */
CREATE TABLE ROLE(
   id BIGINT NOT NULL AUTO_INCREMENT,
   type VARCHAR(64) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (type)
); 

/*
 * Weak entity to resolve the many-to-many relationship between
 * users and their roles as a user can have many roles.
 */
CREATE TABLE USER_ROLE(
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
	PRIMARY KEY (user_id, role_id),
	CONSTRAINT FK_USER FOREIGN KEY (user_id) REFERENCES USER(id),
	CONSTRAINT FK_ROLE FOREIGN KEY (role_id) REFERENCES ROLE(id)
);

/*
 * Setup the default roles
 */
INSERT INTO ROLE (type)
VALUES ('USER'), ('ADMIN'), ('DBA');

/*
 * Add the initial admin account so that additional users can be added.
 * password is encrypted form of 'abc125'
 */
INSERT INTO USER (username, password, first_name, last_name, email, sso_id)
VALUES ('primadmin', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'PRIM', 'Administrator', 'partyofv5@gmail.com', 'primadmin');

/*
 * Now add the admin account to the USER_ROLE table as an ADMIN
 */
INSERT INTO USER_ROLE (user_id, role_id)
  SELECT u.id, r.id
  FROM USER u, ROLE r
  WHERE u.sso_id = 'primadmin' AND r.type = 'ADMIN';
  
/*
 * Persistent Login table to store the login tokens for Remember Me
 */
CREATE TABLE PERSISTENT_LOGINS (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
); 
  
/*
 * Setup the database user for prim
 */
DELIMITER //
CREATE PROCEDURE drop_user_if_exists()
BEGIN
    DECLARE userCount BIGINT DEFAULT 0 ;

    SELECT COUNT(*) INTO userCount FROM mysql.user
    WHERE User = 'primsystem' and  Host = 'localhost';

    IF userCount > 0 THEN
        DROP USER primsystem@localhost;
    END IF;
END ; //
DELIMITER ;

CALL drop_user_if_exists() ;

CREATE USER primsystem@localhost IDENTIFIED BY 'Pr!mP@ssw0rd';

GRANT SELECT, INSERT, UPDATE, DELETE
ON prim.*
TO primsystem@localhost;

USE prim;