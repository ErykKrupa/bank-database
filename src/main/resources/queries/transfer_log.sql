drop table if exists transfer_log;
create table transfer_log
(
  id                      int primary key auto_increment,
  sender_account_number   char(26) not null,
  receiver_account_number char(26) not null,
  currency_iso            char(3)  not null,
  amount                  bigint   not null,
  transaction_time        datetime not null,
  constraint foreign key (sender_account_number)
  references client (account_number),
  constraint foreign key (receiver_account_number)
  references client (account_number)
);
select * from transfer_log;
