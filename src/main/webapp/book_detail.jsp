<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="https://jakarta.ee/xml/ns/jakartaee/jsp/jstl/core" prefix="c" %>
<%
    com.yourname.catalog.model.Book book = (com.yourname.catalog.model.Book) request.getAttribute("book");
%>
<html>
<head>
    <title>Book Details</title>
</head>
<body>
<h2>Book Details</h2>
<p><strong>Title:</strong> <%= book.getTitle() %></p>
<p><strong>Author:</strong> <%= book.getAuthor() %></p>
<p><strong>Year:</strong> <%= book.getYear() %></p>
<p><strong>Genre:</strong> <%= book.getGenre() %></p>
<p><strong>Synopsis:</strong><br/> <%= book.getSynopsis() %></p>
<a href="books">Back to list</a>
</body>
</html>
