# 🏴‍☠️ GrandLine HQ - World Government Operations API

GrandLine HQ is a robust, production-grade RESTful backend service built for managing World Government operations across the Grand Line. Developed using **Java 21** and **Spring Boot**, this application handles complex domain logic including pirate bounties, Devil Fruit consumption constraints, crew management with automated captain succession, and military mobilization via Marine Buster Calls.

## 🚀 Tech Stack & Tools
* **Language:** Java 21
* **Framework:** Spring Boot (v4.1.2)
* **Persistence & Database:** Spring Data JPA, PostgreSQL, Flyway (Database Migrations)
* **Security & Concurrency:** Spring Security, Pessimistic & Optimistic Concurrency Locking (`@Version`, `PESSIMISTIC_WRITE`)
* **Testing:** JUnit 5, Mockito
* **Build & Version Control:** Maven, Git / GitHub, Eclipse IDE

---

## 🏛️ Core Features & Architecture

### 1. 🏴‍pirates Management & Pagination
* **Endpoints:** Full CRUD capabilities for pirates, plus dedicated paginated endpoints for all pirates and wanted criminals (`/api/pirates` and `/api/pirates/wanted`) utilizing Spring Data `Pageable`.
* **Bounty Adjustments:** Secure endpoints to update pirate bounties with strict validation rules.

### 2. 🍎 Devil Fruit Encyclopedia & Consumption Engine
* **Encyclopedia:** Register newly discovered Devil Fruits into the system.
* **Consumption Rules:** Enforces strict constraints—a pirate can only consume a single Devil Fruit, and a specific Devil Fruit cannot be eaten twice, backed by comprehensive unit test suites.

### 3. ⚓ Crew Mechanics & Automated Succession
* **Dynamic Management:** Recruitment and leave handling that automatically recalculates total crew bounties.
* **Captain Succession Logic:** When a crew's captain leaves or is defeated, the system automatically elects a new captain based on the highest active bounty among the remaining crewmates.

### 4. 🚢 Marine Fleets & Buster Call Protocol
* **Database Migrations:** Managed via Flyway SQL migration scripts (`V1` schema and `V2` marine fleets).
* **Buster Call Trigger:** Validates military readiness before execution—requires a minimum deployment of 10 ships and checks that the fleet status is set to `STANDBY` before activating.

### 5. 🛡️ Concurrency & Global Error Handling
* **Data Integrity:** Utilizes optimistic locking (`@Version`) and pessimistic write locks (`PESSIMISTIC_WRITE`) on high-concurrency entities like bounty claims to prevent race conditions.
* **Global Exception Handling:** Centralized `@ControllerAdvice` mapping custom exceptions (`PirateNotFoundException`, `InvalidAmountException`, etc.) to structured, descriptive JSON error payloads with correct HTTP status codes.

---

## 🛠️ Getting Started Locally

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/YourUsername/grandline-hq.git](https://github.com/Embbrz/grandline-hq.git)