<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Movie Reviews</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #eef2f7;
            margin: 0;
        }

        .header {
            background: #243b55;
            color: white;
            padding: 18px 30px;
        }

        .container {
            width: 80%;
            margin: 30px auto;
        }

        .card {
            background: white;
            padding: 22px;
            margin: 18px 0;
            border-radius: 8px;
            box-shadow: 0 2px 8px #cccccc;
        }

        h2, h3 {
            color: #243b55;
        }

        input, textarea, select {
            width: 100%;
            padding: 11px;
            margin: 9px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        textarea {
            resize: vertical;
        }

        button {
            background: #243b55;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin: 5px 5px 5px 0;
        }

        .delete-button {
            background: #d9534f;
        }

        .cancel-button {
            background: #777;
        }

        .success {
            color: green;
        }

        .error {
            color: red;
        }

        a {
            color: #243b55;
        }

        .action-link {
            display: inline-block;
            background: #243b55;
            color: white;
            padding: 12px 20px;
            text-decoration: none;
            border-radius: 5px;
            margin: 5px;
        }
    </style>
</head>

<body>

<div class="header">
    <h2>Movie Rating and Review System</h2>
    Welcome, <c:out value="${sessionScope.userName}"/>
</div>

<div class="container">

    <a href="${pageContext.request.contextPath}/movies">
        Back to Movies
    </a>

    <div class="card">
        <h2><c:out value="${movie.title}"/></h2>

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
    </div>

    <c:if test="${param.added == '1'}">
        <p class="success">Your review was submitted successfully!</p>
    </c:if>

    <c:if test="${param.updated == '1'}">
        <p class="success">Your review was updated successfully!</p>
    </c:if>

    <c:if test="${param.deleted == '1'}">
        <p class="success">Your review was deleted successfully!</p>
    </c:if>

    <c:if test="${param.error == 'validation'}">
        <p class="error">
            Please enter a valid rating and review between 1 and 500 characters.
        </p>
    </c:if>

    <c:if test="${not empty error}">
        <p class="error"><c:out value="${error}"/></p>
    </c:if>

    <div class="card">

        <c:choose>
            <c:when test="${not empty editReview}">

                <h3>Edit Your Review</h3>

                <form action="${pageContext.request.contextPath}/reviews"
                      method="post">

                    <input type="hidden" name="action" value="update">

                    <input type="hidden" name="movieId"
                           value="${movie.movieId}">

                    <input type="hidden" name="reviewId"
                           value="${editReview.reviewId}">

                    <label>Rating (1 to 5 stars)</label>

                    <select name="rating" required>
                        <option value="1" ${editReview.rating == 1 ? 'selected' : ''}>1 Star</option>
                        <option value="2" ${editReview.rating == 2 ? 'selected' : ''}>2 Stars</option>
                        <option value="3" ${editReview.rating == 3 ? 'selected' : ''}>3 Stars</option>
                        <option value="4" ${editReview.rating == 4 ? 'selected' : ''}>4 Stars</option>
                        <option value="5" ${editReview.rating == 5 ? 'selected' : ''}>5 Stars</option>
                    </select>

                    <label>Your Review</label>

                    <textarea name="reviewText"
                              rows="5"
                              maxlength="500"
                              required><c:out value="${editReview.reviewText}"/></textarea>

                    <button type="submit">Update Review</button>

                    <a class="action-link"
                       href="${pageContext.request.contextPath}/reviews?movieId=${movie.movieId}">
                        Cancel
                    </a>

                </form>

            </c:when>

            <c:otherwise>

                <h3>Write Your Review</h3>

                <form action="${pageContext.request.contextPath}/reviews"
                      method="post">

                    <input type="hidden" name="action" value="add">

                    <input type="hidden" name="movieId"
                           value="${movie.movieId}">

                    <label>Rating (1 to 5 stars)</label>

                    <select name="rating" required>
                        <option value="">Select Rating</option>
                        <option value="1">1 Star</option>
                        <option value="2">2 Stars</option>
                        <option value="3">3 Stars</option>
                        <option value="4">4 Stars</option>
                        <option value="5">5 Stars</option>
                    </select>

                    <label>Your Review</label>

                    <textarea name="reviewText"
                              rows="5"
                              maxlength="500"
                              placeholder="Write your opinion about this movie..."
                              required></textarea>

                    <button type="submit">Submit Review</button>

                </form>

            </c:otherwise>
        </c:choose>

    </div>

    <h2>Previous Reviews</h2>

    <c:choose>

        <c:when test="${not empty reviews}">

            <c:forEach var="review" items="${reviews}">

                <div class="card">

                    <h3><c:out value="${review.user.name}"/></h3>

                    <p>
                        <b>Rating:</b>
                        <c:out value="${review.rating}"/> / 5
                    </p>

                    <p><c:out value="${review.reviewText}"/></p>

                    <p>
                        <b>Date:</b>
                        <c:out value="${review.reviewDate}"/>
                    </p>

                    <c:if test="${review.user.userId == sessionScope.loggedInUser}">

                        <a class="action-link"
                           href="${pageContext.request.contextPath}/reviews?movieId=${movie.movieId}&editReviewId=${review.reviewId}">
                            Edit Review
                        </a>

                        <form action="${pageContext.request.contextPath}/reviews"
                              method="post"
                              style="display:inline;"
                              onsubmit="return confirm('Are you sure you want to delete this review?');">

                            <input type="hidden" name="action" value="delete">

                            <input type="hidden" name="movieId"
                                   value="${movie.movieId}">

                            <input type="hidden" name="reviewId"
                                   value="${review.reviewId}">

                            <button type="submit" class="delete-button">
                                Delete Review
                            </button>

                        </form>

                    </c:if>

                </div>

            </c:forEach>

        </c:when>

        <c:otherwise>

            <p>No reviews have been submitted for this movie yet.</p>

        </c:otherwise>

    </c:choose>

</div>

</body>
</html>
