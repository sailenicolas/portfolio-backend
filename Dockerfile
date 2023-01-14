FROM amazoncorretto:17-alpine-jdk
MAINTAINER sailenicolas
COPY target/demojwt-0.0.1-SNAPSHOT.jar demojwt-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/demojwt-0.0.1-SNAPSHOT.jar"] 