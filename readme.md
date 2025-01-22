# Link Shortener - Quick Start
This is a Link Shortener application built with Java 17, Spring Boot, and PostgreSQL, organized with Hexagonal Architecture.

## How It Works
The app receives an original URL and stores it along with a generated shortCode.
You can retrieve the link by ID or shortCode.
A redirect endpoint sends the user to the original URL when accessing via shortCode.

## Maven Build:

From the project root, run:
```bash
mvn clean package -DskipTests
```

This compiles and creates a JAR in target/.
Local PostgreSQL (if not using Docker):

Create a PostgreSQL database linkshortenerdb, user/password matching application.yml (defaults: postgres:postgres).
Launch the app with:
```bash
java -jar target/linkShortener-0.0.1-SNAPSHOT.jar
````
The application should be available at http://localhost:8080.

## Docker & Docker Compose:

Ensure Docker is installed.

From the project root, run:
```bash
docker-compose up --build -d
````

This launches both PostgreSQL and the linkshortener container.
Check http://localhost:8080.

## Basic Usage

- Create (POST /api): send JSON {"originalUrl":"https://..."}

- List (GET /api): see all stored links

- Get by ID (GET /api/{id}): returns JSON

- Update (PUT /api/{id}): send JSON {"newUrl":"https://..."}

- Delete (DELETE /api/{id})

- Redirect (GET /api/r/{shortCode}): returns HTTP 200 and redirect to the original URL


## Swagger (OpenAPI)
### Documentation at:
```http
http://localhost:8080/swagger-ui/index.html
```
### API spec at:
```html 
http://localhost:8080/v3/api-docs
```
