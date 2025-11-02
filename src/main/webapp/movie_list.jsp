<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="css/styles.css" />
    <title>Movie List</title>
</head>
<body>
    <div class="container">
        <h2>Movies</h2>
        <button onclick="location.href='index.jsp'">Voltar à Página Inicial</button>
        <a href="movies?action=new">Add New Movie</a>
        <form action="movies" method="get">
            <input type="text" name="search" value="${param.search}" placeholder="Buscar título ou diretor"/>
            <input type="submit" value="Buscar"/>
        </form>
        <table>
            <tr>
                <th>Title</th><th>Director</th><th>Year</th><th>Genre</th><th>Actions</th>
            </tr>
            <c:forEach var="movie" items="${listMovie}">
                <tr>
                    <td>${movie.title}</td>
                    <td>${movie.director}</td>
                    <td>${movie.year}</td>
                    <td>${movie.genre}</td>
                    <td>
                        <a href="movies?action=view&id=${movie.id}">View</a>
                        <a href="movies?action=edit&id=${movie.id}">Edit</a>
                        <a href="movies?action=delete&id=${movie.id}" onclick="return confirm('Confirm deletion?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
