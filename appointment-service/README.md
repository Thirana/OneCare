# Appointment Service

A microservice for managing appointments in the OneCare healthcare system. This service integrates with the Doctor Service for availability management and will integrate with the Patient Service for patient verification.

## Features
- Create appointments using doctor's availability slots
- View and manage appointments
- Automatic availability management through Doctor Service
- Service discovery using Eureka
- Load-balanced service communication

## API Endpoints

### Create Appointment
```http
POST /appointments
Content-Type: application/json

{
    "patientId": "UUID",    // Patient ID from patient service
    "doctorId": "UUID",     // Doctor ID from doctor service
    "availabilityId": "UUID", // Availability slot ID from doctor service
    "notes": "string"
}
```
Creates a new appointment using an available time slot. The service will:
1. Verify patient exists
2. Verify availability is not already booked
3. Create the appointment
4. Mark the availability as booked in the doctor service

### Get Appointment by ID
```http
GET /appointments/{id}
```
Retrieves a specific appointment by its ID.

### Get All Appointments
```http
GET /appointments?page=0&size=10
```
Retrieves a paginated list of all appointments.

### Delete Appointment
```http
DELETE /appointments/{id}
```
Deletes an appointment and marks its availability slot as available in the doctor service.

### Get Patient's Appointments
```http
GET /appointments/patient/{patientId}
```
Retrieves all appointments for a specific patient.

## Data Models

### Appointment Entity
```java
public class Appointment {
    private UUID id;              // Auto-generated
    private UUID patientId;       // Required
    private UUID doctorId;        // Required
    private UUID availabilityId;  // Required
    private String notes;         // Optional
}
```

### AppointmentRequestDTO
```json
{
    "patientId": "UUID",
    "doctorId": "UUID",
    "availabilityId": "UUID",
    "notes": "string"
}
```

### AppointmentResponseDTO
```json
{
    "id": "UUID",
    "patientId": "UUID",
    "doctorId": "UUID",
    "availabilityId": "UUID",
    "notes": "string"
}
```

## Business Rules
1. Appointments are immutable (can only be created or deleted)
2. Each availability slot can only be booked once
3. Patient ID is required
4. Doctor ID is required
5. Availability slot ID is required
6. Doctor's availability is managed through the doctor service
7. Appointments can only be created for slots that exist and are not booked
8. Deleting an appointment automatically releases the availability slot

## Error Handling
The service includes comprehensive error handling for:
- Entity not found exceptions (404)
- Validation errors (400)
- Illegal state exceptions (409) - when slot is already booked
- Illegal argument exceptions (400)
- Service communication errors (503)

## Service Dependencies
- Doctor Service: For availability management
- Patient Service: For patient verification (to be implemented)
- Eureka Server: For service discovery

## Technical Dependencies
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Web
- Spring Cloud Netflix Eureka Client
- Spring Boot Starter WebFlux
- Spring Boot DevTools
- H2 Database (for development)
- PostgreSQL Driver (for production)

## Setup and Installation

1. Clone the repository
2. Configure application.properties:
```properties
spring.application.name=appointment-service
server.port=5000
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```
3. Run Maven build:
```bash
./mvnw clean install
```
4. Start the service:
```bash
./mvnw spring-boot:run
```

## Required Services
Make sure these services are running:
1. Eureka Server (discovery-server) on port 8761
2. Doctor Service on port 4000
