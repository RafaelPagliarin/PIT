<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="https://jakarta.ee/xml/ns/jakartaee/jsp/jstl/core" prefix="c" %>
<%-- Define if updating or creating new --%>
<%
    String action = (request.getAttribute("book") != null) ? "update" : "insert";
    String formTitle = (action.equals("update")) ? "Edit Book" : "New Book";
    com.yourname.catalog.model.Book book = (com.yourname.catalog.model.Book) request.getAttribute("book");
%>
<html>
<head>
    <title><%= formTitle %></title>
</head>
<body>
<h2><%= formTitle %></h2>
<form action="books?action=<%= action %>" method="post">
    <% if ("update".equals(action)) { %>
        <input type="hidden" name="id" value="<%= book.getId() %>"/>
    <% } %>
    Title:<br/>
    <input type="text" name="title" value="<%= (book != null) ? book.getTitle() : "" %>" required/><br/>
    Author:<br/>
    <input type="text" name="author" value="<%= (book != null) ? book.getAuthor() : "" %>" required/><br/>
    Year:<br/>
    <input type="number" name="year" value="<%= (book != null) ? book.getYear() : "" %>" required/><br/>
    Genre:<br/>
    <input type="text" name="genre" value="<%= (book != null) ? book.getGenre() : "" %>"/><br/>
    Synopsis:<br/>
    <textarea name="synopsis"><%= (book != null) ? book.getSynopsis() : "" %></textarea><br/><br/>
    <input type="submit" value="Save"/>
</form>
<a href="books">Back to list</a>
</body>
</html>
