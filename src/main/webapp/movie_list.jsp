<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="https://jakarta.ee/xml/ns/jakartaee/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Movie List</title>
</head>
<body>
<h2>Movies</h2>
<a href="movies?action=new">Add New Movie</a>
<table border="1">
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
</body>
</html>
