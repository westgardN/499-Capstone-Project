/***********************************************************
* Create the database named prim, its tables, and a user
* that is used to login to MySQL
************************************************************/

DROP DATABASE IF EXISTS prim;

CREATE DATABASE prim CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

/*
 * Populate the version table
 */
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
   status VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
   enabled TINYINT(1) NOT NULL DEFAULT 0,
   failed_logins INT NOT NULL DEFAULT 0,
   last_visited_on DATETIME NULL,
   last_visited_from VARCHAR(100) NULL,
   last_password_changed_on DATETIME NULL,
   activated_on DATETIME NULL,
   PRIMARY KEY (id),
   UNIQUE (username),
   UNIQUE (email),
   UNIQUE (sso_id)
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

/*
 * Role table to determine what actions a user can perform
 */
CREATE TABLE ROLE(
   id BIGINT NOT NULL AUTO_INCREMENT,
   type VARCHAR(64) NOT NULL,
   PRIMARY KEY (id),
   UNIQUE (type)
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

/*
 * Security Token table is used for user authentication and authorization
 */
CREATE TABLE SECURITY_TOKEN(
   id BIGINT NOT NULL AUTO_INCREMENT,
   user_id BIGINT NOT NULL,
   token VARCHAR(100) NOT NULL,
   created_date DATETIME NOT NULL DEFAULT NOW(),
   expiration_date DATETIME NULL,
   PRIMARY KEY (id),
   CONSTRAINT FK_USER_TOKEN FOREIGN KEY (user_id) REFERENCES USER(id)
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

/*
 * Setup the default roles
 */
INSERT INTO ROLE (type)
VALUES ('USER'), ('ADMIN'), ('DBA');

/*
 * Add the initial admin and user accounts so that additional users can be added.
 * password is encrypted form of 'abc125'
 */
INSERT INTO USER (username, password, first_name, last_name, email, sso_id, status, enabled)
VALUES ('primadmin', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'PRIM', 'Administrator', 'partyofv5@gmail.com', 'primadmin', 'ACTIVE', 1),
('primuser', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'PRIM', 'Administrator', 'partyofv5user@gmail.com', 'primuser', 'ACTIVE', 1)
;

/*
 * Now add the admin account to the USER_ROLE table as an ADMIN
 */
INSERT INTO USER_ROLE (user_id, role_id)
  SELECT u.id, r.id
  FROM USER u, ROLE r
  WHERE u.sso_id = 'primadmin' AND r.type IN ('USER', 'ADMIN');
  
/*
 * Now add the user account to the USER_ROLE table as an USER
 */
INSERT INTO USER_ROLE (user_id, role_id)
  SELECT u.id, r.id
  FROM USER u, ROLE r
  WHERE u.sso_id = 'primuser' AND r.type IN ('USER');

/*
 * Persistent Login table to store the login tokens for Remember Me
 */
CREATE TABLE PERSISTENT_LOGINS (
	username VARCHAR(64) NOT NULL,
	series VARCHAR(64) NOT NULL,
	token VARCHAR(64) NOT NULL,
	last_used TIMESTAMP NOT NULL,
	PRIMARY KEY (series)
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

/*
 * Interaction table to store interactions received from a social network
 * and manually created ones.
 */
 CREATE TABLE INTERACTION (
   id BIGINT NOT NULL AUTO_INCREMENT,
   created_time DATETIME NULL,
   description VARCHAR(512) NULL,
   from_id VARCHAR(128) NULL,
   from_name  VARCHAR(128) NULL,
   message_id VARCHAR(128) NULL,
   message_link VARCHAR(512) NULL,
   message TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
   sentiment INT NULL,
   social_network VARCHAR(128) NULL,
   type VARCHAR(128) NULL,
   state VARCHAR(128) NOT NULL DEFAULT 'OPEN',
   flag VARCHAR(128) NULL,
   PRIMARY KEY (id)
 ) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

 /*
  * Interaction Response stores the response action to an interaction. The
  * type field determines the type of response and the state field
  * determines if further action / responses are needed.
  */
 CREATE TABLE INTERACTION_RESPONSE (
   id BIGINT NOT NULL AUTO_INCREMENT,
   response_time DATETIME NOT NULL,
   response_to BIGINT NOT NULL,
   response_by BIGINT NOT NULL,
   message TEXT NULL,
   type VARCHAR(128) NOT NULL,
   PRIMARY KEY (id),
   CONSTRAINT FK_USER_RESPONSE FOREIGN KEY (response_by) REFERENCES USER(id),
   CONSTRAINT FK_INTERACTION_RESPONSE FOREIGN KEY (response_to) REFERENCES INTERACTION(id)
 ) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

 /*
  * Sentiment Queue stores interactions waiting to have their message
  * content analyzed. Supports priority queueing.
  */
CREATE TABLE SENTIMENT_QUEUE (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_time DATETIME NOT NULL,
  interaction_id BIGINT NOT NULL,
  priority INT NOT NULL DEFAULT 10,
  processed TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  CONSTRAINT FK_INTERACTION_QUEUE FOREIGN KEY (interaction_id) REFERENCES INTERACTION(id)
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

/*
 * The Social Network Registration stores the registration of the social
 * accounts that we will be retrieving information from.
 */
CREATE TABLE SOCIAL_NETWORK_REGISTRATION (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL,
  created_time DATETIME NOT NULL,
  social_network VARCHAR(128) NOT NULL,
  token VARCHAR(512) NOT NULL,
  refresh_token VARCHAR(512) NULL,
  expires DATETIME NULL,
  last_used TIMESTAMP NULL,
  PRIMARY KEY (id)
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

/*
 * The Data Refresh Request stores information about a data retrieval request.
 * The request may be user or system initiated and may be to retrieve social
 * media data or score the sentiment or any future type of request.
 * The purpose is to prevent multiple retrieval requests for the same data.
 */
CREATE TABLE DATA_REFRESH_REQUEST (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_time DATETIME NOT NULL,
  requested_by VARCHAR(128) NOT NULL,
  start_time DATETIME NULL,
  finish_time DATETIME NULL,
  type VARCHAR(128),
  PRIMARY KEY (id)
) CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

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