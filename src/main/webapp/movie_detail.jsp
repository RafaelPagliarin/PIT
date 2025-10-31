<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="https://jakarta.ee/xml/ns/jakartaee/jsp/jstl/core" prefix="c" %>
<%
    com.yourname.catalog.model.Movie movie = (com.yourname.catalog.model.Movie) request.getAttribute("movie");
%>
<html>
<head>
    <title>Movie Details</title>
</head>
<body>
<h2>Movie Details</h2>
<p><strong>Title:</strong> <%= movie.getTitle() %></p>
<p><strong>Director:</strong> <%= movie.getAuthor() %></p>
<p><strong>Year:</strong> <%= movie.getYear() %></p>
<p><strong>Genre:</strong> <%= movie.getGenre() %></p>
<p><strong>Synopsis:</strong><br/> <%= movie.getSynopsis() %></p>
<a href="movies">Back to list</a>
</body>
</html>
