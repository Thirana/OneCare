# Appointment Service

## Overview
The Appointment Service is a microservice component of the OneCare - patient management system that manages medical appointments between patients and doctors. It provides a RESTful API for creating, updating, retrieving, and deleting appointments, as well as managing appointment statuses and schedules.

## Features
- Create new appointments
- Update existing appointments
- Delete appointments
- Retrieve appointments by various criteria (ID, patient, doctor, date)
- Manage appointment status (SCHEDULED, COMPLETED, CANCELLED, NO_SHOW)
- Business hours validation (9:00 AM to 10:00 PM)
- Automatic doctor availability management
- Input validation and error handling

## Technical Stack
- Java with Spring Boot
- Spring Data JPA for data persistence
- PostgreSQL for production database
- H2 for development/testing
- Maven for dependency management
- Spring WebFlux for reactive client operations

## API Endpoints

### Create Appointment
```http
POST /appointments
```
Creates a new appointment with the provided details.

### Get Appointment by ID
```http
GET /appointments/{id}
```
Retrieves a specific appointment by its UUID.

### Get All Appointments
```http
GET /appointments?page={page}&size={size}
```
Retrieves a paginated list of all appointments.

### Update Appointment
```http
PUT /appointments/{id}
```
Updates an existing appointment with new details.

### Delete Appointment
```http
DELETE /appointments/{id}
```
Deletes an existing appointment.

### Get Appointments by Patient
```http
GET /appointments/patient/{patientId}
```
Retrieves all appointments for a specific patient.

### Get Appointments by Doctor
```http
GET /appointments/doctor/{doctorId}
```
Retrieves all appointments for a specific doctor.

### Get Appointments by Date
```http
GET /appointments/date/{date}
```
Retrieves all appointments for a specific date (ISO format: YYYY-MM-DD).

### Update Appointment Status
```http
PATCH /appointments/{id}/status/{status}
```
Updates the status of an appointment (SCHEDULED, COMPLETED, CANCELLED, NO_SHOW).

## Data Models

### Appointment Request DTO
```json
{
  "patientId": "UUID",
  "doctorId": "UUID",
  "date": "YYYY-MM-DD",
  "startTime": "HH:mm",
  "endTime": "HH:mm",
  "notes": "string",
  "status": "SCHEDULED|COMPLETED|CANCELLED|NO_SHOW"
}
```

### Appointment Response DTO
```json
{
  "id": "UUID",
  "patientId": "UUID",
  "doctorId": "UUID",
  "date": "YYYY-MM-DD",
  "startTime": "HH:mm",
  "endTime": "HH:mm",
  "notes": "string",
  "status": "SCHEDULED|COMPLETED|CANCELLED|NO_SHOW"
}
```

## Business Rules
1. Appointments can only be scheduled during business hours (9:00 AM to 10:00 PM)
2. Appointments cannot be scheduled in the past
3. End time must be after start time
4. Patient and doctor IDs are required
5. Date and time slots cannot be null
6. Doctor availability is automatically managed
7. Appointment status changes are tracked

## Error Handling
The service includes comprehensive error handling for:
- Entity not found exceptions (404)
- Validation errors (400)
- Illegal argument exceptions (400)
- General application errors

## Setup and Installation

1. Clone the repository
2. Configure database properties in `application.properties`
3. Run Maven build:
```bash
./mvnw clean install
```
4. Start the service:
```bash
./mvnw spring-boot:run
```

## Dependencies
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Web
- Spring Boot DevTools
- PostgreSQL Driver
- H2 Database (for testing)
- Spring Boot Starter WebFlux
