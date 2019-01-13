DROP USER IF EXISTS `employee`@`localhost`;
CREATE USER `employee`@`localhost` IDENTIFIED BY 'employeepassword';
GRANT SELECT ON bank.account_currency TO `employee`@`localhost`;
GRANT SELECT, INSERT, UPDATE ON bank.client TO `employee`@`localhost`;
GRANT SELECT ON bank.account_currency TO `employee`@`localhost`;
GRANT SELECT ON bank.transfer_log TO `employee`@`localhost`;
GRANT INSERT, DELETE ON bank.debit_card TO `employee`@`localhost`;
GRANT INSERT, DELETE ON bank.credit_card TO `employee`@`localhost`;
GRANT SELECT, UPDATE ON bank.employee TO `employee`@`localhost`;
FLUSH PRIVILEGES ;