<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="css/styles.css" />
    <title>Book List</title>
</head>
<body>
    <div class="container">
        <h2>Books</h2>
        <button onclick="location.href='index.jsp'">Voltar à Página Inicial</button>
        <a href="books?action=new">Add New Book</a>
        <form action="books" method="get">
            <input type="text" name="search" placeholder="Buscar título ou autor" value="${param.search}" />
            <input type="submit" value="Buscar"/>
        </form>
        <table>
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
    </div>
</body>
</html>
