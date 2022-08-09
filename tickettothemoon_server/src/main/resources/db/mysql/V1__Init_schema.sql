-- CREATE DATABASE IF NOT EXISTS tickettothemoon;
-- ALTER DATABASE tickettothemoon
-- DEFAULT CHARACTER SET utf8
-- DEFAULT COLLATE utf8_general_ci;
-- GRANT ALL PRIVILEGES ON tickettothemoon.* TO pc@localhost IDENTIFIED BY 'pc';


-- USE tickettothemoon;

CREATE TABLE IF NOT EXISTS PROF (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(30),
  prenom VARCHAR(30),
  INDEX(nom)
) engine=InnoDB;