CREATE TABLE product (
    id_product VARCHAR(36) NOT NULL,
    tx_name VARCHAR(256) NOT NULL,
    price DOUBLE NOT NULL,
    tx_unit VARCHAR(64) NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id_product)
);
