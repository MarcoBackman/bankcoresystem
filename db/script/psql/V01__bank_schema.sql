-- postgresSQL script
CREATE TABLE account (
   account_id varchar(45) NOT NULL,
   username varchar(45) NOT NULL,
   created_when TIMESTAMP NOT NULL DEFAULT NOW(),
   PRIMARY KEY (account_id)
);

CREATE TABLE "user" (
    user_id SERIAL NOT NULL,
    username varchar(45) DEFAULT NULL,
    UserRole varchar(45) DEFAULT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE transaction (
   transaction_id SERIAL NOT NULL,
   account_id varchar(30) NOT NULL,
   tran_when TIMESTAMP NOT NULL DEFAULT NOW(),
   settled_when TIMESTAMP DEFAULT NULL,
   money_amount decimal(10,0) NOT NULL DEFAULT '0',
   side char(2) DEFAULT NULL,
   PRIMARY KEY (transaction_id),
   CONSTRAINT account_id FOREIGN KEY (account_id) REFERENCES account (account_id)
);