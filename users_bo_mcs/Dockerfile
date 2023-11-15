FROM openjdk:17
COPY target/user_bo_mcs-0.0.1-SNAPSHOT.jar user_bo_mcs-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Dspring-boot.run.profiles=prod","-jar","/user_bo_mcs-0.0.1-SNAPSHOT.jar"]