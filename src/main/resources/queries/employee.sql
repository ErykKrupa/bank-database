drop table if exists employee;
create table employee
(
  id               int primary key auto_increment,
  pesel            char(11) unique,
  first_name       varchar(40)                         not null,
  last_name        varchar(40)                         not null,
  birth_date       date                                not null,
  phone_number     char(9) unique                      not null,
  email            varchar(60) unique,
  position         varchar(40)                         not null,
  access           varchar(40)                         not null default 'common', /*common, manager, admin*/
  salary           bigint                              not null,
  login            varchar(30) unique                  not null,
  password         varchar(30)                         not null,
  is_working       bool                                not null default true,
  log_time         datetime                            not null default now()
);
select * from employee;
