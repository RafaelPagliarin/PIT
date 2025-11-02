package controller;

import dao.MovieDAO;
import model.Movie;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

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

    // POST method delegates to doGet for form submissions
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

    private void insertMovie(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Movie newMovie = new Movie();
        newMovie.setTitle(request.getParameter("title"));
        newMovie.setDirector(request.getParameter("director"));
        newMovie.setYear(Integer.parseInt(request.getParameter("year")));
        newMovie.setGenre(request.getParameter("genre"));
        newMovie.setSynopsis(request.getParameter("synopsis"));

        movieDAO.insert(newMovie);
        response.sendRedirect("movies");
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

    private void updateMovie(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(request.getParameter("title"));
        movie.setDirector(request.getParameter("director"));
        movie.setYear(Integer.parseInt(request.getParameter("year")));
        movie.setGenre(request.getParameter("genre"));
        movie.setSynopsis(request.getParameter("synopsis"));

        movieDAO.update(movie);
        response.sendRedirect("movies");
    }

    private void viewMovie(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Movie movie = movieDAO.findById(id);
        request.setAttribute("movie", movie);
        request.getRequestDispatcher("movie_detail.jsp").forward(request, response);
    }
}
