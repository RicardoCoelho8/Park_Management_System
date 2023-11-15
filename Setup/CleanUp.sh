docker-compose up -d

cd ..
    cd park_bo_mcs
        mvn clean install
    cd ..
    cd payments_bo_mcs
        mvn clean install
    cd ..
    cd users_bo_mcs
        mvn clean install
    cd ..
cd Setup

docker rm -f $(docker ps -aq)
docker rmi $(docker images -q)
docker volume rm $(docker volume ls -q)

read -p "Press enter to continue"