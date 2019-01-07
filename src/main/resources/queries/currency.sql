drop table if exists currency;
create table currency
(
  iso   char(3)  primary key unique not null,
  name varchar(30) unique not null,
  exchange_to_dollar decimal(19,4)
);
select * from currency;