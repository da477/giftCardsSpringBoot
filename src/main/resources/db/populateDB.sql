DELETE FROM users;
DELETE FROM developers;

INSERT INTO users (email, first_name, last_name, password, role, status)
VALUES ('admin@admin.com', 'Admin', 'Adminov', '$2a$12$fUk4C6XvaJikG6XsvJgQIuDzgRO2szinGMPXErfOCbk0rDoZjK5aO', 'ADMIN',
        'ACTIVE'),
       ('user@user.com', 'User', 'Userov', '$2a$12$fUk4C6XvaJikG6XsvJgQIuDzgRO2szinGMPXErfOCbk0rDoZjK5aO', 'USER',
        'ACTIVE'),
       ('user2@user.com', 'User2', 'Userov', '$2a$12$fUk4C6XvaJikG6XsvJgQIuDzgRO2szinGMPXErfOCbk0rDoZjK5aO', 'USER',
        'BANNED');

INSERT INTO developers(firstname, lastname)
VALUES ('Ivan', 'Ivanov'),
       ('Sergey', 'Sergeev'),
       ('Petr', 'Petrov');
