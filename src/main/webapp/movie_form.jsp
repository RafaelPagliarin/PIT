<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="https://jakarta.ee/xml/ns/jakartaee/jsp/jstl/core" prefix="c" %>
<%-- Define if updating or creating new --%>
<%
    String action = (request.getAttribute("movie") != null) ? "update" : "insert";
    String formTitle = (action.equals("update")) ? "Edit Movie" : "New Movie";
    com.yourname.catalog.model.Movie movie = (com.yourname.catalog.model.Movie) request.getAttribute("movie");
%>
<html>
<head>
    <title><%= formTitle %></title>
</head>
<body>
<h2><%= formTitle %></h2>
<form action="movies?action=<%= action %>" method="post">
    <% if ("update".equals(action)) { %>
        <input type="hidden" name="id" value="<%= movie.getId() %>"/>
    <% } %>
    Title:<br/>
    <input type="text" name="title" value="<%= (movie != null) ? movie.getTitle() : "" %>" required/><br/>
    Author:<br/>
    <input type="text" name="author" value="<%= (movie != null) ? movie.getAuthor() : "" %>" required/><br/>
    Year:<br/>
    <input type="number" name="year" value="<%= (movie != null) ? movie.getYear() : "" %>" required/><br/>
    Genre:<br/>
    <input type="text" name="genre" value="<%= (movie != null) ? movie.getGenre() : "" %>"/><br/>
    Synopsis:<br/>
    <textarea name="synopsis"><%= (movie != null) ? movie.getSynopsis() : "" %></textarea><br/><br/>
    <input type="submit" value="Save"/>
</form>
<a href="movies">Back to list</a>
</body>
</html>
