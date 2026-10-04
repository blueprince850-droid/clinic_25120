# Clinic Management REST API

A beginner-friendly Spring Boot application for managing patients, doctors, offices, specializations, and appointments in a clinic.

This project uses Spring Boot, Spring Data JPA, and PostgreSQL to expose a REST API for common clinic operations.

## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Run the Application](#run-the-application)
- [ERD / Database Model](#erd--database-model)
- [API Endpoints](#api-endpoints)
- [Request and Response Examples](#request-and-response-examples)
- [Error Handling](#error-handling)
- [Testing with Postman](#testing-with-postman)
- [Troubleshooting](#troubleshooting)
- [Notes for Beginners](#notes-for-beginners)

## Overview

The app models a small clinic system with six main tables:

- `office`
- `doctor`
- `patient`
- `specialization`
- `appointment`
- `doctor_specialization`

### Core relationships

- One doctor has exactly one office.
- One patient can have many appointments.
- One doctor can have many appointments.
- One doctor can have many specializations.
- One specialization can belong to many doctors.
- The many-to-many doctor-to-specialization relationship is handled through the junction table `doctor_specialization`.

## Project Structure

```text
clinic/
├── src/
│   ├── main/
│   │   ├── java/com/example/clinic/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── ClinicApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/java/com/example/clinic/
├── Requirement/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── clinic-postman-collection.json
├── clinic-postman-happy-path.json
├── clinic-postman-cleanup.json
├── clinic-postman-all-in-one.json
├── README.md
└── target/
```

## Technology Stack

- Java 25
- Spring Boot 4.0.0
- Spring Web MVC
- Spring Data JPA
- Hibernate ORM
- PostgreSQL
- Maven
- Jakarta Validation

## Prerequisites

Before running the project, make sure you have:

- Java 25 installed
- Maven installed
- PostgreSQL installed and running
- A PostgreSQL database named `Clinic`
- A local PostgreSQL user with access to that database

## Database Setup

This application connects to PostgreSQL using the credentials in `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Clinic
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
```

### Important

You must set the `DB_PASSWORD` environment variable before starting the app.

For example on Linux/macOS:

```bash
export DB_PASSWORD='your_postgres_password'
```

Then run:

```bash
./mvnw spring-boot:run
```

If `DB_PASSWORD` is missing, the app will fail to connect to PostgreSQL.

## Run the Application

From the project root:

```bash
./mvnw clean package -DskipTests
java -jar target/clinic-0.0.1-SNAPSHOT.jar
```

Or run directly with Maven:

```bash
export DB_PASSWORD='your_postgres_password'
./mvnw spring-boot:run
```

By default, the API starts on:

```text
http://localhost:8080
```

## ERD / Database Model

The application uses these tables and relationships:

### 1) Office

- `office_id` (PK)
- `room_number`

### 2) Doctor

- `doctor_id` (PK)
- `full_name`
- `office_id` (FK to `office.office_id`)

### 3) Patient

- `patient_id` (PK)
- `full_name`

### 4) Specialization

- `specialization_id` (PK)
- `name`

### 5) Appointment

- `appointment_id` (PK)
- `patient_id` (FK to `patient.patient_id`)
- `doctor_id` (FK to `doctor.doctor_id`)
- `date`
- `reason`
- `status`

### 6) DoctorSpecialization

- `doctor_id` (FK to `doctor.doctor_id`)
- `specialization_id` (FK to `specialization.specialization_id`)
- Composite primary key: (`doctor_id`, `specialization_id`)

## API Endpoints

All API routes are under `/api`.

### Offices

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/offices` | Create an office |
| GET | `/api/offices` | Get all offices |
| GET | `/api/offices/{id}` | Get office by id |
| PUT | `/api/offices/{id}` | Update office |
| DELETE | `/api/offices/{id}` | Delete office |

### Patients

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/patients` | Create a patient |
| GET | `/api/patients` | Get all patients |
| GET | `/api/patients/{id}` | Get patient by id |
| PUT | `/api/patients/{id}` | Update patient |
| DELETE | `/api/patients/{id}` | Delete patient |

### Doctors

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/doctors` | Create a doctor |
| GET | `/api/doctors` | Get all doctors |
| GET | `/api/doctors/{id}` | Get doctor by id |
| PUT | `/api/doctors/{id}` | Update doctor |
| DELETE | `/api/doctors/{id}` | Delete doctor |

### Specializations

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/specializations` | Create a specialization |
| GET | `/api/specializations` | Get all specializations |
| GET | `/api/specializations/{id}` | Get specialization by id |
| PUT | `/api/specializations/{id}` | Update specialization |
| DELETE | `/api/specializations/{id}` | Delete specialization |

### Appointments

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/appointments` | Create an appointment |
| GET | `/api/appointments` | Get all appointments |
| GET | `/api/appointments/{id}` | Get appointment by id |
| PUT | `/api/appointments/{id}` | Update appointment |
| DELETE | `/api/appointments/{id}` | Delete appointment |

### Doctor-Specialization Links

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/doctor-specializations` | Link a doctor to a specialization |
| GET | `/api/doctor-specializations` | Get all doctor-specialization links |
| DELETE | `/api/doctor-specializations/{doctorId}/{specializationId}` | Remove a link |

## Request and Response Examples

### Create office

Request:

```http
POST /api/offices
Content-Type: application/json
```

```json
{
  "roomNumber": "Room 101"
}
```

Response:

```json
{
  "officeId": 1,
  "roomNumber": "Room 101"
}
```

### Create patient

Request:

```http
POST /api/patients
Content-Type: application/json
```

```json
{
  "fullName": "Alice Johnson"
}
```

Response:

```json
{
  "patientId": 1,
  "fullName": "Alice Johnson",
  "appointments": []
}
```

### Create doctor

Request:

```http
POST /api/doctors
Content-Type: application/json
```

```json
{
  "fullName": "Dr. Smith",
  "officeId": 1
}
```

Response:

```json
{
  "doctorId": 1,
  "fullName": "Dr. Smith",
  "office": {
    "officeId": 1,
    "roomNumber": "Room 101"
  },
  "appointments": []
}
```

### Create specialization

Request:

```http
POST /api/specializations
Content-Type: application/json
```

```json
{
  "name": "Cardiology"
}
```

Response:

```json
{
  "specializationId": 1,
  "name": "Cardiology"
}
```

### Create appointment

Request:

```http
POST /api/appointments
Content-Type: application/json
```

```json
{
  "patientId": 1,
  "doctorId": 1,
  "date": "2026-09-20",
  "reason": "Regular checkup",
  "status": "Scheduled"
}
```

Response:

```json
{
  "appointmentId": 1,
  "patient": {
    "patientId": 1,
    "fullName": "Alice Johnson"
  },
  "doctor": {
    "doctorId": 1,
    "fullName": "Dr. Smith"
  },
  "date": "2026-09-20",
  "reason": "Regular checkup",
  "status": "Scheduled"
}
```

### Link doctor to specialization

Request:

```http
POST /api/doctor-specializations
Content-Type: application/json
```

```json
{
  "doctorId": 1,
  "specializationId": 1
}
```

Response:

```json
{
  "id": {
    "doctorId": 1,
    "specializationId": 1
  },
  "doctor": {
    "doctorId": 1,
    "fullName": "Dr. Smith"
  },
  "specialization": {
    "specializationId": 1,
    "name": "Cardiology"
  }
}
```

## Error Handling

The API returns structured JSON errors.

Example error response:

```json
{
  "timestamp": "2026-09-20T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Patient not found with id: 99",
  "path": "/api/patients/99"
}
```

### Common error cases

- Invalid request body fields
- Missing required values
- Referencing a non-existent doctor, patient, office, or specialization
- Duplicate doctor-specialization link

## Testing with Postman

This project includes Postman collections for testing:

- `clinic-postman-collection.json`
- `clinic-postman-happy-path.json`
- `clinic-postman-cleanup.json`
- `clinic-postman-all-in-one.json`

### How to use them

1. Open Postman.
2. Click Import.
3. Select one of the JSON files from the project root.
4. Make sure the API is running at `http://localhost:8080`.
5. Send requests in order for the happy path or full lifecycle flow.

## Troubleshooting

### 1) App fails to start with a PostgreSQL error

Make sure you exported the password:

```bash
export DB_PASSWORD='your_password'
```

### 2) Tables are not created

Check that:

- PostgreSQL is running
- The `Clinic` database exists
- Your user has permission to create tables
- `spring.jpa.hibernate.ddl-auto=update` is enabled

### 3) 404 or 400 errors

Check whether:

- The resource ID exists
- The request JSON matches the DTO fields exactly
- Required fields are not missing

### 4) Recursion or nested JSON issues

The project uses `@JsonIgnore` on some bidirectional relationships to prevent infinite recursion when serializing entity objects.

## Notes for Beginners

- Controllers are the API entry points.
- Services contain the business logic.
- Repositories talk to the database.
- DTOs are used for request validation.
- Entities are database models.
- Exceptions are centralized in `GlobalExceptionHandler`.

This is a clean layered architecture: Controller → Service → Repository → Database.

## Summary

This project demonstrates how to build a small, real-world clinic management API using Spring Boot and JPA. It covers the main CRUD operations for the clinic domain and shows how to model one-to-one, one-to-many, and many-to-many relationships in a relational database.

If you are learning Java + Spring Boot, this project is a great example of:

- REST API design
- Entity modeling
- Database relationships
- Validation
- Exception handling
- PostgreSQL integration

---

Created for the Clinic Management REST API in this workspace.
