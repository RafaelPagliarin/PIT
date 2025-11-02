<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="css/styles.css" />
    <title>Book Details</title>
</head>
<body>
    <div class="container">
        <h2>Book Details</h2>

        <p><b>Title:</b> ${book.title}</p>
        <p><b>Author:</b> ${book.author}</p>
        <p><b>Year:</b> ${book.year}</p>
        <p><b>Genre:</b> ${book.genre}</p>
        <p><b>Synopsis:</b> ${book.synopsis}</p>

        <button onclick="location.href='books'">Back to List</button>
    </div>
</body>
</html>

