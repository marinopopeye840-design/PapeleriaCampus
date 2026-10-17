# Pruebas realizadas - Papelería Campus

## Descripción

En este documento se registran las pruebas realizadas al sistema de gestión de productos de Papelería Campus.

Las pruebas permiten comprobar el funcionamiento de la búsqueda y filtros, las operaciones CRUD, las validaciones de los campos y el manejo de errores relacionados con la base de datos PostgreSQL.

---

# 1. Listado inicial

## Prueba inicial

**Objetivo:** Comprobar que al iniciar la aplicación se carguen correctamente los productos registrados en PostgreSQL.

**Resultado esperado:**  
La tabla debe mostrar los 12 productos almacenados en la base de datos y el contador debe indicar 12 registros visibles.

**Resultado obtenido:**  
La aplicación cargó correctamente los productos desde PostgreSQL y mostró los registros en el TableView.

**Estado:** APROBADO

**Evidencia:**  
`evidencias/listado_Inicial.png.png`

---

# 2. Pruebas de búsqueda y filtros

## B1 - Búsqueda por CUADERNO

**Entrada:**

- Texto: `CUADERNO`
- Categoría: `Todas`
- Solo con existencias: No

**Resultado esperado:**  
Mostrar los productos P001 y P002.

**Registros esperados:** 2

**Resultado obtenido:**  
Se mostraron correctamente P001 y P002.

**Registros obtenidos:** 2

**Estado:** APROBADO

---

## B2 - Búsqueda por código T00

**Entrada:**

- Texto: `T00`
- Categoría: `Todas`
- Solo con existencias: No

**Resultado esperado:**  
Mostrar T001, T002, T003 y T004.

**Registros esperados:** 4

**Resultado obtenido:**  
Se mostraron correctamente los cuatro productos cuyo código contiene T00.

**Registros obtenidos:** 4

**Estado:** APROBADO

---

## B3 - Filtro por categoría Papelería

**Entrada:**

- Texto: vacío
- Categoría: `Papelería`
- Solo con existencias: No

**Resultado esperado:**  
Mostrar P001, P002, P003 y P004.

**Registros esperados:** 4

**Resultado obtenido:**  
La aplicación mostró correctamente los cuatro productos de la categoría Papelería.

**Registros obtenidos:** 4

**Estado:** APROBADO

---

## B4 - Productos con existencias

**Entrada:**

- Texto: vacío
- Categoría: `Todas`
- Solo con existencias: Sí

**Resultado esperado:**  
Mostrar solamente productos con stock mayor que 0.

No deben aparecer P004, T004 ni A003.

**Registros esperados:** 9

**Resultado obtenido:**  
La aplicación mostró únicamente los productos que tenían existencias disponibles.

**Registros obtenidos:** 9

**Estado:** APROBADO

---

## B5 - Filtro combinado

**Entrada:**

- Texto: `USB`
- Categoría: `Tecnología`
- Solo con existencias: Sí

**Resultado esperado:**  
Mostrar T001 y T003.

**Registros esperados:** 2

**Resultado obtenido:**  
Se mostraron correctamente T001 y T003.

**Registros obtenidos:** 2

**Estado:** APROBADO

**Evidencia:**  
`evidencias/evidencias_filtro_combinado.png.png`

---

## B6 - Búsqueda sin coincidencias

**Entrada:**

- Texto: `ZZZ999`
- Categoría: `Todas`
- Solo con existencias: No

**Resultado esperado:**  
No mostrar productos y presentar un mensaje indicando que no existen coincidencias.

**Registros esperados:** 0

**Resultado obtenido:**  
La tabla quedó sin registros, el contador mostró 0 y apareció el mensaje indicando que no se encontraron productos.

**Estado:** APROBADO

---

## B7 - Limpiar filtros

**Procedimiento:**  
Después de aplicar filtros se presionó el botón `Limpiar filtro`.

**Resultado esperado:**  
Limpiar el texto de búsqueda, colocar la categoría en Todas, desmarcar Solo con existencias y volver a mostrar los productos.

**Resultado obtenido:**  
Los filtros fueron limpiados correctamente y la tabla volvió a mostrar todos los registros.

**Estado:** APROBADO

---

# 3. Pruebas CRUD

## Crear producto

**Datos utilizados:**

- Código: `X001`
- Nombre: `Producto de prueba`
- Categoría: `Accesorios`
- Precio: `10.00`
- Stock: `1`

**Resultado esperado:**  
El producto debe guardarse correctamente y aparecer en la tabla.

**Resultado obtenido:**  
El producto fue registrado correctamente en PostgreSQL y apareció en el TableView.

**Estado:** APROBADO

---

## Actualizar producto

**Producto:** `X001`

**Cambios realizados:**

- Precio: `15.50`
- Stock: `3`

**Resultado esperado:**  
Actualizar el producto sin crear un nuevo registro.

**Resultado obtenido:**  
El producto fue actualizado correctamente y se conservaron los demás datos.

**Estado:** APROBADO

**Evidencia:**  
`evidencias/evidencia_actualizar.png.png`

