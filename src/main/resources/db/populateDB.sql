DELETE FROM users;
-- https://bcrypt-generator.com from admin
INSERT INTO users (id, email, name, surname, password, role, status)
VALUES (1, 'admin@admin.com', 'admin', 'Adminov', '$2a$11$lYujt9mtAVoQI4YJ0QPCmOAD8PalSt2b9hiUyoz5otsp61/vat7TO', 'ADMIN',
        'ACTIVE'),
       (2, 'user@user.com', 'user', 'Userov', '$2a$11$lYujt9mtAVoQI4YJ0QPCmOAD8PalSt2b9hiUyoz5otsp61/vat7TO', 'USER',
        'ACTIVE'),
       (3, 'user2@user.com', 'user2', 'Userov', '$2a$11$lYujt9mtAVoQI4YJ0QPCmOAD8PalSt2b9hiUyoz5otsp61/vat7TO', 'USER',
        'BANNED');

delete from roles;
insert into roles values (1, 'ROLE_USER');
insert into roles values (2, 'ROLE_ADMIN');

delete from user_roles;
insert into user_roles values (1, 2);

DELETE FROM giftcards;
insert into giftcards (number, amount, isprint, status, isGenerated, typeCard, owner_id, withdrawal)
values (123456789, 100, false, 'NEW', true, 'SIMPLE', '21201', 0),
       (123456788, 100, true, 'BANNED', true, 'SIMPLE', '54309', 0),
       (123456787, 99, false, 'ACTIVE', true, 'DISCOUNTED', '54709', 0),
       (123456786, 99, true, 'ACTIVE', false, 'DISCOUNTED', '21501', 0),
       (123456785, 99, false, 'BANNED', true, 'DISCOUNTED', 'k5901', 98),
       (123456783, 99, false, 'ACTIVE', true, 'BANK', '53708', 98),
       (123456784, 98, false, 'ACTIVE', true, 'BANK', 'e5901', 98);