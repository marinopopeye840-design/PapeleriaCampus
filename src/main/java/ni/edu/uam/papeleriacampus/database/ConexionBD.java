package ni.edu.uam.papeleriacampus.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:postgresql://localhost:5432/papeleria_campus";

    private static final String USUARIO = "postgres";

    private static final String CONTRASENA =
            System.getenv("DB_PASSWORD");


    public static Connection getConexion() throws SQLException {

        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}