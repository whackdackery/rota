DROP TABLE IF EXISTS system_roles;

CREATE TABLE system_roles
(
    id        BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    created   TIMESTAMP    NOT NULL,
    updated   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    role_type VARCHAR(256) NOT NULL UNIQUE,
    name      VARCHAR(256) NOT NULL UNIQUE
);

INSERT INTO system_roles
VALUES (1,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'SUPER_ADMIN',
        'Super administrator'),
       (2,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'ADMIN',
        'Administrator'),
       (3,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'EDITOR',
        'Editor'),
       (4,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'SCHEDULER',
        'Scheduler'),
       (5,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'END_USER',
        'End user');

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    created  TIMESTAMP    NOT NULL,
    updated  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    username VARCHAR(128) NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
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

DROP TABLE IF EXISTS users_to_system_roles;

CREATE TABLE users_to_system_roles
(
    user_id        BIGINT NOT NULL,
    system_role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (system_role_id) REFERENCES system_roles (id),
    UNIQUE KEY (user_id, system_role_id)
);

INSERT INTO users_to_system_roles
VALUES (1, 1),
       (2, 4);