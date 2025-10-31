<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="https://jakarta.ee/xml/ns/jakartaee/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Book List</title>
</head>
<body>
<h2>Books</h2>
<a href="books?action=new">Add New Book</a>
<table border="1">
    <tr>
        <th>Title</th><th>Author</th><th>Year</th><th>Genre</th><th>Actions</th>
    </tr>
    <c:forEach var="book" items="${listBook}">
        <tr>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.year}</td>
            <td>${book.genre}</td>
            <td>
                <a href="books?action=view&id=${book.id}">View</a>
                <a href="books?action=edit&id=${book.id}">Edit</a>
                <a href="books?action=delete&id=${book.id}" onclick="return confirm('Confirm deletion?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
