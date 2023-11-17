docker-compose up -d

cd ..
cd eureka
cmd /C mvn clean install 
cd ..
cd api-gateway
cmd /C mvn clean install 
cd ..
cd park_bo_mcs
cmd /C mvn clean install -DskipTests
cd ..
cd payments_bo_mcs
cmd /C mvn clean install -DskipTests
cd ..
cd users_bo_mcs
cmd /C mvn clean install 
cd ..
cd Setup

cmd /C docker rm -f $(docker ps -aq)
cmd /C docker rmi $(docker images -q)
cmd /C docker volume rm $(docker volume ls -q)
