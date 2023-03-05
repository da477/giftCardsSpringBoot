DELETE FROM users;
INSERT INTO users (email, first_name, last_name, password, role, status)
VALUES ('admin@admin.com', 'Admin', 'Adminov', '$2a$12$fUk4C6XvaJikG6XsvJgQIuDzgRO2szinGMPXErfOCbk0rDoZjK5aO', 'ADMIN',
        'ACTIVE'),
       ('user@user.com', 'User', 'Userov', '$2a$12$fUk4C6XvaJikG6XsvJgQIuDzgRO2szinGMPXErfOCbk0rDoZjK5aO', 'USER',
        'ACTIVE'),
       ('user2@user.com', 'User2', 'Userov', '$2a$12$fUk4C6XvaJikG6XsvJgQIuDzgRO2szinGMPXErfOCbk0rDoZjK5aO', 'USER',
        'BANNED');

DELETE FROM giftcards;
insert into giftcards (number, amount, isprint, status, isGenerated, typeCard, owner_id, withdrawal)
values (123456789, 100, false, 'NEW', true, 'SIMPLE', '21601', 0),
       (123456788, 100, true, 'BANNED', true, 'SIMPLE', '54709', 0),
       (123456787, 99, false, 'ACTIVE', true, 'DISCOUNTED', '54709', 0),
       (123456786, 99, true, 'ACTIVE', false, 'DISCOUNTED', '21601', 0),
       (123456785, 99, false, 'BANNED', true, 'DISCOUNTED', 'kre5901', 98),
       (123456784, 98, false, 'ACTIVE', true, 'CITADELE', 'kre5901', 98);