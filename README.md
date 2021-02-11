# Phone Number Validation(frontend)
This application is to validate phone numbers for specific patterns.

## Tools
spring boot thymeleaf


## Description
There are two APIs; one API for getting  all customers.
It takes page number and page size as parameters, you can use http://localhost:8082/?size=10&page=1
and the other allow you to filter data by country/state, you can choose country/state then press on search button 


## How To Run java application
1- open cmd at code location  <br /> 
2- build the code using --> mvn clean package -DskipTests   <br />
3- run the app --> java -jar {jar path}\phoneNumbersValidationClient-1.0-SNAPSHOT.jar


