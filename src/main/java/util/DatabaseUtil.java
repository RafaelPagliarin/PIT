package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/catalog";
    private static final String USER = "postgres"; // seu usuário
    private static final String PASSWORD = "300425"; // sua senha



    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver PostgreSQL não encontrado", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
