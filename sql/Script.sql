DROP database IF EXISTS task;

CREATE database `task`;

use `task`;

CREATE TABLE accesslevel ( id BIGINT(8) PRIMARY KEY NOT NULL UNIQUE auto_increment,
title varchar(50) NOT NULL ) engine = innodb DEFAULT charset = utf8;

CREATE TABLE USER ( id BIGINT(8) PRIMARY KEY NOT NULL UNIQUE auto_increment,
login varchar(50) NOT NULL,
password varchar(50) NOT NULL,
accesslvl BIGINT(8) NOT NULL,
dateofcreation datetime NOT NULL,
dateofmodification datetime NOT NULL,
FOREIGN KEY (accesslvl) REFERENCES accesslevel (id) ) engine = innodb DEFAULT charset = utf8;

INSERT
	INTO
	accesslevel (title)
VALUES ('Admin');

INSERT
	INTO
	accesslevel (title)
VALUES ('Tester');

INSERT
	INTO
	accesslevel (title)
VALUES ('User');