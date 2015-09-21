create table t_order (
    id VARCHAR(40) not null,
    location varchar(50) not null,
    status varchar(50) not null,
    cost number(8,2) not null,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

