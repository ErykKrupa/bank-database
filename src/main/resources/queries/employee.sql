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
  access           varchar(40)                         not null, /*common, manager, admin*/
  salary           bigint                              not null,
  login            varchar(30) unique                  not null,
  password         char(60)                            not null,
  is_active        bool                                not null,
  log_time         datetime                            not null
);
select * from employee;
