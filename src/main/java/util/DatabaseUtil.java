package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitário para gerenciar conexões com o banco de dados PostgreSQL.
 */
public class DatabaseUtil {
    /** URL da conexão com o banco PostgreSQL */
    private static final String URL = "jdbc:postgresql://localhost:5432/catalog";

    /** Usuário de conexão do banco */
    private static final String USER = "postgres"; // seu usuário

    /** Senha de conexão do banco */
    private static final String PASSWORD = "300425"; // sua senha

    /**
     * Obtém uma conexão ativa com o banco de dados PostgreSQL.
     *
     * @return objeto Connection para acessos SQL
     * @throws SQLException em caso de falha na conexão
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Tenta carregar o driver PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // Se driver não for encontrado, lança exceção de runtime
            throw new RuntimeException("Driver PostgreSQL não encontrado", e);
        }
        // Retorna a conexão ao banco com as credenciais fornecidas
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

