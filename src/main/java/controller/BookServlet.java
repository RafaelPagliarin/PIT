package controller;

import dao.BookDAO;
import model.Book;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {

    private BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                listBooks(request, response);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "insert":
                        insertBook(request, response);
                        break;
                    case "delete":
                        deleteBook(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "update":
                        updateBook(request, response);
                        break;
                    case "view":
                        viewBook(request, response);
                        break;
                    default:
                        listBooks(request, response);
                        break;
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String search = request.getParameter("search");
        List<Book> listBook;
        if (search != null && !search.trim().isEmpty()) {
            listBook = bookDAO.findBySearch(search);
        } else {
            listBook = bookDAO.findAll();
        }
        request.setAttribute("listBook", listBook);
        request.getRequestDispatcher("book_list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("book_form.jsp").forward(request, response);
    }

    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        try {
            Book newBook = new Book();
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String yearStr = request.getParameter("year");
            String genre = request.getParameter("genre");
            String synopsis = request.getParameter("synopsis");

            // Validação básica
            validateBookFields(title, author, yearStr, genre, synopsis);

            newBook.setTitle(title);
            newBook.setAuthor(author);
            newBook.setYear(Integer.parseInt(yearStr));
            newBook.setGenre(genre);
            newBook.setSynopsis(synopsis);

            bookDAO.insert(newBook);
            response.sendRedirect("books");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("book_form.jsp").forward(request, response);
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        bookDAO.delete(id);
        response.sendRedirect("books");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = bookDAO.findById(id);
        request.setAttribute("book", existingBook);
        request.getRequestDispatcher("book_form.jsp").forward(request, response);
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Book book = new Book();

            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String yearStr = request.getParameter("year");
            String genre = request.getParameter("genre");
            String synopsis = request.getParameter("synopsis");

            // Validação básica
            validateBookFields(title, author, yearStr, genre, synopsis);

            book.setId(id);
            book.setTitle(title);
            book.setAuthor(author);
            book.setYear(Integer.parseInt(yearStr));
            book.setGenre(genre);
            book.setSynopsis(synopsis);

            bookDAO.update(book);
            response.sendRedirect("books");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("book_form.jsp").forward(request, response);
        }
    }

    private void viewBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookDAO.findById(id);
        request.setAttribute("book", book);
        request.getRequestDispatcher("book_detail.jsp").forward(request, response);
    }

    private void validateBookFields(String title, String author, String yearStr, String genre, String synopsis) {
        if (title == null || title.trim().isEmpty() || title.length() > 255) {
            throw new IllegalArgumentException("Title is required and cannot exceed 255 characters.");
        }
        if (author == null || author.trim().isEmpty() || author.length() > 255) {
            throw new IllegalArgumentException("Author is required and cannot exceed 255 characters.");
        }
        if (yearStr == null || yearStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Year is required.");
        }
        try {
            int year = Integer.parseInt(yearStr);
            if (year < 0 || year > 9999) { // Ajuste conforme necessidade
                throw new IllegalArgumentException("Year must be between 0 and 9999.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Year must be a valid number.");
        }
        if (genre == null || genre.trim().isEmpty() || genre.length() > 100) {
            throw new IllegalArgumentException("Genre is required and cannot exceed 100 characters.");
        }
        if (synopsis != null && synopsis.length() > 1000) {
            throw new IllegalArgumentException("Synopsis cannot exceed 1000 characters.");
        }
    }
}
