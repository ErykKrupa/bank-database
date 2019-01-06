drop table if exists client;
create table client
(
  account_number char(26) primary key,
  pesel          char(11) unique,
  account_type   enum ('standard', 'for_kids') not null default 'standard',
  first_name     varchar(40)                   not null,
  last_name      varchar(40)                   not null,
  birth_date     date                          not null,
  phone_number   char(9) unique                not null,
  email          varchar(60) unique,
  login          varchar(30) unique            not null,
  password       varchar(30)                   not null,
  is_active      bool                          not null default true,
  log_time       datetime                      not null default now()
);
select * from client;


