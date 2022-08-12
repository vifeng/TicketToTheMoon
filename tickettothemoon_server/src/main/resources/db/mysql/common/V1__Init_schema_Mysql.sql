-- FIXME: MYSQL
-- all this comments doesn't pass the gradle bootRun
  -- Your password does not satisfy the current policy requirements
  -- database has to be created. 
  -- gradle flyclean doesn't work : asks for connection info.
  -- to reset flyway got to database and do DELETE FROM tickettothemoon.flyway_schema_history ; then run the app again


-- CREATE DATABASE IF NOT EXISTS tickettothemoon;
-- ALTER DATABASE tickettothemoon
-- DEFAULT CHARACTER SET utf8mb4
-- DEFAULT COLLATE utf8mb4_0900_ai_ci;
-- CREATE USER 'computer'@'localhost' IDENTIFIED BY 'rootroot';
-- GRANT ALL PRIVILEGES ON tickettothemoon.* TO 'computer'@'localhost';
-- FLUSH PRIVILEGES;
-- USE tickettothemoon;

DROP TABLE IF EXISTS PROF;
CREATE TABLE PROF (
  prof_Id INTEGER NOT NULL AUTO_INCREMENT,
  nom varchar(30),
  prenom varchar(30),
 PRIMARY KEY (prof_Id)
);