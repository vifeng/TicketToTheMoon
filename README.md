Table of contents

- [PROJECT : eventhubserver](#project--eventhubserver)
  - [About The Project](#about-the-project)
    - [Application objective](#application-objective)
    - [Three-tier application architecture](#three-tier-application-architecture)
  - [Documentation](#documentation)
  - [Build with](#build-with)
    - [Versions used for the development](#versions-used-for-the-development)
- [GETTING STARTED](#getting-started)
  - [1. RUNNING LOCALLY](#1-running-locally)
    - [Installation \& running](#installation--running)
- [Few commands](#few-commands)
- [API Documentation \& actuator](#api-documentation--actuator)
  - [API Documentation](#api-documentation)
  - [Actuator](#actuator)
- [GENERAL INFO](#general-info)
  - [Status](#status)
  - [Roadmap](#roadmap)
  - [Features](#features)
  - [Versions and platform support](#versions-and-platform-support)
  - [Tests results](#tests-results)
  - [Issues and bugs](#issues-and-bugs)
- [CONTRIBUTE / CONTACT](#contribute--contact)
- [LICENCE](#licence)

---

---

# PROJECT : eventhubserver

## About The Project

EventHub is a ticketing application designed for event industry professionals and the general public.

### Application objective
The primary goal of EventHub is to facilitate the management and sale of tickets for events by providing an interface for venue managers and end-users. On one side, professionals can manage events, venues, sessions, and pricing. On the other, the general public can view shows, make reservations, and purchase tickets.

### Three-tier application architecture
Three-tier architecture : Presentation --> Business logic --> Data layer
More information is to be found in the [documentation section](https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/).
- **Presentation Layer (front-end):** Represented by event-hub-site, which is the user interface developed in Vue.js. This is the part visible to users (both the public and managers) for interacting with the application.
- **Business Logic Layer (back-end):** Represented by event-hub-server, which is the REST API written in Java/Spring Boot. This layer contains the business logic, such as data processing, event management, venue management, reservations, and more.
- **Data Layer:** During development, the application uses an H2 database and test data managed by Flyway.


## Documentation

[General Documentation of the project](https://github.com/vifeng/TicketToTheMoon/tree/main/documentation) - such as functional requirements, database... mainly in french 
[A wiki for development notes](https://github.com/vifeng/TicketToTheMoon/wiki) is available to follow the development process.
README files are available.

## Build with

```
| Software stack                  | CI/CD stack                                 | Servers            |
| ------------------------------- | ------------------------------------------  | ------------------ |
| SpringBoot, Gradle              |                                             | Tomcat             |
| VueJs3, NPM                     |                                             |                    |
|                                 |                                             | Apache             |
| ------------------------------- | ------------------------------------------- |                    |
| Tests                           | Documentation                               |                    |
| ------------------------------- | ------------------------------------------- |                    |
| Junit, mockMVC, slice testing   | Spring REST Docs                            |                    |
```

### Versions used for the development

- Gradle 8.0.2
- Java 17.0.2
- Kotlin 1.8.10
- NPM 9.6.6
- Node v19.8.1
- Vue 3.3.2
- Vite 4.3.6

# GETTING STARTED

## 1. RUNNING LOCALLY

### Installation & running

clone the project

```sh
git clone https://github.com/vifeng/tickettothemoon.git
```

start the Backend

```sh
cd event-hub-server
./gradlew bootRun
```

Access to the backend API documentation [http://localhost:8080/api](http://localhost:8080/api) in your browser.
Access to the Application endpoints such as the EVENTS endpoint [http://localhost:8080/api/events](http://localhost:8080/api/events) in your browser.

start the Frontend

```sh
cd event-hub-site
npm run dev
```

Access the application [ http://localhost:5173/](http://localhost:5173/) in your browser.

# Few commands

In the event-hub-server folder
```sh
./gradlew build -x test
# Build without the tests
./gradlew clean
# clean the build folder
CTL+C
# Shutdown the front or backend from the terminal
```

In the event-hub-site folder
```sh
npm list vue
# to check the vue version.
npm run dev
# run the application
npm run build
# build the application
```

<br>

---

# API Documentation & actuator

## API Documentation

Access to the API documentation at [http://localhost:8080/api](http://localhost:8080/api) in your browser.

## Actuator
Access to the actuator at [http://localhost:8080/actuator](http://localhost:8080/manage/actuator) in your browser.

- There are actuator health check and info routes as well:
  - http://localhost:8080/actuator/health
  - http://localhost:8080/actuator/info


<br>

---

# GENERAL INFO

## Status

The project is in development. 
- The backend is almost finished. All the important buisness logic has been done and tested.
- The frontend is in progress. It only shows a few pages of some get requests to the API.

## Roadmap

See the project's roadmap at [github projects](https://github.com/users/vifeng/projects/2)
See the open issues for a list of proposed features (and known issues) at [github issues](https://github.com/vifeng/TicketToTheMoon/issues)

## Features

see [functional requirements documentation](https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/01_ExpressionBesoin-V5_OK.pdf)


## Versions and platform support

More information about the development process is to be found in the [repository wiki](https://github.com/vifeng/TicketToTheMoon/wiki)

## Tests results

The documentation is depending on the tests results. Thus all the tests are passing otherwise the build couldn't be done.

## Issues and bugs

See the open issues for a list of proposed features (and known issues) at [the project's github issues](https://github.com/vifeng/TicketToTheMoon/issues)
<br>

---

# CONTRIBUTE / CONTACT

- Feel free to send pull requests.
- My issue tracker is available here: https://github.com/tickettothemoon/issues
- You can contact me on github.
  <br>

---

# LICENCE

[MIT](./LICENSE.txt)
