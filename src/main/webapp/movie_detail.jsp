<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/styles.css" />
    <title>Movie Details</title>
</head>
<body>
    <div class="container">
        <h2>Movie Details</h2>

        <p><b>Title:</b> ${movie.title}</p>
        <p><b>Director:</b> ${movie.director}</p>
        <p><b>Year:</b> ${movie.year}</p>
        <p><b>Genre:</b> ${movie.genre}</p>
        <p><b>Synopsis:</b> ${movie.synopsis}</p>

        <button onclick="location.href='movies'">Back to List</button>
    </div>
</body>
</html>
