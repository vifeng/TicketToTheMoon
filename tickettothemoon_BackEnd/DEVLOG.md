TABLE OF CONTENTS

- [Developpement Notes](#developpement-notes)
  - [Info on the project](#info-on-the-project)
    - [Project board](#project-board)
    - [Code and issues](#code-and-issues)
  - [Useful Documentation on tools and technologies](#useful-documentation-on-tools-and-technologies)
    - [Thunder VSCode Extension](#thunder-vscode-extension)
    - [Todo Tree VSCode Extension](#todo-tree-vscode-extension)
    - [Documentation rest](#documentation-rest)
    - [Actuator](#actuator)
    - [HAL Browser](#hal-browser)
    - [LiveReload](#livereload)
    - [h2](#h2)
    - [Mapstruct](#mapstruct)
    - [General Documentation](#general-documentation)
  - [Specific bug resolved](#specific-bug-resolved)
    - [Gradle](#gradle)
    - [Spring \& JPA](#spring--jpa)
    - [Mapstruct](#mapstruct-1)
    - [Other](#other)
- [Technologies choice](#technologies-choice)
  - [Record for Dto](#record-for-dto)
  - [Mapstruct](#mapstruct-2)
    - [Recompile and clean the workspace](#recompile-and-clean-the-workspace)
  - [Swagger Vs SpringDoc Vs Hal Explorer](#swagger-vs-springdoc-vs-hal-explorer)
- [JPA relationships choice](#jpa-relationships-choice)
  - [ManyToOne vs OneToMany](#manytoone-vs-onetomany)
  - [Cascade.PERSIST for reservations in booking](#cascadepersist-for-reservations-in-booking)
- [Questions / Débat](#questions--débat)
  - [Vérification de code selon les règles métiers](#vérification-de-code-selon-les-règles-métiers)

---

# Developpement Notes

This document is a log of the development of the project. It contains useful documentation, bugs resolved, and questions/choices about the project. I use it to keep track of the project and to help me to remember what I did and why I did it. It is also a good way to share my knowledge and to help others. I hope it will be useful to you. :)

## Info on the project

### Project board

It's the good place to see the progress, and what remains to be done, learned, improved, etc.

https://github.com/users/vifeng/projects/2/views/1

### Code and issues

https://github.com/vifeng/TicketToTheMoon

I haven't really used consistently the issues features of github. I should use it, but since I'm alone and junior it feels like everything is an issue !! ;)

## Useful Documentation on tools and technologies

### Thunder VSCode Extension

Thunder is a REST API client extension for Visual Studio Code. It is a lightweight and easy-to-use extension for sending HTTP requests to test your REST API. it is a good alternative to Postman. Now I use it for all my tests. you'll find the collections of requests in the thunder folder : ./thunder. You can import them in your thunder extension. So you now, there is a free plan limiting the number of requests per day.

link to extension : https://marketplace.visualstudio.com/items?itemName=rongwong.thunder-client

### Todo Tree VSCode Extension

I use Todo Tree, a vscode extension that allows you to see all the TODOs in your code. It is very useful to keep track of what remains to be done. You will find the Tags that I use, and my configuration for the highlights in the ./todoTree. You can copy my configuration in the settings.json file of the .vscode folder of your project or via the command palette menu.

link to extension : https://marketplace.visualstudio.com/items?itemName=Gruntfuggly.todo-tree

### Documentation rest

https://www.baeldung.com/spring-rest-docs#:~:text=Spring%20REST%20Docs%20produces%20documentation,http%20request%20snippets%20are%20generated

### Actuator

https://www.javatpoint.com/spring-boot-actuator#:~:text=Spring%20Boot%20Actuator%20is%20a,place%20where%20the%20resources%20live).
https://spring.io/guides/gs/actuator-service/
https://www.baeldung.com/spring-boot-actuators

### HAL Browser

https://www.baeldung.com/spring-rest-hal
Basically, just add the following dependency to your build.gradle.kts file and it's done !

```kts
depedependencies {
  implementation("org.springframework.data:spring-data-rest-hal-explorer")
}
```

### LiveReload

extension for chrome that reloads the page when a change is made in the code. You need to install the extension and activate it in the browser. You may allow only specific website.
https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei

### h2

useful script for a mysql dump : `SCRIPT NODATA DROP TO 'dump.txt';`
source : http://www.h2database.com/html/commands.html#script

in the resources folder, the script is executed in the following order :

- `schema.sql` is executed before data.sql
- `data.sql` is executed before import.sql
- `import.sql` is executed before the h2 console is started

### Mapstruct

- [official documentation](https://mapstruct.org/documentation/stable/reference/html/#_gradle)
- [official examples](https://github.com/eugenp/tutorials/tree/master/mapstruct)
- [official examples with java record](https://github.com/mapstruct/mapstruct-examples/tree/main/mapstruct-record)
- [quick guide](https://www.baeldung.com/mapstruct)
- [Mapping Collections](https://www.baeldung.com/java-mapstruct-mapping-collections)

### General Documentation

- https://spring.io/guides
- [The project's initializr file](https://start.spring.io/#!type=gradle-project-kotlin&language=java&platformVersion=3.0.6&packaging=war&jvmVersion=17&groupId=com.vf&artifactId=backEnd&name=tickettothemoon_BackEndAPI&description=Spring%20Boot%20backend%20app%20for%20a%20ticket%20office%20website&packageName=com.vf.backEnd&dependencies=configuration-processor,data-jpa,validation,webflux,web,session,actuator,h2,devtools)
- [SpringBoot plugin documentation](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/)

## Specific bug resolved

### Gradle

- Spring Initializr with Gradle Kotlin and actuator : see [bug pb & solution](https://github.com/spring-io/initializr/issues/922)

- error message : `Could not resolve all files for configuration ':classpath'.
Could not resolve org.springframework.boot:spring-boot-gradle-plugin:3.0.6.`
  https://github.com/redhat-developer/vscode-java/issues/3094
  for vsCode try this :
  in your settings CTL+ SHIFT+P > preferences:opensettings(ui)
  type in the search bar : java.import.gradle.java.home
  fill in the location to the JVM used to run the Gradle daemon.
  such as for i.E. /Library/Java/JavaVirtualMachines/jdk-17.0.2.jdk/Contents/Home (path for macOS users)
  your path should be matching the java version defined in your gradle build of course

I also did a gradle clean and removed the followings folders of the project , I think it is more likely the reason it is now working :
rm -r bin .classpath .gradle .nb-gradle .project .settings .vscode .DS_Store .idea

this could be made in a task depending on gradle clean such as :
build.gradle.kts - newer kotlin version

tasks.register("distClean") {
dependsOn("clean")
doLast {
delete("bin")
delete (".classpath")
delete (".gradle")
delete (".nb-gradle")
delete (".project")
delete (".settings")
delete (".vscode")
delete (".DS_Store")
delete (".idea")
}
}
build.gradle groovy older version :

task distClean {
dependsOn("clean")
doLast {
delete "bin"
delete ".classpath"
delete ".gradle"
delete ".nb-gradle"
delete ".project"
delete ".settings"
delete ".vscode"
delete '.DS_Store'
delete '.idea'
}
}
then in vsCode CTL+ SHIFT+P > Java : clean java langage server workspace

### Spring & JPA

- error message : `The import javax.persistence cannot be resolved` :
  The javax.persistence package was moved to a newly named dependency (jakarta.persistence. The persistence package is part of the larger JPA (Java Persistence API). See Intro to JPA.
  https://stackoverflow.com/questions/15598210/the-import-javax-persistence-cannot-be-resolved/74996933#74996933

- error message : `via JDBC [Erreur de syntaxe dans l'instruction SQL "\000a    create table seat (\000a        id bigint generated by default as identity,\000a        number integer not null,\000a        reserved boolean not null,\000a        [*]row integer not null,\000a        seated boolean not null,\000a        area_fk bigint,\000a        category_fk bigint,\000a        primary key (id)\000a    )"; attendu "identifier"` :
  the problem was that I used the keyword row as a column name. I changed it to rowNo and it worked. Same for "hour".

- Entities have also reserved names. For example, I couldn't name my entity "User" because it is a reserved name. I changed it to "Customer" and it worked. As well as "Order" which I changed to "Ticket_reservation". I should look for a list of reserved names.

- Composite primary key not found : This is something that took me ages to resolve, so for the moment I just enjoy what is working but I should give it another try later. I had to change my first implementation. see commit for details or the github project board. I added some setters to the composite key because the generated implementation uses the empty constructor then uses the setters to set the values.

### Mapstruct

- Implementation order : In mapstruct the generated implementation uses the empty constructor then uses the setters to set the values. The orders of the setters is not guaranteed. So if you have a setter that depends on another setter, you can't be sure that the other setter has been called. One of the solution is to use custom mapping method using the builder pattern or validate the entity object in the service layer instead of the setters. For example see the configurationHall.java and its mapper (see `setCapacityOfConfiguration()`, Hall has to be already defined because we call one of his variable to check against a value of the current object).

- A request sends back info that is not needed : for exemple, I added an utilitary get Field to booking such as getSeatId {return id.getSeatId();} and same for the other key sesssionEventID. The request returned :

```json
[
  {
    "id": {
      "seatId": {},
      "sessionEventId": {}
    },
    "isBooked": true,
    "sessionEventId": {},
    "seatId": {}
  }
]
```

thus duplicating the info seatId and sessionEventId. The solution is to delete the utility methods or if it is needed just add the following to the Mapper. This is also the way to go to ignore a field such as a password for example:

```java
@Mapping(target = "id", ignore = true)
BookingDTO toDto(Booking booking);
```

which wiill result in :

```json
[
  {
    "id": {
      "seatId": {},
      "sessionEventId": {}
    },
    "isBooked": true
  }
]
```

### Other

- A generic error occurred : JSON parse error: Cannot construct instance of `com.vf.tickettothemoon_BackEnd.domain.dto.CustomerDTO` (although at least one Creator exists): no String-argument constructor/factory method to deserialize from String value ('{....}')" : My JSON payload request was not correct. I had to change the payload to match the DTO and check the data types. For example, I had a int instead of a String for the phoneNumber in Customer.

# Technologies choice

## Record for Dto

Java implemented Records that are used for DTOs mainly. Thus, there is no need for lombok wihch with I had some issues with.
Lombok could still be used for other purposes such as entities. I didn't because it doesn't work with code coverage.

## Mapstruct

Mapstruct is used to map entities to DTOs and vice versa. It is a code generator that generates the mapping code at compile time.
Firstly, I used ModelMapper but it is not compatible with the Java Record feature.
It does seems more easy than ModelMapper to use and configure.

### Recompile and clean the workspace

If you change an entity, a record or a mapper file, you need to rebuild the project to generate the code. It is not done automatically. In vsCode **CTL+ SHIFT+P > Java : clean java langage server workspace** to rebuild the project.

1. manual solution
   You can use the following gradle tasks (but I haven't implemented it yet) :

```shell
gradle mapstructGenerate
# generate the mapstruct implementation, you don't need to clean your workspace on vscode anymore.
gradle mapstructClean
# clean the mapstruct implementation
gradle mapstruct
# clean and generate the mapstruct implementation
gradle mapstructCompile
# compile the mapstruct implementation
```

2. vscode solution

   You can add a task to Vscode : you can add the mapstructGenerate task to your build configuration. To do this, open the .vscode/tasks.json file and add the following task. Once you have added the mapstructGenerate task to your build configuration, you can run it by pressing Ctrl+Shift+B.

```json
{
  "type": "shell",
  "label": "mapstructGenerate",
  "command": "./gradlew mapstructGenerate",
  "group": "build",
  "presentation": {
    "reveal": "always"
  }
}
```

1. gradle solution
   In this solution we add a task mapstructGenerate that will be executed before the bootRun task. So you don't need to clean your workspace on vscode anymore. But this code is not working yet. ;)

```kts
<!--  code not working -->
tasks.register<JavaExec>("mapstructGenerate") {
    classpath = sourceSets["main"].compileClasspath + configurations.annotationProcessor
    mainClass.set("org.mapstruct.tools.Application")
    args = ["-d", "build/generated/source/mapstruct/main"]
}

tasks.named("bootRun") {
    dependsOn("mapstructGenerate")
}
```

## Swagger Vs SpringDoc Vs Hal Explorer

I tried to install swagger but didn't succeeded. I heard about Spring REST Docs during a conference and it seems to be a good alternative to swagger. It is simple, documentation is well written. The advantage is :

- that it is not a runtime tool but a build time tool.
- documentation is generated from the tests.

but since you have to write the tests it's a bit more work. So in the meantime for a quick documentation I used hal explorer which give the endpoints of the API and other useful usage.

In this project you'll find both Spring REST Docs(I haven't written much test yet) and Hal Explorer.

# JPA relationships choice

## ManyToOne vs OneToMany

source : [https://stackoverflow.com/questions/16119531/hibernate-jpa-manytoone-vs-onetomany](https://stackoverflow.com/questions/16119531/hibernate-jpa-manytoone-vs-onetomany)

Situation :

The solution you choose mainly depends on the situation, and on the level of coupling between the entities. Suppose you have a User and a Message, where a user can have thousands of messages, it could make sense to model it only as a ManyToOne from Message to User, because you'll rarely ask for all the messages of a user anyway. The association could be made bidirectional only to help with queries though, since JPQL queries join between entities by navigating through their associations.

Efficiency :

- Also, having @ManytoOne side as the owner would require only n+1 queries while saving the associtions. Where n is the number of associations (many side).
- Whereas having @OneToMany as the owner, while inserting the parent entity (one side) with associations (many side) would result in 2\*N + 1 queries. In which one query would be for insert of the association and other query would be for updating foreign key in the associated entity.

## Cascade.PERSIST for reservations in booking

**Problème** : options de cascades de reservations dans booking. si je mets cascadeType.PERSIST, comme ci-dessous, ça persiste les ticket_reservation qui ont déjà été enregistré en base de données par ailleurs (juste avant dans DBInit) et donc peut causer une duplicate erreur.

```java
@OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
@JoinColumn(name = "Booking_FK")
private Set<Ticket_Reservation> reservations;
```

**Ca revient à se poser la question : Est ce que je devrais persister les ticketReservation que via booking ?**

Exemple de choix métier :

1.  Un utilisateur effectue une réservation en sélectionnant plusieurs tickets, puis confirme la réservation. La création du Booking et la persistance des Ticket_Reservation sont effectuées en une seule opération atomique.
2.  Une Ticket_Reservation est persistée lorsqu'un client sélectionne un siège, mais elle n'est associée à un Booking que si le client confirme sa réservation.
3.  Un client peut réserver un ticket pour un spectacle, puis plus tard ajouter un autre ticket à la même réservation sans créer un nouveau Booking.
4.  Si un client réserve plusieurs tickets pour un concert, toutes les Ticket_Reservation associées à cette réservation ne sont persistées qu'une fois que le Booking est confirmé.

**Solution choisie :** Pour l'instant on est sur l'option 3 la plus simple à mettre en oeuvre, j'enlève l'option PERSIST. On peut toujours changer plus tard...

# Questions / Débat

## Vérification de code selon les règles métiers

Exemple : un code postal doit être composé de 5 chiffres.
La vérification des infos peut se faire en front ou en back pour un update ou post. J'aurais tendance à le faire que côté front, histoire de ne pas doubler le code (problème de vérifications différentes ou doublon de code) et aussi pour ne pas faire de requête inutile à la base de données. En terme de sécurité je ne sais pas si c'est une bonne idée.
