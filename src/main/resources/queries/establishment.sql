drop table if exists establishment;
create table establishment
(
  id          int primary key auto_increment,
  director_id int unique  not null, /*foreign key*/
  address     varchar(60) not null,
  postal_code varchar(10) not null,
  city        varchar(40) not null,
  country     char(2)     not null
);
select * from establishment;
