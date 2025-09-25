CREATE TABLE users
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name  VARCHAR(50)  NOT NULL,
    last_name   VARCHAR(50),
    bio         TEXT,
    city        VARCHAR(100) NOT NULL,

    age         INTEGER      NOT NULL,

    phone       VARCHAR(20)  NOT NULL UNIQUE,
    email       VARCHAR(100) UNIQUE,
    password    VARCHAR(255) NOT NULL,

    gender      VARCHAR(20)  NOT NULL,
    role        VARCHAR(20)  NOT NULL,
    looking_for VARCHAR(25),

    added_at    TIMESTAMP,
    updated_at  TIMESTAMP
);