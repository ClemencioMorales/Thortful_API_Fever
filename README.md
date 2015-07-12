# Thortful API Fever
Simple REST API made with Spring Boot that lets a user login and once authenticated, returns a list of cities. 

-The list of cities is not available without prior authentication. 

-The user can logout to finish the session.

-The web application is configured to run in the port 8081 and not the default one, 8080. However, 
this behaviour can be modified by updating the variable server.port=port_number in the application.properties file

#Available Endpoints

**http://localhost:8081/login/{user}/{password}**

-Idempotent operation. Logs the user into the web application. There are two available users, hardcoded in the class 
AuthenticationService.java; the user "User" with password "P4ssw0rd" and "UserOther" with "pAsswd".

-Example:
http://localhost:8081/login/User/P4ssw0rd

**http://localhost:8081/retrieveCities**

-If and only if the user is logged in, retrieves a list of cities, hardcoded in the class CitiesService.java.

-Example:
http://localhost:8081/retrieveCities

**http://localhost:8081/logout**

-Logs the user out of the web application.

-Example:
http://localhost:8081/logout


