create table accounts (
    id serial primary key,
    name varchar(100) unique not null,
    total_contract_value decimal not null default '0.0'
);