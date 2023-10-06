TABLE OF CONTENTS

- [Developpement Notes](#developpement-notes)
  - [Info on the project](#info-on-the-project)
    - [Project board](#project-board)
    - [Code and issues](#code-and-issues)
  - [Useful Documentation](#useful-documentation)
    - [Documentation rest](#documentation-rest)
    - [Actuator](#actuator)
    - [HAL Browser](#hal-browser)
    - [LiveReload](#livereload)
    - [h2](#h2)
    - [General Documentation](#general-documentation)
  - [Specific bug resolved](#specific-bug-resolved)
- [Technologies choice](#technologies-choice)
  - [Record for Dto](#record-for-dto)
  - [Mapstruct](#mapstruct)
    - [Recompile and clean the workspace](#recompile-and-clean-the-workspace)
  - [Swagger Vs SpringDoc Vs Hal Explorer](#swagger-vs-springdoc-vs-hal-explorer)
- [JPA relationships choice](#jpa-relationships-choice)
  - [ManyToOne vs OneToMany](#manytoone-vs-onetomany)
- [Questions / Débat](#questions--débat)
  - [Vérification de code selon les règles métiers](#vérification-de-code-selon-les-règles-métiers)

---

# Developpement Notes

## Info on the project

### Project board

https://github.com/users/vifeng/projects/2/views/1

### Code and issues

https://github.com/vifeng/TicketToTheMoon

## Useful Documentation

### Documentation rest

https://www.baeldung.com/spring-rest-docs#:~:text=Spring%20REST%20Docs%20produces%20documentation,http%20request%20snippets%20are%20generated

### Actuator

https://www.javatpoint.com/spring-boot-actuator#:~:text=Spring%20Boot%20Actuator%20is%20a,place%20where%20the%20resources%20live).
https://spring.io/guides/gs/actuator-service/
https://www.baeldung.com/spring-boot-actuators

### HAL Browser

https://www.baeldung.com/spring-rest-hal

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

### General Documentation

- https://spring.io/guides
- [The project's initializr file](https://start.spring.io/#!type=gradle-project-kotlin&language=java&platformVersion=3.0.6&packaging=war&jvmVersion=17&groupId=com.vf&artifactId=backEnd&name=tickettothemoon_BackEndAPI&description=Spring%20Boot%20backend%20app%20for%20a%20ticket%20office%20website&packageName=com.vf.backEnd&dependencies=configuration-processor,data-jpa,validation,webflux,web,session,actuator,h2,devtools)
- [SpringBoot plugin documentation](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/)

## Specific bug resolved

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

- error message : `The import javax.persistence cannot be resolved` :
  The javax.persistence package was moved to a newly named dependency (jakarta.persistence. The persistence package is part of the larger JPA (Java Persistence API). See Intro to JPA.
  https://stackoverflow.com/questions/15598210/the-import-javax-persistence-cannot-be-resolved/74996933#74996933

- error message : `via JDBC [Erreur de syntaxe dans l'instruction SQL "\000a    create table seat (\000a        id bigint generated by default as identity,\000a        number integer not null,\000a        reserved boolean not null,\000a        [*]row integer not null,\000a        seated boolean not null,\000a        area_fk bigint,\000a        category_fk bigint,\000a        primary key (id)\000a    )"; attendu "identifier"` :
  the problem was that I used the keyword row as a column name. I changed it to rowNo and it worked. Same for "hour".

- Entities have also reserved names. For example, I couldn't name my entity "User" because it is a reserved name. I changed it to "Customer" and it worked. As well as "Order" which I changed to "Ticket_reservation". I should look for a list of reserved names.

# Technologies choice

## Record for Dto

Java implemented Records that are used for DTOs mainly. Thus, there is no need for lombok wihch with I had some issues with.
Lombok could still be used for other purposes such as entities. I didn't because it doesn't work with code coverage.

## Mapstruct

Mapstruct is used to map entities to DTOs and vice versa. It is a code generator that generates the mapping code at compile time.
Firstly, I used ModelMapper but it is not compatible with the Java Record feature.
It does seems more easy than ModelMapper to use and configure.

### Recompile and clean the workspace

1. manual solution
   If you change an entity, a record or a mapper file, you need to rebuild the project to generate the code. It is not done automatically. in vsCode CTL+ SHIFT+P > Java : clean java langage server workspace to rebuild the project. Or you can use the following gradle tasks :

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
   If you are using VSCode, you can add the mapstructGenerate task to your build configuration. To do this, open the .vscode/tasks.json file and add the following task. Once you have added the mapstructGenerate task to your build configuration, you can run it by pressing Ctrl+Shift+B.

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

3. gradle solution
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

In this project you'll find both Spring REST Docs and Hal Explorer.

# JPA relationships choice

## ManyToOne vs OneToMany

source : [https://stackoverflow.com/questions/16119531/hibernate-jpa-manytoone-vs-onetomany](https://stackoverflow.com/questions/16119531/hibernate-jpa-manytoone-vs-onetomany)

Situation :

The solution you choose mainly depends on the situation, and on the level of coupling between the entities. Suppose you have a User and a Message, where a user can have thousands of messages, it could make sense to model it only as a ManyToOne from Message to User, because you'll rarely ask for all the messages of a user anyway. The association could be made bidirectional only to help with queries though, since JPQL queries join between entities by navigating through their associations.

Efficiency :

- Also, having @ManytoOne side as the owner would require only n+1 queries while saving the associtions. Where n is the number of associations (many side).
- Whereas having @OneToMany as the owner, while inserting the parent entity (one side) with associations (many side) would result in 2\*N + 1 queries. In which one query would be for insert of the association and other query would be for updating foreign key in the associated entity.

# Questions / Débat

## Vérification de code selon les règles métiers

Exemple : un code postal doit être composé de 5 chiffres.
La vérification des infos peut se faire en front ou en back pour un update ou post. J'aurais tendance à le faire que côté front, histoire de ne pas doubler le code (problème de vérifications différentes ou doublon de code) et aussi pour ne pas faire de requête inutile à la base de données. En terme de sécurité je ne sais pas si c'est une bonne idée.
