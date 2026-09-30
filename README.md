# Library Management System v4

This version is designed to fix the blank-page problem after login and to support both local MySQL/JDBC development and Render deployment.

## Technology
- Java 17
- Jakarta Servlet 6
- JSP
- JDBC
- MySQL 8+
- Maven
- Apache Tomcat 10.1+
- Docker

## Login
Username: `admin`
Password: `admin123`

## Option A — easiest local run with Docker
Install Docker Desktop, then from this folder run:

```bash
docker compose up --build
```

Open:
`http://localhost:8080/`

The MySQL database is created automatically from `database/library.sql`.

## Option B — local Tomcat + MySQL
1. Create MySQL database using `database/library.sql`.
2. Set these environment variables if needed:
   - DB_HOST=localhost
   - DB_PORT=3306
   - DB_NAME=library_db
   - DB_USER=root
   - DB_PASSWORD=your_password
3. Run:
```bash
mvn clean package
```
4. Copy `target/library.war` to Tomcat 10.1+ `webapps/`.
5. Start Tomcat.
6. Open:
`http://localhost:8080/library/`

## Render deployment
Use the included `Dockerfile` and `render.yaml`.

IMPORTANT: Render web service needs access to a MySQL database. Render does not automatically create the MySQL database for this application. Use an external MySQL provider and add these environment variables in the Render service:
- DB_HOST
- DB_PORT
- DB_NAME
- DB_USER
- DB_PASSWORD

Then open the Render service root URL. Because the WAR is copied as `ROOT.war`, there is no `/library` context path on Render.

Health check:
`/health`

If `/health` says `DATABASE_ERROR`, the Java application is running but the MySQL settings are incorrect or the database is unreachable.

## Why this fixes the blank screen
- The WAR is deployed as `ROOT.war` for Render.
- The application has a real `index.html` welcome page.
- Login redirects to `/dashboard`.
- Dashboard catches database errors and displays them instead of producing a blank page.
- `/health` checks the JDBC/MySQL connection.
- JDBC configuration uses environment variables rather than a hard-coded localhost password.
