CREATE DATABASE IF NOT EXISTS tickettothemoon;
GRANT ALL PRIVILEGES ON tickettothemoon.* TO pc@localhost IDENTIFIED BY 'pc';

USE tickettothemoon;

CREATE TABLE IF NOT EXISTS Prof (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  INDEX(last_name)
) engine=InnoDB;