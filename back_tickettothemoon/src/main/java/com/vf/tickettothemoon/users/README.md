# USERS

## Description
L’ensemble des packages et classes concernant l’utilisateur « utilisateur », soit le grand public qui utilise le site de billetterie. 
Un visiteur peut créer un compte et consulter des spectacles. Il n’est pas obligé de consulter les spectacles s’il n’y a qu’un seul spectacle à proposer celui-ci est automatiquement sélectionné.
Un visiteur peut acheter un spectacle en étant authentifié ou pas. S’il n’est pas authentifié il ne pourra plus réimprimer son billet une fois émis et reçu par mail.
Un visiteur peut gérer son panier pour ajouter, retirer des places, choisir un tarif ou se contenter du choix par défaut d’une place à tarif normal. Le panier est sauvegardé dans la session d’un visiteur authentifié.
Si le visiteur est authentifié il pourra en plus gérer son profil et consulter ses commandes actuelles et passées.

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