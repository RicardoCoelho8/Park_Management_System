cd ..
cd eureka
cmd /C mvn clean install -DskipTests
cd ..
cd api-gateway
cmd /C mvn clean install -DskipTests
cd ..
cd park_bo_mcs
cmd /C mvn clean install -DskipTests
cd ..
cd payments_bo_mcs
cmd /C mvn clean install -DskipTests
cd ..
cd users_bo_mcs
cmd /C mvn clean install -DskipTests
