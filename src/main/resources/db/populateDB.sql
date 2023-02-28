DELETE FROM users;
DELETE FROM developers;
DELETE FROM giftcards;

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

insert into giftcards (number, amount, isprint, status, withdrawal)
values (123456789, 100, false, 'NEW', 0),
       (123456788, 99, false, 'ACTIVE', 0),
       (123456787, 98, false, 'TEST', 98);