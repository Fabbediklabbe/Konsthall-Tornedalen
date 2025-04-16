# 🖼️ Tornedalens Konsthall

En webbapplikation för den påhittade konsthallen **Tornedalens Konsthall**. Applikationen fungerar som en hemsida där besökare kan läsa om aktuella och kommande utställningar, konstnärer, samt hitta praktisk information som öppettider och kontaktuppgifter.

---

## 🚀 Funktioner

- Visa information om aktuella och kommande utställningar
- Presentera konstnärer och verk
- Kontaktinformation och öppettider
- Modern och responsiv design med HTML, CSS och JavaScript

---

## 🛠️ Teknologier

- **Java & Spring Boot** – Backend och applikationslogik  
- **Thymeleaf** – Rendering av HTML-sidor på serversidan  
- **MySQL** – Databas för att lagra utställningar och annan information  
- **HTML, CSS, JavaScript** – Frontend  
- **Spring Data JPA** – För att hantera databasen  
- **Lombok** – För att förenkla Java-kod  
- **Spring DevTools** – För smidigare utveckling  

---

## Guide

- **application.properties** - src/main/resources/application.properties
spring.application.name=Tornedalen-Konsthall

spring.jpa.properties.hibernate.default_schema=Tornedalen

spring.jpa.hibernate.ddl-auto=validate

spring.datasource.url=jdbc:mysql://**databaslänk**:3306/Tornedalen
spring.datasource.username=**username**
spring.datasource.password=**password**
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql: true