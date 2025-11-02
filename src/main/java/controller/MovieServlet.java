package controller;

import dao.MovieDAO;
import model.Movie;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/movies")
public class MovieServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                listMovies(request, response);
            } else {
                switch (action) {
                    case "new":
                        showNewForm(request, response);
                        break;
                    case "insert":
                        insertMovie(request, response);
                        break;
                    case "delete":
                        deleteMovie(request, response);
                        break;
                    case "edit":
                        showEditForm(request, response);
                        break;
                    case "update":
                        updateMovie(request, response);
                        break;
                    case "view":
                        viewMovie(request, response);
                        break;
                    default:
                        listMovies(request, response);
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

    private void listMovies(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String search = request.getParameter("search");
        List<Movie> listMovie;
        if (search != null && !search.trim().isEmpty()) {
            listMovie = movieDAO.findBySearch(search);
        } else {
            listMovie = movieDAO.findAll();
        }
        request.setAttribute("listMovie", listMovie);
        request.getRequestDispatcher("movie_list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("movie_form.jsp").forward(request, response);
    }

    private void insertMovie(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        try {
            Movie newMovie = new Movie();
            String title = request.getParameter("title");
            String director = request.getParameter("director");
            String yearStr = request.getParameter("year");
            String genre = request.getParameter("genre");
            String synopsis = request.getParameter("synopsis");

            validateMovieFields(title, director, yearStr, genre, synopsis);

            newMovie.setTitle(title);
            newMovie.setDirector(director);
            newMovie.setYear(Integer.parseInt(yearStr));
            newMovie.setGenre(genre);
            newMovie.setSynopsis(synopsis);

            movieDAO.insert(newMovie);
            response.sendRedirect("movies");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("movie_form.jsp").forward(request, response);
        }
    }

    private void deleteMovie(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        movieDAO.delete(id);
        response.sendRedirect("movies");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Movie existingMovie = movieDAO.findById(id);
        request.setAttribute("movie", existingMovie);
        request.getRequestDispatcher("movie_form.jsp").forward(request, response);
    }

    private void updateMovie(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Movie movie = new Movie();

            String title = request.getParameter("title");
            String director = request.getParameter("director");
            String yearStr = request.getParameter("year");
            String genre = request.getParameter("genre");
            String synopsis = request.getParameter("synopsis");

            validateMovieFields(title, director, yearStr, genre, synopsis);

            movie.setId(id);
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setYear(Integer.parseInt(yearStr));
            movie.setGenre(genre);
            movie.setSynopsis(synopsis);

            movieDAO.update(movie);
            response.sendRedirect("movies");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("movie_form.jsp").forward(request, response);
        }
    }

    private void viewMovie(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Movie movie = movieDAO.findById(id);
        request.setAttribute("movie", movie);
        request.getRequestDispatcher("movie_detail.jsp").forward(request, response);
    }

    private void validateMovieFields(String title, String director, String yearStr, String genre, String synopsis) {
        if (title == null || title.trim().isEmpty() || title.length() > 255) {
            throw new IllegalArgumentException("Title is required and cannot exceed 255 characters.");
        }
        if (director == null || director.trim().isEmpty() || director.length() > 255) {
            throw new IllegalArgumentException("Director is required and cannot exceed 255 characters.");
        }
        if (yearStr == null || yearStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Year is required.");
        }
        try {
            int year = Integer.parseInt(yearStr);
            if (year < 0 || year > 9999) {
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
