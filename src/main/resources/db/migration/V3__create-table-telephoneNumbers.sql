CREATE TABLE cliente_telephone_numbers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT,
    telephone_numbers VARCHAR(100),
    FOREIGN KEY (cliente_id) REFERENCES clientes (id) ON DELETE CASCADE
);