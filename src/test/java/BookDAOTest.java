import dao.BookDAO;
import model.Book;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookDAOTest {

    private BookDAO bookDAO;

    @BeforeAll
    void init() {
        bookDAO = new BookDAO();
    }

    @Test
    void testInsertAndFindBySearch() throws SQLException {
        Book book = new Book();
        book.setTitle("JUnit 5 Testing");
        book.setAuthor("Tester");
        book.setYear(2025);
        book.setGenre("Education");
        book.setSynopsis("Unit testing with JUnit 5");

        bookDAO.insert(book);

        List<Book> books = bookDAO.findBySearch("JUnit 5 Testing");
        assertFalse(books.isEmpty(), "Book should be found in search");
        Book found = books.get(0);
        assertEquals("JUnit 5 Testing", found.getTitle());
        assertEquals("Tester", found.getAuthor());
    }

    @Test
    void testFindAll() throws SQLException {
        List<Book> allBooks = bookDAO.findAll();
        assertNotNull(allBooks);
    }

    @Test
    void testUpdateAndFindById() throws SQLException {
        // Insert a book to update
        Book book = new Book();
        book.setTitle("Initial Title");
        book.setAuthor("Initial Author");
        book.setYear(2020);
        book.setGenre("Test");
        book.setSynopsis("Initial synopsis");
        bookDAO.insert(book);

        List<Book> list = bookDAO.findBySearch("Initial Title");
        assertFalse(list.isEmpty());
        Book toUpdate = list.get(0);

        toUpdate.setTitle("Updated Title");
        toUpdate.setAuthor("Updated Author");
        bookDAO.update(toUpdate);

        Book updated = bookDAO.findById(toUpdate.getId());
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated Author", updated.getAuthor());
    }

    @Test
    void testDelete() throws SQLException {
        Book book = new Book();
        book.setTitle("To Be Deleted");
        book.setAuthor("Author");
        book.setYear(2023);
        book.setGenre("Fiction");
        book.setSynopsis("This book will be deleted");

        bookDAO.insert(book);

        List<Book> list = bookDAO.findBySearch("To Be Deleted");
        assertFalse(list.isEmpty());

        Book toDelete = list.get(0);
        bookDAO.delete(toDelete.getId());

        Book deleted = bookDAO.findById(toDelete.getId());
        assertNull(deleted, "Deleted book should be null");
    }
}
