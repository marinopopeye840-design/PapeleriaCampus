-- Creación del schema

CREATE SCHEMA papeleria;


-- Creación de la tabla producto

CREATE TABLE papeleria.producto
(
    id_producto SERIAL PRIMARY KEY,
    codigo VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(80) NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    precio NUMERIC(10,2) NOT NULL,
    stock INTEGER NOT NULL,

    -- Restricciones de la tabla
    CONSTRAINT chk_producto_codigo
        CHECK (TRIM(codigo) <> ''),

    CONSTRAINT chk_producto_nombre
        CHECK (CHAR_LENGTH(TRIM(nombre)) BETWEEN 3 AND 80),

    CONSTRAINT chk_producto_categoria
        CHECK (categoria IN ('Papelería', 'Tecnología', 'Accesorios')),

    CONSTRAINT chk_producto_precio
        CHECK (precio > 0),

    CONSTRAINT chk_producto_stock
        CHECK (stock >= 0)
);


-- Registros iniciales de productos

INSERT INTO papeleria.producto
(codigo, nombre, categoria, precio, stock)
VALUES
('P001', 'Cuaderno universitario', 'Papelería', 65.00, 25),
('P002', 'Cuaderno cuadriculado', 'Papelería', 72.50, 12),
('P003', 'Lapicero azul', 'Papelería', 12.00, 80),
('P004', 'Resma de papel carta', 'Papelería', 185.00, 0),
('T001', 'Memoria USB 32 GB', 'Tecnología', 210.00, 8),
('T002', 'Mouse inalámbrico', 'Tecnología', 320.00, 6),
('T003', 'Teclado USB', 'Tecnología', 450.00, 4),
('T004', 'Cable HDMI', 'Tecnología', 180.00, 0),
('A001', 'Mochila escolar', 'Accesorios', 850.00, 5),
('A002', 'Estuche de lápices', 'Accesorios', 95.00, 18),
('A003', 'Botella reutilizable', 'Accesorios', 240.00, 0),
('A004', 'Regla metálica', 'Accesorios', 45.00, 20);


-- Mostrar todos los productos registrados

SELECT *
FROM papeleria.producto
ORDER BY id_producto;


-- Verificar la cantidad de productos

SELECT COUNT(*) AS total_productos
FROM papeleria.producto;