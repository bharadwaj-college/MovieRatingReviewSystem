
# Build the Java WAR application
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package -DskipTests

# Run the application on Tomcat 10.1
FROM tomcat:10.1.61-jre21-temurin-noble

# Configure Tomcat to listen on Render's default port
RUN sed -i 's/port="8080"/port="10000"/' /usr/local/tomcat/conf/server.xml

# Deploy the application at the root URL
COPY --from=build /app/target/MovieRatingReviewSystem-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 10000

CMD ["catalina.sh", "run"]
