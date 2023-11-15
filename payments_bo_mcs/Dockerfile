FROM openjdk:17
COPY target/payments_bo_mcs-0.0.1-SNAPSHOT.jar payments_bo_mcs-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Dspring-boot.run.profiles=prod","-jar","/payments_bo_mcs-0.0.1-SNAPSHOT.jar"]