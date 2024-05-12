ALTER TABLE transaction ALTER COLUMN money_amount TYPE DECIMAL(20, 4) USING money_amount::decimal;
