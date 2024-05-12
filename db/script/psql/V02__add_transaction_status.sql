create type transaction_status as enum ('NEW', 'CANCELED', 'REJECTED', 'SETTLED', 'PENDING');

alter table transaction add status transaction_status not null default 'NEW';