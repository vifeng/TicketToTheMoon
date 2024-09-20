Table of contents
- [eventhub-site](#eventhub-site)
  - [About The Project](#about-the-project)
  - [Documentation](#documentation)
  - [Versions used for the development](#versions-used-for-the-development)
  - [Recommended IDE Setup](#recommended-ide-setup)
  - [Project Setup](#project-setup)
    - [Compile and Hot-Reload for Development](#compile-and-hot-reload-for-development)
    - [Compile and Minify for Production](#compile-and-minify-for-production)
    - [Lint with ESLint](#lint-with-eslint)

---


# eventhub-site

## About The Project

EventHub is a ticket office website. The primary goal of EventHub is to facilitate the management and sale of tickets for events by providing an interface for venue managers and end-users. On one side, professionals can manage events, venues, sessions, and pricing. On the other, the general public can view shows, make reservations, and purchase tickets.
Main features are :

- eventhub-site : a user interface visible to users (both the public and managers) for interacting with the application.
- eventhub-server : REST API to manage the data. It contains the business logic, such as data processing, event management, venue management, reservations, and more.

## Documentation

- [General Documentation of the project](https://github.com/vifeng/TicketToTheMoon/tree/main/documentation) - such as functional requirements, database... mainly in french  
- [A wiki](https://github.com/vifeng/TicketToTheMoon/wiki) presents : 
  - [a slideshow presentation for the application](https://github.com/vifeng/TicketToTheMoon/wiki#:~:text=A%20slideshow%20presentation%20for%20the%20application)
  - a video demonstration of the application and the commands to run the demo yourself
  - the development process notes

## Versions used for the development

- NPM 9.6.6
- Node v19.8.1
- Vue 3.3.2
- Vite 4.3.6

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

## Project Setup

clone the project

```sh
git clone https://github.com/vifeng/tickettothemoon.git
cd TicketToTheMoon/eventhub-site
```

```sh
npm install
npm audit fix
# if prompt to do it
```

### Compile and Hot-Reload for Development

Of course please run evenhub-server first !

```sh
npm run dev
```

Access the the frontEnd [ http://localhost:5173/](http://localhost:5173/) in your browser.

### Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```
