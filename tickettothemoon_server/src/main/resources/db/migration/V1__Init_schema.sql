CREATE OR REPLACE DATABASE IF NOT EXISTS tickettothemoon;
GRANT ALL PRIVILEGES ON tickettothemoon.* TO pc@localhost IDENTIFIED BY 'pc';

USE tickettothemoon;


DROP TABLE PROF IF EXISTS;

CREATE TABLE PROF (
  prof_Id INTEGER NOT NULL AUTO_INCREMENT,
  nom varchar(30),
  prenom varchar(30),
 PRIMARY KEY (prof_Id)
);