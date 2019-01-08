drop table if exists currency;
create table currency
(
  iso                char(3) primary key,
  currency_name      varchar(30) unique not null,
  exchange_to_dollar bigint not null
);
select * from currency;