-- mySQL script
CREATE TABLE `account` (
   `account_id` varchar(45) NOT NULL,
   `account_no` varchar(9) NOT NULL,
   `username` varchar(45) NOT NULL,
   `created_when` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`account_id`)
);

CREATE TABLE `user` (
    `user_id` int NOT NULL AUTO_INCREMENT,
    `username` varchar(45) DEFAULT NULL,
    `user_role` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `transaction` (
   `transaction_id` bigint NOT NULL AUTO_INCREMENT,
   `account_id` varchar(30) NOT NULL,
   `tran_when` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `settled_when` datetime DEFAULT NULL,
   `processed_when` datetime DEFAULT NULL,
   `money_amount` decimal(10,0) NOT NULL DEFAULT '0',
   `side` char(2) DEFAULT NULL,
   PRIMARY KEY (`transaction_id`),
   CONSTRAINT `account_id` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
);