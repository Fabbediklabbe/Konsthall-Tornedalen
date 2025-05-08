# 🖼️ Tornedalens Konsthall

En webbapplikation för den påhittade konsthallen **Tornedalens Konsthall**. Applikationen fungerar som en hemsida där besökare kan läsa om aktuella och kommande utställningar, konstnärer, samt hitta praktisk information som öppettider och kontaktuppgifter.

---

# Funktioner

- Visa information om aktuella och kommande utställningar
- Presentera konstnärer och verk
- Kontaktinformation och öppettider
- Modern och responsiv design med HTML, CSS och JavaScript

**Chatforum**
- Skapa trådar för att hitta nya vänner eller diskutera aktuella utställningar
- Kommentera på andras trådar

**Användarsystem**
- Inloggning med e-post och lösenord
- Lösenord lagras hashat med Spring Security
- Automatisk koppling mellan användare och trådar/kommentarer

**Internationellt språkstöd (i18n)**
- Språkstöd för svenska, finska, engelska och nordsamiska
- användning av *Thymleaf* och messages_xx.properties för översättningar

---

# Teknologier

- **Java & Spring Boot** – Backend och applikationslogik  
- **Thymeleaf** – Rendering av HTML-sidor på serversidan  
- **MySQL** – Databas för att lagra utställningar och annan information  
- **HTML, CSS, JavaScript** – Frontend  
- **Spring Data JPA** – För att hantera databasen  
- **Lombok** – För att förenkla Java-kod  
- **Spring DevTools** – För smidigare utveckling  

---

# Guide

## Installation
**1. Klona projektet**  
git clone https://github.com/Fabbediklabbe/Konsthall-Tornedalen.git

**2. gå in i projektmappen**  
cd Tornedalen-Konsthall

**3. Bygg projektet**  
./mvnw clean install -DskipTests

**4. Starta applikationen**  
./mvnw spring-boot:run

**5. Öppna webbsidan i valfri webbläsare**  
localhost:8080

**VIKTIGT!**  
För att kunna starta applikationen krävs det att du skapar en "application.properties" fil. Denna ska ha följande sökväg: 
**src\main\resources\application.properties**
Filen går att få tag på genom att kontakta mig, alternativt att du konfigurerar den själv med följande template och byter ut det innanför '[]'
```properties
spring.application.name=Tornedalen-Konsthall
spring.jpa.properties.hibernate.default_schema=Tornedalen
spring.jpa.hibernate.ddl-auto=validate
spring.datasource.url=jdbc:mysql://[LÄNK TILL DIN DATABAS]:3306/Tornedalen
spring.datasource.username=[ANVÄNDARNAMN TILL DIN DATABAS]
spring.datasource.password=[LÖSENORD TILL DIN DATABAS]
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql: true
```