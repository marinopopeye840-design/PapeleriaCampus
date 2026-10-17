package ni.edu.uam.papeleriacampus.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ni.edu.uam.papeleriacampus.dao.ProductoDAO;
import ni.edu.uam.papeleriacampus.model.Producto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProductoController {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtBuscar;

    @FXML
    private ComboBox<String> cmbCategoriaFiltro;

    @FXML
    private CheckBox chkExistencia;

    @FXML
    private TableView<Producto> btlProductos;

    @FXML
    private TableColumn<Producto, Integer> colId;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, BigDecimal> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private Label lblContador;

    private final ObservableList<Producto> listaProductos =
            FXCollections.observableArrayList();

    private final ProductoDAO productoDAO = new ProductoDAO();

    private Producto productoSeleccionado;


    @FXML
    public void initialize() {
        configurarTabla();
        configurarComboBox();
        configurarSeleccionTableView();
        cargarProductos();
    }


    private void actualizarContador() {
        lblContador.setText("Registros visibles: " + listaProductos.size());
    }


    private void configurarTabla() {

        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getIdProducto()).asObject());
        colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigo()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colCategoria.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategoria()));
        colPrecio.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getPrecio()));
        colStock.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getStock()).asObject());

        btlProductos.setItems(listaProductos);
    }


    private void configurarComboBox() {

        cmbCategoria.setItems(
                FXCollections.observableArrayList("Papelería",
                        "Tecnología",
                        "Accesorios"
                )
        );

        cmbCategoriaFiltro.setItems(
                FXCollections.observableArrayList("Todas",
                        "Papelería",
                        "Tecnología",
                        "Accesorios"
                )
        );

        cmbCategoriaFiltro.setValue("Todas");
    }


    private void configurarSeleccionTableView() {

        btlProductos.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {
                        productoSeleccionado = seleccionado;
                        cargarProductoEnFormulario(seleccionado);
                    }

                });
    }
    private void cargarProductoEnFormulario(Producto producto) {
        txtCodigo.setText(producto.getCodigo());
        txtNombre.setText(producto.getNombre());
        cmbCategoria.setValue(producto.getCategoria());
        txtPrecio.setText(producto.getPrecio().toString());
        txtStock.setText(String.valueOf(producto.getStock()));
    }
    private void cargarProductos() {
        listaProductos.clear();
        try {
            List<Producto> productos = productoDAO.listarProductos();
            listaProductos.addAll(productos);
            actualizarContador();
        } catch (SQLException e) {

            mostrarErrorBaseDatos("cargar productos", e);

        } catch (Exception e) {

            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error",
                    "No se pudieron cargar los productos.");
        }
    }


    private void recargarBusqueda() {

        String texto = txtBuscar.getText().trim();
        String categoria = cmbCategoriaFiltro.getValue();
        boolean soloExistencia = chkExistencia.isSelected();

        try {

            List<Producto> productos =
                    productoDAO.buscarProductos(
                            texto,
                            categoria,
                            soloExistencia
                    );

            listaProductos.clear();
            listaProductos.addAll(productos);

            actualizarContador();

        } catch (SQLException e) {

            mostrarErrorBaseDatos(
                    "recargar búsqueda",
                    e
            );

        } catch (Exception e) {

            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error",
                    "No se pudieron recargar los productos.");
        }
    }


    @FXML
    private void nuevoProducto() {

        limpiarFormulario();
        productoSeleccionado = null;
        btlProductos.getSelectionModel().clearSelection();
        txtCodigo.requestFocus();
    }


    @FXML
    private void guardarProducto() {

        if (!validarCampos()) {
            return;
        }

        try {

            String codigo = txtCodigo.getText().trim().toUpperCase();
            String nombre = txtNombre.getText().trim();
            String categoria = cmbCategoria.getValue();

            BigDecimal precio =
                    new BigDecimal(txtPrecio.getText().trim());

            int stock =
                    Integer.parseInt(txtStock.getText().trim());


            if (productoDAO.existeCodigo(codigo, null)) {

                mostrarAlerta(Alert.AlertType.WARNING,
                        "Código duplicado",
                        "Ya existe un producto con ese código.");

                return;
            }


            Producto producto =
                    new Producto(
                            0,
                            codigo,
                            nombre,
                            categoria,
                            precio,
                            stock
                    );

            boolean guardado =
                    productoDAO.insertarProducto(producto);


            if (guardado) {

                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Producto guardado",
                        "El producto fue guardado correctamente.");

                limpiarFormulario();
                recargarBusqueda();

            } else {

                mostrarAlerta(Alert.AlertType.WARNING,
                        "Aviso",
                        "No se pudo guardar el producto.");
            }

        } catch (SQLException e) {

            mostrarErrorBaseDatos(
                    "guardar producto",
                    e
            );

        } catch (Exception e) {

            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error",
                    "Ocurrió un error al guardar el producto.");
        }
    }


    @FXML
    private void actualizarProducto() {

        if (productoSeleccionado == null) {

            mostrarAlerta(Alert.AlertType.WARNING,
                    "Producto no seleccionado",
                    "Seleccione un producto de la tabla.");

            return;
        }


        if (!validarCampos()) {
            return;
        }


        try {

            String codigo = txtCodigo.getText().trim().toUpperCase();
            String nombre = txtNombre.getText().trim();
            String categoria = cmbCategoria.getValue();

            BigDecimal precio =
                    new BigDecimal(txtPrecio.getText().trim());

            int stock =
                    Integer.parseInt(txtStock.getText().trim());


            if (productoDAO.existeCodigo(
                    codigo,
                    productoSeleccionado.getIdProducto()
            )) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Código duplicado",
                        "Ya existe otro producto con ese código.");

                return;
            }


            productoSeleccionado.setCodigo(codigo);
            productoSeleccionado.setNombre(nombre);
            productoSeleccionado.setCategoria(categoria);
            productoSeleccionado.setPrecio(precio);
            productoSeleccionado.setStock(stock);


            boolean actualizado =
                    productoDAO.actualizarProducto(productoSeleccionado);


            if (actualizado) {

                mostrarAlerta(Alert.AlertType.INFORMATION, "Producto actualizado",
                        "El producto fue actualizado correctamente.");

                limpiarFormulario();
                productoSeleccionado = null;

                recargarBusqueda();

            } else {

                mostrarAlerta(Alert.AlertType.WARNING,
                        "Aviso",
                        "El producto ya no existe o no pudo actualizarse.");
            }

        } catch (SQLException e) {

            mostrarErrorBaseDatos(
                    "actualizar producto", e);

        } catch (Exception e) {

            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error",
                    "Ocurrió un error al actualizar el producto.");
        }
    }


    @FXML
    private void eliminarProducto() {

        if (productoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Producto no seleccionado",
                    "Seleccione un producto de la tabla.");

            return;
        }


        Alert confirmacion =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Eliminar producto");
        confirmacion.setHeaderText(null);

        confirmacion.setContentText(
                "¿Desea eliminar el producto "
                        + productoSeleccionado.getNombre()
                        + "?"
        );


        confirmacion.showAndWait().ifPresent(respuesta -> {

            if (respuesta == ButtonType.OK) {

                try {

                    boolean eliminado =
                            productoDAO.eliminarProducto(productoSeleccionado.getIdProducto()
                            );


                    if (eliminado) {

                        mostrarAlerta(Alert.AlertType.INFORMATION,
                                "Producto eliminado",
                                "El producto fue eliminado correctamente.");
                        limpiarFormulario();
                        productoSeleccionado = null;
                        recargarBusqueda();

                    } else {

                        mostrarAlerta(Alert.AlertType.WARNING,
                                "Aviso",
                                "El producto ya no existe.");
                    }

                } catch (SQLException e) {
                    mostrarErrorBaseDatos("eliminar producto", e);
                } catch (Exception e) {

                    mostrarAlerta(Alert.AlertType.ERROR,
                            "Error",
                            "No se pudo eliminar el producto.");
                }
            }
        });
    }


    @FXML
    private void buscar() {

        try {

            String texto =
                    txtBuscar.getText().trim();

            String categoria =
                    cmbCategoriaFiltro.getValue();

            boolean soloExistencia =
                    chkExistencia.isSelected();


            List<Producto> productos = productoDAO.buscarProductos(texto, categoria, soloExistencia);
            listaProductos.clear();
            listaProductos.addAll(productos);
            actualizarContador();
            if (productos.isEmpty()) {
                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Sin coincidencias",
                        "No se encontraron productos.");
            }

        } catch (SQLException e) {
            mostrarErrorBaseDatos("buscar productos", e);

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo realizar la búsqueda."
            );
        }
    }
    @FXML
    private void limpiarFiltro() {
        txtBuscar.clear();
        cmbCategoriaFiltro.setValue("Todas");
        chkExistencia.setSelected(false);
        cargarProductos();
    }
    @FXML
    private void recargar() {
        cargarProductos();
    }
    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        cmbCategoria.setValue(null);
        txtPrecio.clear();
        txtStock.clear();
    }
    private boolean validarCampos() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        if (codigo.isEmpty() || codigo.length() > 10) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Código inválido",
                    "El código debe tener entre 1 y 10 caracteres."
            );
            return false;
        }
        if (nombre.length() < 3 || nombre.length() > 80) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Nombre inválido",
                    "El nombre debe tener entre 3 y 80 caracteres."
            );
            return false;
        }
        if (cmbCategoria.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Categoría",
                    "Seleccione una categoría."
            );

            return false;
        }
        try {
            BigDecimal precio = new BigDecimal(txtPrecio.getText().trim());
            if (precio.compareTo(BigDecimal.ZERO) <= 0) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Precio inválido",
                        "El precio debe ser mayor que 0."
                );

                return false;
            }
            if (precio.scale() > 2) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Precio inválido",
                        "El precio solo puede tener hasta 2 decimales."
                );

                return false;
            }

            if (precio.compareTo(new BigDecimal("99999999.99")
            ) > 0) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Precio inválido",
                        "El precio supera el valor permitido."
                );

                return false;
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Precio inválido",
                    "Ingrese un precio válido."
            );
            return false;
        }
        try {
            long stock = Long.parseLong(txtStock.getText().trim());
            if (stock < 0 || stock > Integer.MAX_VALUE) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Stock inválido",
                        "Ingrese un stock entre 0 y 2147483647."
                );

                return false;
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Stock inválido",
                    "El stock debe ser un número entero."
            );
            return false;
        }

        return true;
    }

    private void mostrarErrorBaseDatos(String operacion, SQLException e) {
        System.err.println("Error en " + operacion
        );
        System.err.println("SQLState: " + e.getSQLState()
        );
        System.err.println("Mensaje: " + e.getMessage()
        );
        mostrarAlerta(Alert.AlertType.ERROR,
                "Error de base de datos",
                "Ocurrió un problema al realizar la operación."
        );
    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}