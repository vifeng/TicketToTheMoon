Table of contents
- [PROJECT : TicketToTheMoon](#project--tickettothemoon)
  - [About The Project](#about-the-project)
  - [Documentation](#documentation)
  - [Build with](#build-with)
  - [Folders](#folders)
- [GETTING STARTED](#getting-started)
  - [1. RUNNING LOCALLY](#1-running-locally)
    - [Prerequisites](#prerequisites)
    - [Installation \& init](#installation--init)
    - [Database configuration](#database-configuration)
    - [Run](#run)
    - [Shutdown](#shutdown)
  - [2. RUNNING WITH DOCKER : TODO](#2-running-with-docker--todo)
    - [Prerequisites](#prerequisites-1)
    - [Database configuration](#database-configuration-1)
    - [Run](#run-1)
    - [Shutdown](#shutdown-1)
- [Few commands](#few-commands)
  - [Gradle](#gradle)
- [REST API \& actuator](#rest-api--actuator)
- [GENERAL INFO](#general-info)
  - [Status](#status)
  - [Roadmap](#roadmap)
  - [Features](#features)
  - [Visuals](#visuals)
  - [Usage](#usage)
  - [Architecture](#architecture)
  - [Versions and platform support](#versions-and-platform-support)
  - [Tests results](#tests-results)
  - [Issues and bugs](#issues-and-bugs)
- [CONTRIBUTE / CONTACT](#contribute--contact)
- [LICENCE](#licence)

---
---

# PROJECT : TicketToTheMoon

## About The Project
TicketToTheMoon is a ticket office website. Main features are :
- tickettothemoon_client : a front office aimed at the public to buy tickets from.
- tickettothemoon_server : a back office for venues to operate their shows. 

## Documentation  
[Documentation] https://github.com/vifeng/TicketToTheMoon/tree/main/documentation   
README files are available in each folder to explain its purpose.

## Build with
```
   | Software stack                 | CI/CD stack                                | Servers           |
   |------------------------------- | ------------------------------------------ | ------------------|
   | SpringBoot, VueJs3, Gradle     | -  Docker, Docker Compose, Kubernetes      | Tomcat            |
   |                                | -  Nexus, Drone, SonarQube                 | Apache            |
   |------------------------------- |------------------------------------------- | Ngnix             |
   | Tests                          | Documentation                              |                   |
   |------------------------------- |------------------------------------------- |                   |
   | Junit                          | Swagger-ui                                 |                   |
```
## Folders
```
.
├── documentation
├── gradle
├── tickettothemoon_client        → frontend module with Vue.js code
│   ├── dist
│   ├── public
│   └── src
│   │   ├── assets
│   │   ├── components
│   │   │   └── icons
│   │   └── service
│   └── build.gradle
├── tickettothemoon_server        → backend module with Spring Boot code
│   ├── bin
│   ├── build
│   ├── gradle
│   └── src
│   │   ├── main
│   │   │   ├── docker
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── vf
│   │   │   │           └── tickettothemoon
│   │   │   │               ├── administrators
│   │   │   │               ├── authentication
│   │   │   │               ├── configuration
│   │   │   │               ├── exceptions
│   │   │   │               ├── tools
│   │   │   │               └── users
│   │   │   └── resources
│   │   │       ├── db
│   │   │       │   ├── h2
│   │   │       │   └── mysql
│   │   │       │       ├── common
│   │   │       │       ├── prod
│   │   │       │       └── test
│   │   │       ├── messages
│   │   │       ├── static
│   │   │       └── templates
│   │   │           └── prof
│   │   └── test
│   │       └── java
│   │           └── com
│   │               └── vf
│   │                   └── tickettothemoon
│   │                       ├── administrators
│   │                       ├── authentication
│   │                       └── users
│   └── build.gradle
└── build.gradle            → gradle parent managing both modules

            
```
</br>

</br>

---  
# GETTING STARTED
You can run TicketToTheMoon [locally](#1-running-locally) or with [docker](#2-running-with-docker) :     

## 1. RUNNING LOCALLY
### Prerequisites
<!-- TODO: -->
+   Gradle, Java 

### Installation & init
With Gradle command line   
   1. Clone
```
   git clone https://github.com/vifeng/tickettothemoon.git  
   cd tickettothemoon_server
```
   2. if first start, init : ``` gradle init```   

### Database configuration
In its dev configuration, the application uses an in-memory database (H2) which gets populated at startup with data. A similar setups is provided for the prod configuration with MySQL for a persistent database configuration. Please specify the profile needed.   
1. Update the application properties file: `application.properties` , then specify your environnement as such :
   1. Dev :     spring.profiles.active=h2,dev 
   2. Prod :    spring.profiles.active=mysql,prod
   3. Test :    spring.profiles.active=mysql,test
2. Check the database properties in files : `application-mysql.properties`, `application-h2.properties`. The host should be set for localhost. 
3. Start your mysql server if needed
4. Once the application is up and running, visit the chosen database:   
   + [h2-console](http://localhost:8080/h2-console) in your browser.
   + [mysql](http://localhost:3306/tickettothemoon?useUnicode=true) in your favorite application.

### Run
1. go to your project
``` sh
cd ./tickettothemoon
```  
2. Run tickettothemoon_server : 
``` sh
gradle bootRun 
```  
1. Run tickettothemoon_client :  
``` sh
cd tickettothemoon_client
gradle npmDev 
```  
<!-- 2. TODO: Run tickettothemoon entire app (client and server) : ``` gradle bootRun ```   -->
1. Access tickettothemoon [http://localhost:8080](http://localhost:8080) in your browser.   
2. 1. Access tickettothemoon [ http://localhost:5173/]( http://localhost:5173/) in your browser.   

Build without the tests ``` gradle build -x test ```   

### Shutdown
From the terminal: `CTL+C`  
</br>

## 2. RUNNING WITH DOCKER : TODO
### Prerequisites
<!-- TODO: -->
+   Docker
``` xx install xx@latest -g ```

### Database configuration
1. You may also start a MySql database with docker:   
```docker run --name root-tickettothemoon -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=tickettothemoon -p 3306:3306 mysql:5.7 ```
1. Once the application is up and running, visit:      
   [h2-console](http://localhost:8080/h2-console)   
   [mysql-console](http://localhost:3306/tickettothemoon?useUnicode=true)   
### Run
1. ```docker run -p 3306:3306 tickettothemoon/tickettothemoon_server```
2. Access tickettothemoon_server [http://localhost:8080](http://localhost:8080) in your browser.
### Shutdown
<!-- TODO: and the main docker command -->
<br>

---  

# Few commands
## Gradle
`gradle -q projects`    to see the projects and sub projects. -q for quiet.   
`gradle :tickettothemoon_server:build` launch the task build of the tickettothemoon_server sub-project   

<br>

---  
TODO: + screenshot
# REST API & actuator
+ OpenAPI REST API documentation presented here (after application start):   
You can reach the swagger UI with this URL http://localhost:3306/tickettothemoon/.   
You then can get the Open API description reaching this URL localhost:3306/tickettothemoon/api-docs.
+  There are actuator health check and info routes as well:
   + http://localhost:3306/tickettothemoon/actuator/health
   + http://localhost:3306/tickettothemoon/actuator/info   

<br>

---  
# GENERAL INFO
## Status  
WIP: environnement.  
Tests. https://shields.io/
## Roadmap 
See the open issues for a list of proposed features (and known issues) at [github issues](https://github.com/vifeng/TicketToTheMoon/issues)
You can find ideas for releases in the future in the [functional requirements documentation] (https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/01_ExpressionBesoin-V5_OK.pdf).
## Features
see [functional requirements documentation] (https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/01_ExpressionBesoin-V5_OK.pdf)
## Visuals
<!-- TODO: -->
## Usage
<!-- TODO: -->
Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.
## Architecture
3-layers : presentation --> service --> repository  
More information is to be found in the [documentation section] (https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/).
## Versions and platform support
More information is to be found in the [documentation section] (https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/).
## Tests results
<!-- TODO:  -->
## Issues and bugs
See the open issues for a list of proposed features (and known issues) at [github issues](https://github.com/vifeng/TicketToTheMoon/issues)
<br>

---  
# CONTRIBUTE / CONTACT
+ Feel free to send pull requests.
+ My issue tracker is available here: https://github.com/tickettothemoon/issues
+ You can contact me on github.
<br>

---  
# LICENCE
[MIT](./LICENSE.txt)  
