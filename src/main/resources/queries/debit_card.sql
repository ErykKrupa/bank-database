drop table if exists debit_card;
create table debit_card
(
  number            char(16) primary key,
  account_number    char(26) unique not null,
  card_verification char(3)         not null,
  expiry_date       date            not null,
  constraint foreign key (account_number)
  references client (account_number)
);
select * from debit_card;