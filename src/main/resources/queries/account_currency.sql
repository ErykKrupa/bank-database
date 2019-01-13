drop table if exists account_currency;
create table account_currency
(
  account_number char(26) not null,
  currency_iso   char(3)  not null,
  balance        bigint   not null,
  lending_rate   bigint   not null,
  constraint primary key (account_number, currency_iso),
  foreign key (account_number)
  references client (account_number),
  foreign key (currency_iso)
  references currency (iso)
);
select * from account_currency;