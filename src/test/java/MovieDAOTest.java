import dao.MovieDAO;
import model.Movie;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieDAOTest {

    private MovieDAO movieDAO;

    @BeforeAll
    void init() {
        movieDAO = new MovieDAO();
    }

    @Test
    void testInsertAndFindBySearch() throws SQLException {
        Movie movie = new Movie();
        movie.setTitle("JUnit 5 Movie");
        movie.setDirector("Director Tester");
        movie.setYear(2025);
        movie.setGenre("Documentary");
        movie.setSynopsis("Testing DAO with JUnit 5");

        movieDAO.insert(movie);

        List<Movie> movies = movieDAO.findBySearch("JUnit 5 Movie");
        assertFalse(movies.isEmpty(), "Movie should be found by search");
        Movie found = movies.get(0);
        assertEquals("JUnit 5 Movie", found.getTitle());
        assertEquals("Director Tester", found.getDirector());
    }

    @Test
    void testFindAll() throws SQLException {
        List<Movie> allMovies = movieDAO.findAll();
        assertNotNull(allMovies);
    }

    @Test
    void testUpdateAndFindById() throws SQLException {
        Movie movie = new Movie();
        movie.setTitle("Initial Movie");
        movie.setDirector("Initial Director");
        movie.setYear(2020);
        movie.setGenre("Test");
        movie.setSynopsis("Initial synopsis");
        movieDAO.insert(movie);

        List<Movie> list = movieDAO.findBySearch("Initial Movie");
        assertFalse(list.isEmpty());
        Movie toUpdate = list.get(0);

        toUpdate.setTitle("Updated Movie");
        toUpdate.setDirector("Updated Director");
        movieDAO.update(toUpdate);

        Movie updated = movieDAO.findById(toUpdate.getId());
        assertEquals("Updated Movie", updated.getTitle());
        assertEquals("Updated Director", updated.getDirector());
    }

    @Test
    void testDelete() throws SQLException {
        Movie movie = new Movie();
        movie.setTitle("To Be Deleted Movie");
        movie.setDirector("Director");
        movie.setYear(2023);
        movie.setGenre("Fiction");
        movie.setSynopsis("This movie will be deleted");

        movieDAO.insert(movie);

        List<Movie> list = movieDAO.findBySearch("To Be Deleted Movie");
        assertFalse(list.isEmpty());

        Movie toDelete = list.get(0);
        movieDAO.delete(toDelete.getId());

        Movie deleted = movieDAO.findById(toDelete.getId());
        assertNull(deleted, "Deleted movie should be null");
    }
}
