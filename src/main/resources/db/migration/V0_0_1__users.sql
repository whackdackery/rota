DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    created  TIMESTAMP    NOT NULL,
    updated  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    username VARCHAR(128) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(512) NOT NULL
);
INSERT INTO users
VALUES (1,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'admin',
        'admin@email.com',
        'password'),
       (2,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'user',
        'user@email.com',
        'password');