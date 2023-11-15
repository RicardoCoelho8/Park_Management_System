FROM openjdk:17
COPY target/park_bo_mcs-0.0.1-SNAPSHOT.jar park_bo_mcs-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Dspring-boot.run.profiles=prod","-jar","/park_bo_mcs-0.0.1-SNAPSHOT.jar"]