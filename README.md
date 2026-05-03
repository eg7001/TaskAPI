# TaskAPI

A RESTful Task Management API built with Java 21 and Spring Boot. Features JWT authentication, role-based access control, team management, and task assignment.

## Tech Stack

- Java 21 / Spring Boot
- Spring Security + JWT
- PostgreSQL
- Docker & Docker Compose

## Role System

| Role | Permissions |
|------|------------|
| `ADMIN` | Full access — manages users, teams, and all tasks |
| `TEAM_LEAD` | Creates and manages tasks within their own team |
| `USER` | Views and comments on assigned tasks only |

## Running with Docker

Make sure Docker Desktop is running, then:

```bash
git clone https://github.com/eg7001/TaskAPI.git
cd TaskAPI
docker-compose up --build
```

The app starts on `http://localhost:8080`. The database is created automatically and roles are seeded on first startup. No manual setup needed.

To stop:
```bash
docker-compose down
```

## Running Locally

1. Create a PostgreSQL database named `taskapi`
2. Set environment variables:
```
DB_URL=jdbc:postgresql://localhost:5432/taskapi
DB_USER=your_db_user
DB_PASSWORD=your_db_password
JWT_SECRET=your_secret_key
```
3. Run:
```bash
./mvnw spring-boot:run
```

## API Overview

All protected endpoints require `Authorization: Bearer <token>` header.

| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| `POST` | `/api/auth/login` | Public | Login, returns JWT |
| `POST` | `/api/user` | Public | Register new user |
| `GET` | `/api/user` | Authenticated | Get all users |
| `POST` | `/api/user/{userId}/roles` | ADMIN | Assign role to user |
| `GET` | `/api/team` | Authenticated | Get all teams |
| `POST` | `/api/team` | Authenticated | Create a team |
| `POST` | `/api/team/{teamId}/users/{userId}` | ADMIN / TEAM_LEAD | Add user to team |
| `GET` | `/api/tasks` | Authenticated | Get tasks (filtered by role) |
| `POST` | `/api/tasks` | ADMIN / TEAM_LEAD | Create a task |
| `PUT` | `/api/tasks/{taskId}/update` | ADMIN / TEAM_LEAD | Update a task |
| `PATCH` | `/api/tasks/{taskId}/updateStatus/{status}` | Authenticated | Update task status |
| `DELETE` | `/api/tasks/{taskId}` | ADMIN / TEAM_LEAD | Delete a task |
| `POST` | `/api/tasks/{taskId}/comments` | Authenticated | Add a comment |
| `GET` | `/api/tasks/{taskId}/comments` | Authenticated | Get task comments |

## Project Structure

```
src/main/java/com/example/taskapi/
├── controller/     # REST controllers
├── service/        # Business logic
├── repository/     # Data access
├── models/         # JPA entities
├── dto/            # Request/response DTOs
├── mappers/        # Entity ↔ DTO mapping
├── security/       # JWT filter, JwtUtil, SecurityConfig
├── exceptions/     # Global exception handling
└── seed/           # Role seeder
```
