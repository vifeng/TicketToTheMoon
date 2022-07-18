DROP TABLE vets IF EXISTS;

CREATE TABLE Prof (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30)
);
CREATE INDEX Prof_last_name ON Prof (last_name);
