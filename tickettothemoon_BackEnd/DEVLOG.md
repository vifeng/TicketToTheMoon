# Developpement Notes

## Info on the project

Project board
https://github.com/users/vifeng/projects/2/views/1
Code and issues
https://github.com/vifeng/TicketToTheMoon

## Useful Documentation

### Documentation rest

https://www.baeldung.com/spring-rest-docs#:~:text=Spring%20REST%20Docs%20produces%20documentation,http%20request%20snippets%20are%20generated

### Actuator

https://www.javatpoint.com/spring-boot-actuator#:~:text=Spring%20Boot%20Actuator%20is%20a,place%20where%20the%20resources%20live).
https://spring.io/guides/gs/actuator-service/
https://www.baeldung.com/spring-boot-actuators

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
