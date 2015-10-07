create table t_payment (
    id VARCHAR(40) not null,
    version NUMERIC(8) not null,
    amount DECIMAL(8,2) not null,
    created_at DATETIME not null,
    CONSTRAINT pk_payment PRIMARY KEY (id)
);

