package dao;

import model.Movie;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas operações CRUD para a entidade Movie no banco de dados.
 */
public class MovieDAO {

    /**
     * Insere um novo filme no banco.
     * @param movie objeto Movie a ser inserido
     * @throws SQLException em caso de erro na operação SQL
     */
    public void insert(Movie movie) throws SQLException {
        String sql = "INSERT INTO movie (title, director, year, genre, synopsis) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDirector());
            stmt.setInt(3, movie.getYear());
            stmt.setString(4, movie.getGenre());
            stmt.setString(5, movie.getSynopsis());

            stmt.executeUpdate();
        }
    }

    /**
     * Busca todos os filmes no banco ordenados por id.
     * @return lista de filmes
     * @throws SQLException em caso de erro na operação SQL
     */
    public List<Movie> findAll() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie ORDER BY id";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setDirector(rs.getString("director"));
                movie.setYear(rs.getInt("year"));
                movie.setGenre(rs.getString("genre"));
                movie.setSynopsis(rs.getString("synopsis"));
                movies.add(movie);
            }
        }

        return movies;
    }

    /**
     * Busca filmes que correspondem ao termo de busca em título ou diretor.
     * @param search termo de busca
     * @return lista de filmes que atendem ao filtro
     * @throws SQLException em caso de erro na operação SQL
     */
    public List<Movie> findBySearch(String search) throws SQLException {
        List<Movie> result = new ArrayList<>();
        String sql = "SELECT * FROM movie WHERE title ILIKE ? OR director ILIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + search + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setId(rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setDirector(rs.getString("director"));
                    movie.setYear(rs.getInt("year"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setSynopsis(rs.getString("synopsis"));
                    result.add(movie);
                }
            }
        }
        return result;
    }

    /**
     * Busca um filme pelo seu ID.
     * @param id identificador do filme
     * @return objeto Movie ou null se não encontrado
     * @throws SQLException em caso de erro na operação SQL
     */
    public Movie findById(int id) throws SQLException {
        Movie movie = null;
        String sql = "SELECT * FROM movie WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    movie = new Movie();
                    movie.setId(rs.getInt("id"));
                    movie.setTitle(rs.getString("title"));
                    movie.setDirector(rs.getString("director"));
                    movie.setYear(rs.getInt("year"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setSynopsis(rs.getString("synopsis"));
                }
            }
        }

        return movie;
    }

    /**
     * Atualiza os dados de um filme existente no banco.
     * @param movie objeto Movie com os dados atualizados
     * @throws SQLException em caso de erro na operação SQL
     */
    public void update(Movie movie) throws SQLException {
        String sql = "UPDATE movie SET title = ?, director = ?, year = ?, genre = ?, synopsis = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDirector());
            stmt.setInt(3, movie.getYear());
            stmt.setString(4, movie.getGenre());
            stmt.setString(5, movie.getSynopsis());
            stmt.setInt(6, movie.getId());

            stmt.executeUpdate();
        }
    }

    /**
     * Remove um filme pelo seu ID.
     * @param id identificador do filme a ser removido
     * @throws SQLException em caso de erro na operação SQL
     */
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM movie WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
