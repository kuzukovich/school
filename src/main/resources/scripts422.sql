CREATE TABLE cars
(
    id    SERIAL PRIMARY KEY,
    brand VARCHAR(30) NOT NULL,
    model VARCHAR(30) NOT NULL,
    cost  INTEGER     NOT NULL CHECK (cost > 0)
);


CREATE TABLE drivers
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(30) NOT NULL,
    age     SMALLINT    NOT NULL CHECK (age > 0),
    license BOOLEAN     NOT NULL,
    car_id  SMALLINT REFERENCES cars (id)
);