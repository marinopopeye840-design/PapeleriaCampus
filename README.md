# Papelería Campus

## Autor

**Jarold José Montealto Cantillano**

## Descripción

Papelería Campus es una aplicación de escritorio desarrollada para la asignatura Programación de Aplicaciones de Escritorio.

El sistema permite administrar un catálogo de productos utilizando JavaFX para la interfaz gráfica y PostgreSQL para el almacenamiento de la información.

La aplicación permite registrar, consultar, actualizar y eliminar productos, además de realizar búsquedas y aplicar filtros por categoría y existencia.

## Tecnologías utilizadas

- Java 21
- JavaFX 21.0.6
- PostgreSQL
- JDBC
- Maven
- IntelliJ IDEA
- Scene Builder

## Base de datos

La aplicación utiliza la base de datos:

`papeleria_campus`

Dentro de ella se utiliza el esquema:

`papeleria`

La tabla principal es:

`papeleria.producto`

Los productos almacenan la siguiente información:

- ID
- Código
- Nombre
- Categoría
- Precio
- Stock

## Funcionalidades

El sistema permite:

- Visualizar los productos registrados.
- Registrar nuevos productos.
- Actualizar productos existentes.
- Eliminar productos con confirmación.
- Buscar productos por código o nombre.
- Filtrar productos por categoría.
- Mostrar únicamente productos con existencia.
- Combinar búsqueda, categoría y existencia.
- Limpiar los filtros aplicados.
- Recargar los datos desde PostgreSQL.
- Mostrar la cantidad de registros visibles.
- Mantener los filtros después de realizar operaciones CRUD.

## Categorías disponibles

Los productos pueden pertenecer a una de las siguientes categorías:

- Papelería
- Tecnología
- Accesorios

## Validaciones

La aplicación realiza diferentes validaciones antes de guardar o actualizar un producto.

### Código

- No puede estar vacío.
- Debe contener entre 1 y 10 caracteres.
- Se eliminan espacios al inicio y al final.
- Se convierte a mayúsculas.
- No se permiten códigos duplicados.

### Nombre

- No puede estar vacío.
- Debe contener entre 3 y 80 caracteres.

### Categoría

Solo se permiten las categorías definidas en el sistema.

### Precio

- Debe ser un valor numérico.
- Debe ser mayor que 0.
- Puede tener como máximo 2 decimales.
- No puede superar `99999999.99`.

### Stock

- Debe ser un número entero.
- No puede ser negativo.
- Puede tener valor 0.
- El valor máximo permitido es `2147483647`.

## Búsqueda y filtros

La aplicación permite buscar productos utilizando el código o el nombre.

También permite filtrar por:

- Categoría.
- Productos con existencias.

Los filtros pueden utilizarse individualmente o de forma combinada.

## Manejo de errores

La aplicación maneja errores relacionados con la conexión y las operaciones realizadas en PostgreSQL.

Los errores técnicos se registran en consola utilizando el `SQLState`, mientras que al usuario se le muestran mensajes sencillos y comprensibles.

También se controla el caso en que una operación de actualización o eliminación no afecta ningún registro porque el producto ya no existe en la base de datos.

## Conexión a PostgreSQL

La conexión utiliza la siguiente dirección:

`jdbc:postgresql://localhost:5432/papeleria_campus`

Usuario:

`postgres`

La contraseña no se encuentra escrita directamente en el código.

Se obtiene mediante la variable de entorno:

`DB_PASSWORD`

En IntelliJ IDEA se puede configurar desde:

`Run → Edit Configurations → Environment variables`

Ejemplo:

`DB_PASSWORD=contraseña_de_postgresql`

La contraseña real no debe incluirse en el repositorio.

## Estructura principal del proyecto

```text
PapeleriaCampus
├── evidencias
├── src
│   └── main
│       ├── java
│       │   └── ni.edu.uam.papeleriacampus
│       │       ├── controller
│       │       │   └── ProductoController.java
│       │       ├── dao
│       │       │   └── ProductoDAO.java
│       │       ├── database
│       │       │   └── ConexionBD.java
│       │       ├── model
│       │       │   └── Producto.java
│       │       ├── HelloApplication.java
│       │       └── Launcher.java
│       └── resources
│           └── ni.edu.uam.papeleriacampus
│               └── productos-view.fxml
├── PapeleriaCampus.sql
├── pruebas.md
├── README.md
└── pom.xml