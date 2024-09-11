# eventhub-site

## About The Project

EventHub is a ticket office website. The primary goal of EventHub is to facilitate the management and sale of tickets for events by providing an interface for venue managers and end-users. On one side, professionals can manage events, venues, sessions, and pricing. On the other, the general public can view shows, make reservations, and purchase tickets.
Main features are :

- eventhub-site : a user interface visible to users (both the public and managers) for interacting with the application.
- eventhub-server : REST API to manage the data. It contains the business logic, such as data processing, event management, venue management, reservations, and more.

## Documentation

[General Documentation of the project](https://github.com/vifeng/TicketToTheMoon/tree/main/documentation) - such as functional requirements, database... mainly in french
[A wiki for development notes](https://github.com/vifeng/TicketToTheMoon/wiki)

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
