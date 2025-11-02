package dao;

import model.Book;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static util.DatabaseUtil.getConnection;

/**
 * Classe responsável pelas operações CRUD para a entidade Book no banco de dados.
 */
public class BookDAO {

    /**
     * Insere um novo livro no banco.
     * @param book objeto Book a ser inserido
     * @throws SQLException em caso de erro na operação SQL
     */
    public void insert(Book book) throws SQLException {
        String sql = "INSERT INTO book (title, author, year, genre, synopsis) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYear());
            stmt.setString(4, book.getGenre());
            stmt.setString(5, book.getSynopsis());

            stmt.executeUpdate();
        }
    }

    /**
     * Busca todos os livros no banco ordenados por id.
     * @return lista de livros
     * @throws SQLException em caso de erro na operação SQL
     */
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book ORDER BY id";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setYear(rs.getInt("year"));
                book.setGenre(rs.getString("genre"));
                book.setSynopsis(rs.getString("synopsis"));
                books.add(book);
            }
        }

        return books;
    }

    /**
     * Busca livros que correspondem ao termo de busca em título ou autor.
     * @param search termo de busca
     * @return lista de livros que atendem ao filtro
     * @throws SQLException em caso de erro na operação SQL
     */
    public List<Book> findBySearch(String search) throws SQLException {
        List<Book> result = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE title ILIKE ? OR author ILIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + search + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setYear(rs.getInt("year"));
                    book.setGenre(rs.getString("genre"));
                    book.setSynopsis(rs.getString("synopsis"));
                    result.add(book);
                }
            }
        }
        return result;
    }

    /**
     * Busca um livro pelo seu ID.
     * @param id identificador do livro
     * @return objeto Book ou null se não encontrado
     * @throws SQLException em caso de erro na operação SQL
     */
    public Book findById(int id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM book WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setYear(rs.getInt("year"));
                    book.setGenre(rs.getString("genre"));
                    book.setSynopsis(rs.getString("synopsis"));
                }
            }
        }

        return book;
    }

    /**
     * Atualiza os dados de um livro existente no banco.
     * @param book objeto Book com os dados atualizados
     * @throws SQLException em caso de erro na operação SQL
     */
    public void update(Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, author = ?, year = ?, genre = ?, synopsis = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYear());
            stmt.setString(4, book.getGenre());
            stmt.setString(5, book.getSynopsis());
            stmt.setInt(6, book.getId());

            stmt.executeUpdate();
        }
    }

    /**
     * Remove um livro pelo seu ID.
     * @param id identificador do livro a ser removido
     * @throws SQLException em caso de erro na operação SQL
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM book WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
