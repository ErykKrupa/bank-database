DROP USER IF EXISTS `loginuser`@`localhost`;
CREATE USER `loginuser`@`localhost` IDENTIFIED BY 'loginuserpassword';
GRANT SELECT (login,password,is_active) ON bank.client TO `loginuser`@`localhost`;
GRANT SELECT (login,password,access,is_active) ON bank.employee TO `loginuser`@`localhost`;
FLUSH PRIVILEGES ;
