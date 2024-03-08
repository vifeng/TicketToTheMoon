Table of contents

- [PROJECT : TicketToTheMoon](#project--tickettothemoon)
  - [About The Project](#about-the-project)
  - [Documentation](#documentation)
  - [Build with](#build-with)
    - [Versions used for the development](#versions-used-for-the-development)
- [GETTING STARTED](#getting-started)
  - [1. RUNNING LOCALLY](#1-running-locally)
    - [Installation \& running](#installation--running)
    - [Database configuration](#database-configuration)
- [Few commands](#few-commands)
- [REST API \& actuator](#rest-api--actuator)
  - [Actuator](#actuator)
  - [Documentation](#documentation-1)
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

# PROJECT : eventhubserver

## About The Project

TicketToTheMoon is a ticket office website. Main features are :

- tickettothemoon_FrontEnd : a front office aimed at the public to buy tickets from and a back office for venues to operate their shows.
- eventhubserver : REST API to manage the data.

## Documentation

[Documentation] https://github.com/vifeng/TicketToTheMoon/tree/main/documentation  
README files are available in each folder to explain its purpose.

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
| Junit                           | Spring REST Docs                            |                    |
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

```
   git clone https://github.com/vifeng/tickettothemoon.git
```

Backend

```sh
   cd eventhubserver
   gradle bootRun
```

Access to the backend API [http://localhost:8080](http://localhost:8080) in your browser.

Frontend

```sh
   cd tickettothemoon_FrontEnd
   npm run dev
```

Access the the frontEnd [ http://localhost:5173/](http://localhost:5173/) in your browser.

or you could use the ./start.sh script to run both

```sh
  sh ./start.sh
```

### Database configuration

I have use the in memory database H2 for the moment. Once the application is up and running, visit the [h2-console](http://localhost:8080/h2-console) in your browser.

</br>

# Few commands

```sh
gradle build -x test
# Build without the tests
CTL+C
# Shutdown the front or backend from the terminal
npm list vue
# to check the vue version
```

<br>

---

# REST API & actuator

<!-- TODO_LOW readme file-->

## Actuator

- There are actuator health check and info routes as well:
  - http://localhost:3306/eventhubserver/actuator/health
  - http://localhost:3306/eventhubserver/actuator/info

## Documentation

<br>

---

# GENERAL INFO

## Status

WIP

## Roadmap

See the project's roadmap at [github projects](https://github.com/users/vifeng/projects/2)
See the open issues for a list of proposed features (and known issues) at [github issues](https://github.com/vifeng/TicketToTheMoon/issues)
You can find ideas for releases in the future in the [functional requirements documentation](https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/01_ExpressionBesoin-V5_OK.pdf).

## Features

see [functional requirements documentation](https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/01_ExpressionBesoin-V5_OK.pdf)

## Visuals

<!-- TODO_LOW: readme file-->

## Usage

<!-- TODO_LOW: readme file-->

Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.

## Architecture

3-layers : presentation --> service --> repository  
More information is to be found in the [documentation section](https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/).

## Versions and platform support

More information is to be found in the [documentation section](https://github.com/vifeng/TicketToTheMoon/blob/main/documentation/).

## Tests results

<!-- TODO_LOW: readme file-->

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
