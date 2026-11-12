CREATE TABLE franchises (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(150) NOT NULL,
                            CONSTRAINT chk_franchise_name_not_blank CHECK (LENGTH(TRIM(name)) > 0)
);

CREATE TABLE branches (
                          id BIGSERIAL PRIMARY KEY,
                          franchise_id BIGINT NOT NULL,
                          name VARCHAR(150) NOT NULL,
                          CONSTRAINT fk_branch_franchise FOREIGN KEY (franchise_id)
                              REFERENCES franchises (id) ON DELETE CASCADE,
                          CONSTRAINT chk_branch_name_not_blank CHECK (LENGTH(TRIM(name)) > 0)
);

CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          branch_id BIGINT NOT NULL,
                          name VARCHAR(150) NOT NULL,
                          stock INTEGER NOT NULL DEFAULT 0,
                          CONSTRAINT fk_product_branch FOREIGN KEY (branch_id)
                              REFERENCES branches (id) ON DELETE CASCADE,
                          CONSTRAINT chk_product_name_not_blank CHECK (LENGTH(TRIM(name)) > 0),
                          CONSTRAINT chk_product_stock_non_negative CHECK (stock >= 0)
);

-- Índices clave para optimizar la consulta de mayor stock por sucursal de una franquicia
CREATE INDEX idx_branches_franchise_id ON branches (franchise_id);
CREATE INDEX idx_products_branch_stock ON products (branch_id, stock DESC);