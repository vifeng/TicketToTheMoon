-- CREATE OR REPLACE DATABASE IF NOT EXISTS tickettothemoon;
-- GRANT ALL PRIVILEGES ON tickettothemoon.* TO pc@localhost IDENTIFIED BY 'pc';

-- USE tickettothemoon;


DROP TABLE PROF IF EXISTS;

CREATE TABLE PROF (
  prof_Id INTEGER NOT NULL AUTO_INCREMENT,
  nom varchar(30),
  prenom varchar(30),
 PRIMARY KEY (prof_Id)
);

CREATE TABLE employees (
  id INTEGER NOT NULL AUTO_INCREMENT,
  first_name varchar(30),
  last_name varchar(30),
  email varchar(30),
 PRIMARY KEY (id)
);