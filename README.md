# ATMLocator
ATM Locator project for Mobiquity

A spring boot project which consumes a rest-api(https://www.ing.nl/api/locator/atms/), and returns the data with below endpoints
1.	/atms/all – This returns all the ATMs information returned by the ing.nl rest api
2.	/atms/city/{city} – This returns the ATMs information which are located in the city that is same as the given input city value.

# How it is Implemented ?

Used spring initializr(start.spring.io) to generate the basic code structure and architecture of the spring boo rest application.
- Configured the rest-api endoint ( https://www.ing.nl/api/locator/atms/ ) in the application.properties.
- This web-service provides a json response which is not well-formed, contains a few unused characters in the beginning.
- To make it well-formed, removed the unnecessary characters.
- To represent the resultant Json response, created a set of model classes with the structure same as the json response.

# How is the Application Designed ?

A Spring MVC design in general: 
-	Request Callstack : Client(Browser/ Postman) -> Controller -> Service, and vice a versa for response.

Controller :
ATMLocatorController.java : exposes 2 rest API's 
- /atms/all : Lists all the ATMs information exposed by rest-api service, as a proper JSON respone.
- /atms/city/{city} : Filters and lists all the ATMs which are located in the city is same as the provided city, as a proper JSON response.

In addition to these the two required services, there are two more utility services
- /atms/countByCity : Lists all the ATMs count per city, sorted by count in ascending order.
- /atms/countByCityDesc : Lists all the ATMs count per city, sorted by count in descending order.

Service :
ATMLocatorService.java : Wraps the business logic behind the consumption of the exposed given rest-api services utilizing output from repository.

In addition to it, the service also stores all the ATMs information as cache to process the subsequent request without hitting the rest-api endpoint everytime to respond faster in our testing.
To enable this, we need to set cacheATMsData to true in application.properties

Controller :
ATMLocatorController.java : exposes 2 rest API's 
- /atms/all : Lists all the ATMs information exposed by rest-api service, as a proper JSON respone.
- /atms/city/{city} : Filters and lists all the ATMs which are located in the city is same as the provided city, as a proper JSON response.

Tools used : Maven, JDK 8, Spring Boot, Tomcat 9

# How to Run ?

Maven, java should be pre-installed.

- clone the ATMLocator.git
- run “mvn clean spring-boot:run” command inside the ATMLocator folder
