# PROJECT : TicketToTheMoon
## About The Project
TicketToTheMoon is a ticket office website. Main features are :
- tickettothemoon_server : a back office for venues to operate their shows. 
- tickettothemoon_client : a front office aimed at the public to buy tickets from.

## Build with
Software stack:
- SpringBoot
- VueJs3
- Gradle

CI/CD stack:
- Docker, Docker compose, Kubernetes
- Nexus, Drone, SonarQube  

Servers:
- Tomcat, Apache, Ngnix

Tests:
- Junit

## Documentation  
Swagger-ui  

---  

# GETTING STARTED
## Prerequisites
+   xx
``` xx install xx@latest -g ```
## LOCALLY
### Installation
1. Clone the repo
```git clone https://github.com/vifeng/Project-Name.git```
2. Install xx packages
3. `gradle init`
4. `gradle run`
### Database configuration
In its dev configuration, the application uses an in-memory database (H2) which gets populated at startup with data. A similar setups is provided for the prod configuration with MySQL for a persistent database configuration. Please specify the profile needed.
There is two ways:
1. Update application properties: open the application.properties file, then specify your environnement as such :
   1. Dev :     spring.profiles.active=h2,dev 
   2. Prod :    spring.profiles.active=mysql,prod
2. Use a Spring Boot JVM parameter: simply start the JVM with the -Dspring.profiles.active=mysql.prod parameter.
3. Check the database properties in either file : application-mysql.properties, application-h2.properties
4. Start your mysql server if needed
5. The localhost host should be set for a MySQL dabase instance started on your local machine. 
   Visit [h2-console](http://localhost:8080/h2-console) in your browser.
   Visit [mysql-console](localhost:3306/tickettothemoon?useUnicode=true) in your browser.
## Run
1. go to ./tickettothemoon_server
2. On your terminal run $ gradle bootRun
3. Visit [http://localhost:8080](http://localhost:8080) in your browser.

## DOCKER
### Database configuration
You may also start a MySql database with docker:
docker run --name mysql-tickettothemoon -e MYSQL_ROOT_PASSWORD=tickettothemoon -e MYSQL_DATABASE=tickettothemoon -p 3306:3306 mysql:5.7

TODO:
---  
	
# GENERAL INFO
## Status  
just started. Tests.  
https://shields.io/
## Roadmap 
See the open issues for a list of proposed features (and known issues). If you have ideas for releases in the future, it is a good idea to list them in the README.
## Features
## Visuals
## Usage
Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.
## Architecture
3-layers : presentation --> service --> repository  

## Versions and platform support
## Tests results
## Issues and bugs

---  

# CONTRIBUTE / CONTACT
Feel free to send pull requests.
You can contact me on github.


# LICENCE
[MIT](./LICENSE.txt)  
