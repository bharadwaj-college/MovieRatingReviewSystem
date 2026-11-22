<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Movies - Movie Rating System</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #eef2f7;
            margin: 0;
        }

        .header {
            background: #243b55;
            color: white;
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header a {
            color: white;
            text-decoration: none;
            background: #d9534f;
            padding: 10px 15px;
            border-radius: 5px;
        }

        .container {
            width: 85%;
            margin: 35px auto;
        }

        h2 {
            color: #243b55;
        }

        .movie-card {
            background: white;
            padding: 20px;
            margin: 15px 0;
            border-radius: 8px;
            box-shadow: 0 2px 8px #cccccc;
        }

        .movie-card h3 {
            color: #243b55;
            margin-top: 0;
        }

        .rating {
            color: #e6a700;
            font-size: 20px;
            font-weight: bold;
        }

        .review-count {
            color: #555;
        }

        .review-link {
            display: inline-block;
            background: #243b55;
            color: white;
            text-decoration: none;
            padding: 10px 15px;
            border-radius: 5px;
        }

        .review-link:hover {
            background: #385777;
        }

        .empty {
            background: white;
            padding: 20px;
            border-radius: 8px;
        }
    </style>
</head>

<body>

<div class="header">

    <h2>Movie Rating and Review System</h2>

    <div>
        Welcome,
        <c:out value="${sessionScope.userName}"/>
        &nbsp;&nbsp;

        <a href="${pageContext.request.contextPath}/logout">
            Logout
        </a>
    </div>

</div>

<div class="container">

    <h2>Available Movies</h2>

    <c:choose>

        <c:when test="${not empty movies}">

            <c:forEach var="movie" items="${movies}">

                <div class="movie-card">

                    <h3>
                        <c:out value="${movie.title}"/>
                    </h3>

                    <p>
                        <b>Director:</b>
                        <c:out value="${movie.director}"/>
                    </p>

                    <p>
                        <b>Release Year:</b>
                        <c:out value="${movie.releaseYear}"/>
                    </p>

                    <p>
                        <b>Genre:</b>
                        <c:out value="${movie.genre.genreName}"/>
                    </p>

                    <p class="rating">
                        ⭐
                        <c:out value="${requestScope['rating_'.concat(movie.movieId)]}"/>
                        / 5
                    </p>

                    <p class="review-count">
                        Total Reviews:
                        <c:out value="${requestScope['count_'.concat(movie.movieId)]}"/>
                    </p>

                    <p>
                        <a class="review-link"
                           href="${pageContext.request.contextPath}/reviews?movieId=${movie.movieId}">
                            View Reviews / Add Review
                        </a>
                    </p>

                </div>

            </c:forEach>

        </c:when>

        <c:otherwise>

            <div class="empty">
                No movies are currently available.
            </div>

        </c:otherwise>

    </c:choose>

</div>

</body>
</html>
