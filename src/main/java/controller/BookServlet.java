package controller;

import dao.BookDAO;
import model.Book;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

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

    // POST method delegates to doGet for form submissions
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

    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Book newBook = new Book();
        newBook.setTitle(request.getParameter("title"));
        newBook.setAuthor(request.getParameter("author"));
        newBook.setYear(Integer.parseInt(request.getParameter("year")));
        newBook.setGenre(request.getParameter("genre"));
        newBook.setSynopsis(request.getParameter("synopsis"));

        bookDAO.insert(newBook);
        response.sendRedirect("books");
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

    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = new Book();
        book.setId(id);
        book.setTitle(request.getParameter("title"));
        book.setAuthor(request.getParameter("author"));
        book.setYear(Integer.parseInt(request.getParameter("year")));
        book.setGenre(request.getParameter("genre"));
        book.setSynopsis(request.getParameter("synopsis"));

        bookDAO.update(book);
        response.sendRedirect("books");
    }

    private void viewBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookDAO.findById(id);
        request.setAttribute("book", book);
        request.getRequestDispatcher("book_detail.jsp").forward(request, response);
    }
}
