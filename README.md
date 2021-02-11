# Phone Number Validation(frontend)
This application is to validate phone numbers for specific patterns.

## Tools
spring boot thymeleaf

## How To Run java application:
open cmd at code location then build the code using --> mvn clean package -DskipTests
and use this one to run the app --> java -jar {jar path}\phoneNumbersValidationClient-1.0-SNAPSHOT.jar

There are two APIs; one API for getting  all customers.
It takes page number and page size as parameters, you can use http://localhost:8082/?size=10&page=1
and the other allow you to filter data by country/state, you can choose country/state then press on search button 

