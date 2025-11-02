<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="css/styles.css" />
    <title><c:out value="${book != null ? 'Edit Book' : 'New Book'}"/></title>
</head>
<body>
    <div class="container">
        <h2><c:out value="${book != null ? 'Edit Book' : 'New Book'}"/></h2>

        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <form action="books?action=${book != null ? 'update' : 'insert'}" method="post">
            <input type="hidden" name="id" value="${book.id}"/>

            Title: <input type="text" name="title" value="${book.title}" required/><br/>
            Author: <input type="text" name="author" value="${book.author}" required/><br/>
            Year: <input type="number" name="year" value="${book.year}" required/><br/>
            Genre: <input type="text" name="genre" value="${book.genre}"/><br/>
            Synopsis:<br/>
            <textarea name="synopsis">${book.synopsis}</textarea><br/>

            <input type="submit" value="${book != null ? 'Update' : 'Add'}"/>
        </form>
        <button onclick="location.href='books'">Back to List</button>
    </div>
</body>
</html>