---

## Cancelar eliminación

**Producto:** `X001`

**Procedimiento:**  
Se presionó Eliminar y posteriormente se canceló la confirmación.

**Resultado esperado:**  
El producto debe permanecer registrado.

**Resultado obtenido:**  
La eliminación fue cancelada y el producto permaneció en la base de datos.

**Estado:** APROBADO

---

## Confirmar eliminación

**Producto:** `X001`

**Procedimiento:**  
Se presionó Eliminar y se confirmó la operación.

**Resultado esperado:**  
Eliminar el producto y actualizar la tabla.

**Resultado obtenido:**  
El producto fue eliminado correctamente.

**Estado:** APROBADO

**Evidencia:**  
`evidencias/evidencia_eliminacion.png`

---

## Mantener filtros después de operaciones CRUD

**Procedimiento:**  
Se seleccionó la categoría Tecnología y posteriormente se actualizó uno de los productos visibles.

**Resultado esperado:**  
Después de actualizar, el filtro Tecnología debe mantenerse activo.

**Resultado obtenido:**  
El filtro permaneció aplicado después de realizar la actualización y se continuaron mostrando solamente los productos de Tecnología.

**Estado:** APROBADO

---

# 4. Validaciones

## V1 - Código

**Pruebas realizadas:**

- Código vacío.
- Código formado únicamente por espacios.
- Código con más de 10 caracteres.

**Resultado esperado:**  
Rechazar los valores y exigir un código entre 1 y 10 caracteres después de eliminar espacios al inicio y al final.

**Resultado obtenido:**  
La aplicación mostró:

`El código debe tener entre 1 y 10 caracteres.`

**Estado:** APROBADO

---

## V2 - Nombre

**Pruebas realizadas:**

- Nombre formado únicamente por espacios.
- Nombre `AB`.
- Nombre de 81 caracteres.

**Resultado esperado:**  
Rechazar los tres casos porque el nombre debe contener entre 3 y 80 caracteres.

**Resultado obtenido:**  
Los tres valores fueron rechazados y apareció:

`El nombre debe tener entre 3 y 80 caracteres.`

**Estado:** APROBADO

---

## V3 - Categoría

**Prueba realizada:**  
Se intentó guardar un producto sin seleccionar categoría.

**Resultado esperado:**  
Bloquear el registro.

**Resultado obtenido:**  
La aplicación mostró:

`Seleccione una categoría.`

Además, el ComboBox solamente permite seleccionar las categorías establecidas en el sistema.

**Estado:** APROBADO

---

## V4 - Precio con texto

**Entrada:**  
`abc`

**Resultado esperado:**  
Rechazar el valor sin cerrar la aplicación.

**Resultado obtenido:**  
La aplicación mostró:

`Ingrese un precio válido.`

La aplicación continuó funcionando normalmente.

**Estado:** APROBADO

---

## V5 - Precio igual o menor que cero

**Entradas:**

- `0`
- `-1`

**Resultado esperado:**  
Rechazar ambos valores.

**Resultado obtenido:**  
Los dos valores fueron rechazados y apareció:

`El precio debe ser mayor que 0.`

**Estado:** APROBADO

---

## V6 - Decimales y límite del precio

### Caso 1

**Entrada:**  
`12.345`

**Resultado esperado:**  
Rechazar el valor por contener más de dos decimales.

**Resultado obtenido:**  
La aplicación mostró:

`El precio solo puede tener hasta 2 decimales.`

### Caso 2

**Entrada:**  
`100000000.00`

**Resultado esperado:**  
Rechazar el valor porque supera el máximo permitido.

**Resultado obtenido:**  
La aplicación mostró:

`El precio supera el valor permitido.`

**Estado:** APROBADO

---

## V7 - Stock no entero

**Entradas:**

- `abc`
- `2.5`

**Resultado esperado:**  
Rechazar ambos valores.

**Resultado obtenido:**  
La aplicación mostró:

`El stock debe ser un número entero.`

**Estado:** APROBADO

---

## V8 - Límites del stock

### Caso 1

**Entrada:**  
`-1`

**Resultado esperado:**  
Rechazar el valor.

**Resultado obtenido:**  
El valor fue rechazado correctamente.

### Caso 2

**Entrada:**  
`2147483648`

**Resultado esperado:**  
Rechazar el valor.

**Resultado obtenido:**  
El valor fue rechazado correctamente.

### Caso 3

**Entrada:**  
`0`

**Resultado esperado:**  
Permitir guardar el producto.

**Resultado obtenido:**  
El producto fue guardado correctamente con stock 0.

**Estado:** APROBADO

---

## V9 - Código duplicado

### Caso 1

**Entrada:**  
` p001 `

**Resultado esperado:**  
Después de normalizar el código, detectar que P001 ya existe.

**Resultado obtenido:**  
La aplicación mostró:

`Ya existe un producto con ese código.`

### Caso 2

Se seleccionó otro producto y se intentó cambiar su código a `P001`.

**Resultado esperado:**  
Rechazar la actualización.

