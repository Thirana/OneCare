# Patient Service (Microservice)

This is the **Patient Service** of a microservices-based hospital management system. It is built using **Spring Boot** and uses a dedicated **PostgreSQL database** container, both orchestrated using **Docker Compose**.


---

##  How to Run

### Prerequisites

- Docker & Docker Compose installed
- Java 21+ (only needed if running without Docker)
- Maven (only needed if running without Docker)

---

### ▶️ Start the service using Docker Compose

1. Clone this repository:
   ```bash
   git clone https://github.com/Thirana/OneCare.git
   cd patient-service
   
2. Add following `.env` file to patient-service root directory
    ```env
    # Database config
    POSTGRES_DB=db
    POSTGRES_USER=admin_user
    POSTGRES_PASSWORD=password
    
    # JPA config
    SPRING_JPA_HIBERNATE_DDL_AUTO=update
    SPRING_SQL_INIT_MODE=always

3. Run docker Compose
   ```bash
   docker compose up --build