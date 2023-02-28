DROP TABLE IF EXISTS users;

create table users
(
    id         bigserial constraint users_pk   primary key,
    email      varchar(255)                                    not null,
    first_name varchar(50)                                     not null,
    last_name  varchar(100)                                    not null,
    password   varchar(255)                                    not null,
    role       varchar(20) default 'USER'::character varying   not null,
    status     varchar(20) default 'ACTIVE'::character varying not null,
    registered timestamp   default now()                       not null
);
create unique index users_email_uindex   on users (email);

DROP TABLE IF EXISTS developers;
CREATE TABLE developers
(
    id         bigserial NOT NULL,
    firstName  varchar(50)   NOT NULL,
    lastName   varchar(100)  NOT NULL,
    CONSTRAINT developers_pk PRIMARY KEY(id)
);

DROP TABLE IF EXISTS giftcards;
CREATE TABLE giftCards
(
    id         bigserial
        constraint "giftCards_pk"
            primary key,
    number      integer                                         NOT NULL,
    amount      decimal                                         NOT NULL,
    isPrint     boolean,
    status      varchar(20)                                     not null,
    registered  timestamp   default now()                       not null,
    withdrawal  decimal,
    lastUpdate  timestamp   default now()                       not null
);
create unique index giftCards_number_index on giftCards (number);
