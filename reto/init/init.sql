create table if not exists tbl_cliente(
    id BIGINT not null primary key AUTO_INCREMENT,
    cliente_id varchar(50) not null,
    monto double not null,
    tasa double not null,
    moneda_origen varchar(3) not null,
    moneda_destino varchar(3) not null
);

insert into tbl_cliente(cliente_id, monto, tasa, moneda_origen, moneda_destino)
values ("test_123", 1000, 3.45, "USD", "PEN");