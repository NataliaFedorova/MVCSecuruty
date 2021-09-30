################ USERS ##################

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     username TEXT NOT NULL,
                                     name TEXT NOT NULL,
                                     surname TEXT NOT NULL,
                                     email TEXT,
                                     age INT NOT NULL,
                                     password VARCHAR(255));


INSERT INTO users (username, name, surname, email, age, password) VALUES
('adminn', 'John', 'Smith', 'js@gmail.com', 23, '$2a$12$QBGXt86AvJOYhMNy727rm.DEGXJHHycp6.PII6qtT1z.NUGdyZl5i'),
('userr', 'Trisha', 'Vern', 'tv@gmail.com', 44, '$2a$12$B4X/BcYRlodO40yroTWt.e/zXu3z3MnEkLO4QLEtmhW/SBmyV0TOW');

################ ROLES ##################

CREATE TABLE IF NOT EXISTS roles (
                                     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     roleName VARCHAR(100) NOT NULL
    );

INSERT INTO roles VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles VALUES (2, 'ROLE_USER');

################ Table for mapping users an roles: user_roles ##################
CREATE TABLE  user_roles (
                             user_id BIGINT NOT NULL,
                             role_id BIGINT NOT NULL,
                             PRIMARY KEY (user_id, role_id),
                             FOREIGN KEY(user_id) REFERENCES users(id),
                             FOREIGN KEY(role_id) REFERENCES roles(id)
);

INSERT INTO user_roles(user_id, role_id) VALUES (1,1);
INSERT INTO user_roles(user_id, role_id) VALUES (2,2);
