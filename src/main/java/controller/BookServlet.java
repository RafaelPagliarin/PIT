package controller;

import dao.BookDAO;
import model.Book;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet responsável pelos processos de CRUD de livros.
 * Controla requisições HTTP relacionadas ao gerenciamento de livros.
 */
@WebServlet("/books")
public class BookServlet extends HttpServlet {

    /** Objeto DAO para operações com banco de dados de livros */
    private BookDAO bookDAO = new BookDAO();

    /**
     * Processa requisições GET.
     * Roteia ações de acordo com o parâmetro 'action'.
     */
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

    /**
     * Processa requisições POST, delegando ao método doGet.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Lista todos os livros, com opção de busca.
     */
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

    /**
     * Exibe o formulário para inserção de novo livro.
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("book_form.jsp").forward(request, response);
    }

    /**
     * Insere um novo livro no banco após validação.
     */
    private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        try {
            Book newBook = new Book();

            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String yearStr = request.getParameter("year");
            String genre = request.getParameter("genre");
            String synopsis = request.getParameter("synopsis");

            // Valida os dados recebidos
            validateBookFields(title, author, yearStr, genre, synopsis);

            newBook.setTitle(title);
            newBook.setAuthor(author);
            newBook.setYear(Integer.parseInt(yearStr));
            newBook.setGenre(genre);
            newBook.setSynopsis(synopsis);

            // Insere o livro no banco
            bookDAO.insert(newBook);
            response.sendRedirect("books");
        } catch (IllegalArgumentException e) {
            // Em caso de erro na validação, retorna para o formulário com a mensagem de erro
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("book_form.jsp").forward(request, response);
        }
    }

    /**
     * Remove um livro pelo seu ID.
     */
    private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        bookDAO.delete(id);
        response.sendRedirect("books");
    }

    /**
     * Exibe o formulário de edição com os dados existentes do livro.
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book existingBook = bookDAO.findById(id);
        request.setAttribute("book", existingBook);
        request.getRequestDispatcher("book_form.jsp").forward(request, response);
    }

    /**
     * Atualiza os dados do livro após validação.
     */
    private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Book book = new Book();

            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String yearStr = request.getParameter("year");
            String genre = request.getParameter("genre");
            String synopsis = request.getParameter("synopsis");

            // Valida os dados de entrada
            validateBookFields(title, author, yearStr, genre, synopsis);

            // Atualiza o objeto livro
            book.setId(id);
            book.setTitle(title);
            book.setAuthor(author);
            book.setYear(Integer.parseInt(yearStr));
            book.setGenre(genre);
            book.setSynopsis(synopsis);

            // Atualiza no banco
            bookDAO.update(book);
            response.sendRedirect("books");
        } catch (IllegalArgumentException e) {
            // Retorna para o formulário com mensagem de erro
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("book_form.jsp").forward(request, response);
        }
    }

    /**
     * Visualiza os detalhes de um livro.
     */
    private void viewBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookDAO.findById(id);
        request.setAttribute("book", book);
        request.getRequestDispatcher("book_detail.jsp").forward(request, response);
    }

    /**
     * Valida os campos do formulário de livro.
     */
    private void validateBookFields(String title, String author, String yearStr, String genre, String synopsis) {
        if (title == null || title.trim().isEmpty() || title.length() > 255) {
            throw new IllegalArgumentException("Título obrigatório e não deve exceder 255 caracteres.");
        }
        if (author == null || author.trim().isEmpty() || author.length() > 255) {
            throw new IllegalArgumentException("Autor obrigatório e não deve exceder 255 caracteres.");
        }
        if (yearStr == null || yearStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Ano de publicação obrigatório.");
        }
        try {
            int year = Integer.parseInt(yearStr);
            if (year < 0 || year > 9999) {
                throw new IllegalArgumentException("Ano deve estar entre 0 e 9999.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ano deve ser um número válido.");
        }
        if (genre == null || genre.trim().isEmpty() || genre.length() > 100) {
            throw new IllegalArgumentException("Gênero obrigatório e não deve exceder 100 caracteres.");
        }
        if (synopsis != null && synopsis.length() > 1000) {
            throw new IllegalArgumentException("Sinopse não deve exceder 1000 caracteres.");
        }
    }
}
