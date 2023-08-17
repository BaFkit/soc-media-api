create table if not exists users
(
    id         uuid primary key,
    username   varchar(50) unique not null,
    email      varchar(50) unique not null,
    password   varchar(255)       not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists posts
(
    id         uuid primary key,
    title      varchar(255) not null,
    text       text,
    image      bytea,
    user_id    uuid         not null references users (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table if not exists users_roles
(
    user_id    uuid   not null references users (id),
    role_id    bigint not null references roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (user_id, role_id)
);

create table if not exists subscriptions
(
    id                  bigserial primary key,
    follower            uuid    not null references users (id),
    subscription_target uuid    not null references users (id),
    is_friends          boolean not null,
    created_at          timestamp default current_timestamp
)