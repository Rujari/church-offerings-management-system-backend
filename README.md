

- **Names:** BYIRINGIRO Aime
- **Student ID:** 27870
- **Project name:** Church Offerings Management System (church-offerings-management-system)


A Spring Boot REST API that records the pledges and offerings of a church.
Members make pledges that are followed up by treasurers. Each treasurer serves
one parish and can manage several fund types. A pledge moves from PLEDGED to
PARTIALLY_PAID and ends FULFILLED or WITHDRAWN. Once a pledge is FULFILLED, the
treasurer issues receipts.

Entities: FundType, Parish, Treasurer, Member, Pledge, Receipt.

- Java 17
- Spring Boot 3 (Spring Web, Spring Data JPA)
- PostgreSQL
- Maven


1. **Install the requirements:** JDK 17 (or newer), Maven and PostgreSQL.
2. **Clone the repository:**
   ```bash
   git clone https://github.com/Rujari/church-offerings-management-system-backend
   cd church-offerings-management-system-backend
   ```
3. **Create the database** (in pgAdmin or psql):
   ```sql
   CREATE DATABASE church_offerings_db;
   ```
4. **Configure the connection** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/church_offerings_db
   spring.datasource.username=postgres
   spring.datasource.password=YOUR_POSTGRES_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
5. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```
   The API starts on `http://localhost:8080`. The tables are created
   automatically by Hibernate.
6. **Test with Postman:** import
   `church-offerings-management-system-midterm.postman_collection.json`
   from the root of this repository.
7. **Enter the seed data first** (fund types, parishes, treasurers, members,
   pledges) through the `POST /api/.../save` endpoints, then test the other
   requirements.

