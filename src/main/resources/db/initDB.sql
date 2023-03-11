DROP TABLE IF EXISTS user_roles;
create table user_roles
(
    user_id INTEGER not null,
    role_id INTEGER not null,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) on DELETE set null
);


DROP TABLE IF EXISTS roles cascade;
create table roles
(
    id   int generated by default as identity PRIMARY KEY,
    name varchar(100) not null
);

DROP TABLE IF EXISTS users cascade;
create table users
(
    id         BIGINT generated by default as identity PRIMARY KEY,
    email      varchar(128)                                    not null,
    name       varchar(50)                                     not null,
    surname    varchar(100)                                    not null,
    password   varchar(255)                                    not null,
    role       varchar(20) default 'USER'::character varying   not null,
    status     varchar(20) default 'ACTIVE'::character varying not null,
    registered timestamp   default now()                       not null
);
create unique index users_email_uindex on users (email);

DROP TABLE IF EXISTS roles cascade;
create table roles
(
    id   int generated by default as identity PRIMARY KEY,
    name varchar(100) not null
);



DROP TABLE IF EXISTS giftcards;
CREATE TABLE giftCards
(
    id          BIGINT generated by default as identity PRIMARY KEY,
    number      bigserial                                       NOT NULL,
    amount      decimal                                         NOT NULL,
    isPrint     boolean,
    status      varchar(20) default 'NEW'::character varying    not null,
    isGenerated boolean,
    typeCard    varchar(20) default 'SIMPLE'::character varying not null,
    registered  timestamp   default now(),
    withdrawal  decimal,
    lastUpdate  timestamp   default now(),
    owner_id    varchar(20)
);
create unique index giftCards_number_index on giftCards (number);
