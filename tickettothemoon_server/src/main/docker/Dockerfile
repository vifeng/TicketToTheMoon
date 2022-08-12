FROM openjdk:alpine
MAINTAINER Virginie F. <mail>
# Spring Boot application creates working directories for Tomcat by default
VOLUME /tmp
ADD tickettothemoon.jar tickettothemoon.jar
RUN sh -c 'touch /tickettothemoon.jar'
# To reduce Tomcat startup time we added a system property pointing to "/dev/urandom" as a source of entropy.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tickettothemoon.jar"]