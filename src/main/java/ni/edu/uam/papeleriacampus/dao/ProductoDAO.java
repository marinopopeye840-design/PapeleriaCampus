package ni.edu.uam.papeleriacampus.dao;

import ni.edu.uam.papeleriacampus.database.ConexionBD;
import ni.edu.uam.papeleriacampus.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public List<Producto> listarProductos() throws SQLException {

        List<Producto> lista = new ArrayList<>();

        String sql = """
                SELECT id_producto, codigo, nombre, categoria, precio, stock
                FROM papeleria.producto
                ORDER BY codigo
                """;

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Producto producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getBigDecimal("precio"),
                        rs.getInt("stock")
                );

                lista.add(producto);
            }
        }

        return lista;
    }



    public boolean insertarProducto(Producto producto) throws SQLException {

        String sql = """
                INSERT INTO papeleria.producto
                (codigo, nombre, categoria, precio, stock)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getCategoria());
            ps.setBigDecimal(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());

            int filas = ps.executeUpdate();

            return filas > 0;
        }
    }


    public boolean actualizarProducto(Producto producto) throws SQLException {

        String sql = """
                UPDATE papeleria.producto
                SET codigo = ?,
                    nombre = ?,
                    categoria = ?,
                    precio = ?,
                    stock = ?
                WHERE id_producto = ?
                """;

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getCategoria());
            ps.setBigDecimal(4, producto.getPrecio());
            ps.setInt(5, producto.getStock());
            ps.setInt(6, producto.getIdProducto());

            int filas = ps.executeUpdate();

            return filas > 0;
        }
    }


    public boolean eliminarProducto(int idProducto) throws SQLException {

        String sql = """
                DELETE FROM papeleria.producto
                WHERE id_producto = ?
                """;

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            int filas = ps.executeUpdate();

            return filas > 0;
        }
    }


    public boolean existeCodigo(String codigo, Integer idExcluir) throws SQLException {

        String sql;

        if (idExcluir == null) {

            sql = """
                    SELECT COUNT(*)
                    FROM papeleria.producto
                    WHERE UPPER(TRIM(codigo)) = UPPER(TRIM(?))
                    """;

        } else {

            sql = """
                    SELECT COUNT(*)
                    FROM papeleria.producto
                    WHERE UPPER(TRIM(codigo)) = UPPER(TRIM(?))
                    AND id_producto <> ?
                    """;
        }

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, codigo);

            if (idExcluir != null) {
                ps.setInt(2, idExcluir);
            }

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }


    public List<Producto> buscarProductos(String texto,
                                          String categoria,
                                          boolean soloExistencia)
            throws SQLException {

        List<Producto> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT id_producto, codigo, nombre, categoria, precio, stock
                FROM papeleria.producto
                WHERE 1 = 1
                """);

        List<Object> parametros = new ArrayList<>();


        if (texto != null && !texto.isBlank()) {

            sql.append("""
                    
                    AND (codigo ILIKE ? OR nombre ILIKE ?)
                    """);

            String busqueda = "%" + texto.trim() + "%";

            parametros.add(busqueda);
            parametros.add(busqueda);
        }


        if (categoria != null && !categoria.equals("Todas")) {

            sql.append("""
                    
                    AND categoria = ?
                    """);

            parametros.add(categoria);
        }


        if (soloExistencia) {

            sql.append("""
                    
                    AND stock > 0
                    """);
        }


        sql.append("""
                
                ORDER BY codigo
                """);


        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement ps = conexion.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                ps.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Producto producto = new Producto(rs.getInt("id_producto"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("categoria"),
                            rs.getBigDecimal("precio"),
                            rs.getInt("stock")
                    );

                    lista.add(producto);
                }
            }
        }

        return lista;
    }
}