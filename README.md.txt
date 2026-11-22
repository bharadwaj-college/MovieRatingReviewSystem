# Movie Rating and Review System

## Project Description
The Movie Rating and Review System is a Java-based web application that allows users to register, log in, browse movies, and share their opinions by adding ratings and reviews.

The application provides a platform for users to view movie information, read reviews, and manage their own reviews.

## Technologies Used
- Java 23
- JSP and JSTL
- Jakarta Servlets
- Spring Framework 6.2.7
- Hibernate ORM 6.6.3.Final
- MySQL
- Apache Tomcat 10.1
- Maven
- HTML and CSS
- NetBeans IDE

## Main Features
- User registration
- User login and logout
- Session management
- Display available movies
- View movie reviews
- Add movie ratings and reviews
- Edit existing reviews
- Delete existing reviews
- Display average movie ratings and review counts
- MySQL database integration

## Database Details
Database name: `movie_review_db`

Main tables:
- `users`
- `genres`
- `movies`
- `reviews`

The database export is available in the `database` folder.

## Project Setup and Execution

### Requirements
- JDK 23
- NetBeans IDE
- Apache Tomcat 10.1
- XAMPP with MySQL
- Maven

### Installation Steps

1. Download or clone this project.
2. Open the project in NetBeans IDE.
3. Start Apache and MySQL using XAMPP.
4. Open phpMyAdmin at `http://localhost/phpmyadmin`.
5. Create a database named `movie_review_db`.
6. Import the SQL file from the `database` folder.
7. Open `src/main/resources/hibernate.cfg.xml`.
8. Configure the MySQL connection details according to your local setup.
9. Build the project using Maven.
10. Deploy and run the project using Apache Tomcat.

### Database Configuration
The application uses Hibernate to connect to MySQL.

Default local database settings:
- Database: `movie_review_db`
- Host: `localhost`
- Port: `3306`
- Username: `root`
- Password: Empty (default XAMPP setup)

Update the database settings in `hibernate.cfg.xml` if your MySQL configuration is different.

## Project Structure

- `src/main/java` – Java source code
- `src/main/resources` – Configuration files
- `src/main/webapp` – Web application files and JSP pages
- `database` – SQL database backup
- `pom.xml` – Maven dependencies and project configuration
- `README.md` – Project documentation

## GitHub Repository
To be updated after uploading the project.

## Live Application
To be updated after deployment.
