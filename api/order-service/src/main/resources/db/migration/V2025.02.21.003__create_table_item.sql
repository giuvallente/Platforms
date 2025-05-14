CREATE TABLE item (
    id_item VARCHAR(36) NOT NULL,
    id_product VARCHAR(36) NOT NULL,
    id_order VARCHAR(36) NOT NULL,
    qtd INT NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id_item)
);
