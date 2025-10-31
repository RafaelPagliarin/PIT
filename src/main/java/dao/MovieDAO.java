package dao;

import model.Movie;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

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

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM movie WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
