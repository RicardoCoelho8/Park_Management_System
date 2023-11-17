docker-compose up -d

cd ..
    cd eureka
        mvn clean install 
    cd ..
    cd api-gateway
        mvn clean install 
    cd ..
    cd park_bo_mcs
        mvn clean install -DskipTests
    cd ..
    cd payments_bo_mcs
        mvn clean install -DskipTests
    cd ..
    cd users_bo_mcs
        mvn clean install 
    cd ..
cd Setup

docker rm -f $(docker ps -aq)
docker rmi $(docker images -q)
docker volume rm $(docker volume ls -q)

read -p "Press enter to continue"