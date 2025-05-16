# OneCare Healthcare System

A microservices-based healthcare system for managing patients, doctors, and appointments.

## System Architecture

The system consists of four main microservices:

1. **Discovery Server** (Port: 8761)
   - Service registry and discovery using Netflix Eureka
   - Enables dynamic service discovery and load balancing

2. **Patient Service** (Port: 3000)
   - Manages patient information
   - Handles patient registration and profile management

3. **Doctor Service** (Port: 4000)
   - Manages doctor information and availability
   - Handles doctor registration and availability slots

4. **Appointment Service** (Port: 5000)
   - Manages appointment bookings
   - Coordinates between patient and doctor services

## Complete Appointment Booking Workflow

Here's a step-by-step guide on how to book an appointment, with example API calls:

### 1. View Available Doctors

First, get a list of all doctors or filter by specialization:

```http
# Get all doctors
GET http://localhost:4000/doctors

# Get doctors by specialization
GET http://localhost:4000/doctors/specialization/Cardiology
```

Example Response:
```json
[
    {
        "id": "11111111-1111-1111-1111-111111111111",
        "name": "Dr. Alice Smith",
        "specialization": "Cardiology",
        "email": "alice.smith@example.com",
        "phone": "1234567890"
    },
    {
        "id": "22222222-2222-2222-2222-222222222222",
        "name": "Dr. Bob Johnson",
        "specialization": "Cardiology",
        "email": "bob.johnson@example.com",
        "phone": "2345678901"
    }
]
```

### 2. Check Doctor's Availability

Once you've chosen a doctor, get their availability slots:

```http
GET http://localhost:4000/doctors/11111111-1111-1111-1111-111111111111/availability
```

Example Response:
```json
[
    {
        "id": "aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
        "date": "2025-05-10",
        "startTime": "09:00:00",
        "endTime": "12:00:00",
        "booked": false
    },
    {
        "id": "aaaa2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
        "date": "2025-05-12",
        "startTime": "14:00:00",
        "endTime": "17:00:00",
        "booked": false
    }
]
```

### 3. Book an Appointment

Using your patient ID, the chosen doctor's ID, and the selected availability slot ID, create an appointment:

```http
POST http://localhost:5000/appointments
Content-Type: application/json

{
    "patientId": "99999999-9999-9999-9999-999999999999",
    "doctorId": "11111111-1111-1111-1111-111111111111",
    "availabilityId": "aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
    "notes": "Regular checkup"
}
```

Example Response:
```json
{
    "id": "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb",
    "patientId": "99999999-9999-9999-9999-999999999999",
    "doctorId": "11111111-1111-1111-1111-111111111111",
    "availabilityId": "aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
    "notes": "Regular checkup"
}
```

### 4. View Your Appointments

Check your booked appointments:

```http
GET http://localhost:5000/appointments/patient/99999999-9999-9999-9999-999999999999
```

Example Response:
```json
[
    {
        "id": "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb",
        "patientId": "99999999-9999-9999-9999-999999999999",
        "doctorId": "11111111-1111-1111-1111-111111111111",
        "availabilityId": "aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
        "notes": "Regular checkup"
    }
]
```

### 5. Cancel an Appointment

If needed, you can cancel your appointment:

```http
DELETE http://localhost:5000/appointments/bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb
```

This will:
1. Delete the appointment
2. Mark the availability slot as available again

## Service Interactions

When booking an appointment, the following interactions occur:

1. **Appointment Service → Patient Service**
   - Verifies patient exists (to be implemented)
   - Checks patient eligibility (future enhancement)

2. **Appointment Service → Doctor Service**
   - Verifies doctor exists
   - Checks availability slot exists and is not booked
   - Updates availability slot status

## Setup and Running the System

1. Start the Discovery Server:
```bash
cd discovery-server
./mvnw spring-boot:run
```

2. Start the Doctor Service:
```bash
cd doctor-service
./mvnw spring-boot:run
```

3. Start the Patient Service:
```bash
cd patient-service
./mvnw spring-boot:run
```

4. Start the Appointment Service:
```bash
cd appointment-service
./mvnw spring-boot:run
```

## Development Environment

- Java 21
- Spring Boot 3.4.5
- Spring Cloud 2024.0.1
- Maven for dependency management
- H2 Database (development)
- PostgreSQL (production)

## API Documentation

Detailed API documentation for each service can be found in their respective README files:
- [Appointment Service Documentation](appointment-service/README.md)
- [Doctor Service Documentation](doctor-service/README.md)
- [Patient Service Documentation](patient-service/README.md)

## Error Handling

The system implements comprehensive error handling:

- **400 Bad Request**: Invalid input data
- **404 Not Found**: Resource doesn't exist
- **409 Conflict**: Resource conflict (e.g., already booked)
- **503 Service Unavailable**: Service communication errors