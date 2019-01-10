drop table if exists credit_card;
create table credit_card
(
  number            char(16) primary key,
  account_number    char(26)       not null,
  card_verification char(3)        not null,
  expiry_date       date           not null,
  funds_limit       bigint         not null,
  used_funds        bigint         not null,
  currency          char(3)        not null,
  constraint foreign key (currency)
  references currency (currency_name),
  constraint foreign key (account_number)
  references client (account_number)
);
select * from credit_card;