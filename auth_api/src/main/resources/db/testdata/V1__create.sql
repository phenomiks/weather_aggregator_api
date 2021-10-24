BEGIN TRANSACTION;

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    login      VARCHAR(30) NOT NULL UNIQUE,
    email      VARCHAR(40) NOT NULL UNIQUE,
    password   VARCHAR(64) NOT NULL,
    archive    BOOLEAN   DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE tokens
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id),
    token   VARCHAR NOT NULL UNIQUE
);

COMMIT;