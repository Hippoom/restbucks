create table t_order_item (
    order_id VARCHAR(40) not null,
    name varchar(50) not null,
    quantity int not null,
    milk varchar(50) not null,
    size varchar(50) not null,
    price DECIMAL(8,2) not null,
    CONSTRAINT pk_order_item PRIMARY KEY (order_id, name, milk, size)
);