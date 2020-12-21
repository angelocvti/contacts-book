create table contacts
(
    id             BIGSERIAL,
    name           VARCHAR(255),
    email          VARCHAR(255),
    address        VARCHAR(255),
    birthdate      DATE,
    primary key (id)
);