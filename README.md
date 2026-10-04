# Kigali Clinic ? JPA & JPQL Quiz

Spring Boot + Spring Data JPA + PostgreSQL project implementing the 2026 in-class quiz
for the Web Technology and Internet course at AUCA.

## Part A ? Derived Queries

| ID | Endpoint | Query keywords |
|----|----------|---------------|
| A1 | GET /api/patients/by-last-name?lastName=uwise | IgnoreCase, OrderBy |
| A2 | GET /api/appointments/by-status?status=SCHEDULED | enum param, OrderBy |
| A3 | GET /api/appointments/between?start=2026-10-01&end=2026-10-31 | Between |
| A4 | POST /api/appointments | existsBy, And, Not (HTTP 409) |

## Part B ? JPQL with joins

| ID | Endpoint | JPQL |
|----|----------|------|
| B1 | GET /api/doctors/by-specialization?name=cardiology | JOIN collection, LOWER, DISTINCT |
| B2 | GET /api/doctors/without-office | IS NULL, ORDER BY |
| B3 | GET /api/specializations/unused | IS EMPTY |
| B4 | GET /api/patients/of-doctor/{doctorId} | DISTINCT, path navigation (404 if doctor unknown) |

## Part C ? Aggregates and bulk updates

| ID | Endpoint | JPQL |
|----|----------|------|
| C1 | GET /api/appointments/stats/by-status | GROUP BY, COUNT |
| C2 | GET /api/patients/frequent?min=3 | GROUP BY, HAVING, ORDER BY COUNT |
| C3 | GET /api/offices/busiest | 3-way JOIN, ORDER BY COUNT DESC |
| C4 | PATCH /api/appointments/cancel-day?doctorId=...&date=... | UPDATE, @Modifying, @Transactional |

## Bonus

| Bonus | Endpoint | Implementation |
|-------|----------|----------------|
| +5 | GET /api/appointments/page?page=0&size=5&sort=appointmentDate,desc | Pageable, split on comma for direction |
| +5 | DELETE /api/appointments/cancelled-before?date=2026-10-15 | JPQL DELETE, @Modifying |

## Project structure

    clinic/src/main/java/kigali/clinic/rw/
      ClinicApplication.java
      controller/
      domain/
      repository/
      service/

## Run

    cd clinic
    mvn clean compile
    mvn spring-boot:run

App runs on http://localhost:8080. Requires PostgreSQL with a clinic_db database.
