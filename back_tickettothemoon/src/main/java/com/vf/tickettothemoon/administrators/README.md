# ADMINISTRATORS

## Description
L’ensemble des packages et classes concernant l’utilisateur « gestionnaire » qui gère entre autres les évènements du site de billetterie.  
Le gestionnaire doit s’authentifier car toutes ses actions nécessitent une authentification. Il peut gérer son profil, créer et gérer des évènements, consulter les données d’un évènement ou des évènements dans leur ensemble. Il peut diffuser un évènement ce qui nécessite sa création. Un administrateur doit créer les comptes des gestionnaires.


# Conventions

## Architecture
├── controller
└── domain
    ├── constant
    ├── dao_repo
    ├── dto
    ├── model
    └── service

- Controller : Contient les components des classes de type Controller 
- Domain : Contient les packages et classes métiers :
    - Constant : les constantes de l’application.
    - DAO_ Repositories : les classes représentant les entités JPA responsables des opérations en base de données.
    - DTO : les classes DTO des objets métiers.
    - Model : les objets métiers.
    - Services : Contient les components des classes de type Service qui sont responsables de la logique métier et servent à mapper les entités en DTO et inversement.

## Naming Convention
Les différentes classes sont suffixées du nom de leur fonction :
xxxController, xxxService, xxxDto, xxxRepository, xxxException  
Les interfaces seront suffixées d’un « able » : Validable

## Design pattern used
- MVC : utilisé pour dissocier les traitements de la visualisation.
- DAO : utilisé pour pouvoir se connecter à plusieurs bases de données distinctes.
- DTO : utilisé pour transporter plusieurs objets distants en un seul appel distant ou pour les données d’un objet distant non transportable sur le réseau (pas sérialisable).