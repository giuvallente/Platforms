CREATE TABLE orders (
    id_order VARCHAR(36) NOT NULL,
    id_user VARCHAR(36) NOT NULL,
    date_order TIMESTAMP NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id_order)
);
