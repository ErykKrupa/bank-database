DROP USER IF EXISTS `bankclient`@`localhost`;
CREATE USER `bankclient`@`localhost` IDENTIFIED BY 'clientpassword';
GRANT SELECT, UPDATE ON bank.account_currency TO bankclient@`localhost`;
GRANT INSERT, UPDATE, SELECT ON bank.transfer_log TO bankclient@`localhost`;
GRANT SELECT ON bank.currency TO bankclient@`localhost`;
GRANT SELECT ON bank.debit_card TO bankclient@`localhost`;
GRANT SELECT ON bank.credit_card TO bankclient@`localhost`;
GRANT SELECT, UPDATE ON bank.client TO bankclient@`localhost`;
FLUSH PRIVILEGES ;
