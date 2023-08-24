Table of contents

- [PROJECT : TicketToTheMoon_BackEnd](#project--tickettothemoon_backend)
  - [About The Project](#about-the-project)
  - [Documentation](#documentation)
  - [Build with](#build-with)
    - [Versions used for the development](#versions-used-for-the-development)
- [GETTING STARTED](#getting-started)
  - [1. RUNNING LOCALLY](#1-running-locally)
    - [Installation \& running](#installation--running)
    - [Database configuration](#database-configuration)
- [Few usefull commands](#few-usefull-commands)
- [REST API \& actuator](#rest-api--actuator)
  - [Actuator](#actuator)
  - [Documentation](#documentation-1)

---

---

# PROJECT : TicketToTheMoon_BackEnd

## About The Project

TicketToTheMoon is a ticket office website. Main features are :

- tickettothemoon_FrontEnd : a front office aimed at the public to buy tickets from and a back office for venues to operate their shows.
- tickettothemoon_BackEnd : REST API to manage the data.

## Documentation

[Documentation] https://github.com/vifeng/TicketToTheMoon/tree/main/documentation  
README files are available in each folder to explain its purpose.

## Build with

### Versions used for the development

- Gradle 8.0.2
- Java 17.0.2
- Kotlin 1.8.10

# GETTING STARTED

## 1. RUNNING LOCALLY

### Installation & running

clone the project

```
   git clone https://github.com/vifeng/tickettothemoon.git
```

Backend

```sh
   cd tickettothemoon_BackEnd
   gradle bootRun
```

Access to the backend API [http://localhost:8080/api](http://localhost:8080/api) in your browser.

### Database configuration

I have use the in memory database H2 for the moment. Once the application is up and running, visit the [h2-console](http://localhost:8080/h2-console) in your browser.
Connect to the database with the credentials which are in the [application.properties](src/main/resources/application.properties) file.
</br>

# Few usefull commands

```sh
gradle build -x test
# Build without the tests
CTL+C
# Shutdown the front or backend from the terminal
./gradlew wrapper --gradle-version  8.1.1
# update gradle wrapper version
gradle clean
# clean the build folder
gradle clean build
# clean the build folder and build the project
gradle check
# run all verification tasks, including tests and linting
```

<br>

---

# REST API & actuator

## Actuator

<!-- TO_UPDATE: README file -->

- There are actuator health check and info routes as well:
  - http://localhost:3306/tickettothemoon/actuator/health
  - http://localhost:3306/tickettothemoon/actuator/info

## Documentation

<br>

---
