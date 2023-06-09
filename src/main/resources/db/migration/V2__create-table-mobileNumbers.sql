CREATE TABLE cliente_mobile_numbers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    mobile_numbers VARCHAR(100) NOT NULL,
    cliente_id BIGINT,
    CONSTRAINT fk_cliente_mobile_numbers_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES clientes (id)
        ON DELETE CASCADE
);