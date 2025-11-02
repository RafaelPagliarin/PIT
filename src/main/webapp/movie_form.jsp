<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/styles.css" />
    <title><c:out value="${movie != null ? 'Edit Movie' : 'New Movie'}"/></title>
</head>
<body>
    <div class="container">
        <h2><c:out value="${movie != null ? 'Edit Movie' : 'New Movie'}"/></h2>
        <form action="movies?action=${movie != null ? 'update' : 'insert'}" method="post">
            <input type="hidden" name="id" value="${movie.id}"/>

            Title: <input type="text" name="title" value="${movie.title}" required/><br/>
            Director: <input type="text" name="director" value="${movie.director}" required/><br/>
            Year: <input type="number" name="year" value="${movie.year}" required/><br/>
            Genre: <input type="text" name="genre" value="${movie.genre}"/><br/>
            Synopsis:<br/>
            <textarea name="synopsis">${movie.synopsis}</textarea><br/>

            <input type="submit" value="${movie != null ? 'Update' : 'Add'}"/>
        </form>
        <button onclick="location.href='movies'">Back to List</button>
    </div>
</body>
</html>