**Resultado obtenido:**  
La aplicación mostró:

`Ya existe otro producto con ese código.`

### Caso 3

Se seleccionó el propio producto P001 y se realizó una actualización conservando el código P001.

**Resultado esperado:**  
Permitir la actualización.

**Resultado obtenido:**  
La actualización se realizó correctamente.

**Estado:** APROBADO

**Evidencia:**  
`evidencias/evidencias_validacion.png.png`

---

## V10 - Actualizar o eliminar sin selección

### Actualizar

Se presionó Actualizar sin seleccionar una fila.

**Resultado esperado:**  
Bloquear la operación.

**Resultado obtenido:**  
La aplicación mostró:

`Seleccione un producto de la tabla.`

### Eliminar

Se presionó Eliminar sin seleccionar una fila.

**Resultado esperado:**  
Bloquear la operación.

**Resultado obtenido:**  
La aplicación mostró el mismo aviso indicando que debía seleccionarse un producto.

**Estado:** APROBADO

---

# 5. Pruebas de excepciones y operaciones fallidas

## E1 - Error de conexión

**Procedimiento:**  
Se modificó temporalmente el puerto de PostgreSQL de `5432` a `5433`.

**Resultado esperado:**  
Mostrar un mensaje comprensible, mantener la aplicación abierta y no presentar el error como una consulta válida sin resultados.

**Resultado obtenido:**  
La aplicación mostró un mensaje de error de base de datos.

Después de aceptar el mensaje, la interfaz permaneció abierta y la aplicación no se cerró inesperadamente.

Posteriormente se restauró el puerto `5432` y la conexión volvió a funcionar normalmente.

**Estado:** APROBADO

---

## E2 - Restricción SQL

**Procedimiento:**  
Se creó temporalmente una clase `PruebaDAO` para llamar directamente al método `insertarProducto()` del DAO.

Se intentó insertar nuevamente el código:

`P001`

sin utilizar las validaciones del Controller.

**Resultado esperado:**  
PostgreSQL debe rechazar el registro debido a la restricción de unicidad.

**Resultado obtenido en consola:**

`Error al insertar producto.`

`SQLState: 23505`

PostgreSQL informó que la llave correspondiente al código P001 ya existía.

El producto duplicado no fue insertado.

Después de realizar la prueba se eliminó la clase temporal `PruebaDAO`.

**Estado:** APROBADO

---

## E3 - Cero filas afectadas

### Actualización

Se creó un producto temporal y se seleccionó desde JavaFX.

Sin recargar la aplicación, el producto fue eliminado directamente desde PostgreSQL.

Posteriormente se intentó actualizar desde JavaFX el producto que todavía estaba cargado en memoria.

**Resultado esperado:**  
La aplicación no debe mostrar un mensaje falso de actualización exitosa.

**Resultado obtenido:**  
La aplicación mostró:

`El producto ya no existe o no pudo actualizarse.`

**Estado:** APROBADO

### Eliminación

Se creó otro producto temporal y se seleccionó desde JavaFX.

Posteriormente fue eliminado directamente desde PostgreSQL sin recargar la aplicación.

Después se intentó eliminar nuevamente desde JavaFX.

**Resultado esperado:**  
La aplicación debe detectar que ninguna fila fue eliminada.

**Resultado obtenido:**  
La aplicación mostró:

`El producto ya no existe.`

**Estado:** APROBADO

---

## E4 - Registro de SQLState

**Procedimiento:**  
Se modificó temporalmente una consulta del DAO para utilizar una tabla inexistente:

`papeleria.producto_prueba`

**Resultado esperado:**  
Registrar la operación y el SQLState en consola y mostrar al usuario solamente un mensaje sencillo.

**Resultado obtenido en consola:**

`Error en cargar productos`

`SQLState: 42P01`

La consola informó que la relación `papeleria.producto_prueba` no existía.

En la interfaz se mostró:

`Ocurrió un problema al realizar la operación.`

La aplicación permaneció abierta y no mostró información técnica al usuario.

Después de realizar la prueba se restauró la consulta original:

`papeleria.producto`

**Estado:** APROBADO

---

# 6. Resultado general

Las pruebas realizadas permitieron comprobar el funcionamiento de las principales operaciones del sistema.

Se verificaron correctamente:

- carga de productos desde PostgreSQL;
- búsqueda por código y nombre;
- filtros por categoría;
- filtro por existencia;
- combinación de filtros;
- contador de registros visibles;
- registro de productos;
- actualización de productos;
- eliminación con confirmación;
- mantenimiento de filtros después de operaciones CRUD;
- validaciones de código;
- validaciones de nombre;
- validación de categoría;
- validaciones de precio;
- validaciones de stock;
- prevención de códigos duplicados;
- manejo de operaciones sin selección;
- manejo de errores de conexión;
- restricciones de PostgreSQL;
- operaciones que afectan cero filas;
- registro de SQLState;
- mensajes de error comprensibles para el usuario.

El sistema respondió correctamente durante las pruebas realizadas y no presentó cierres inesperados.