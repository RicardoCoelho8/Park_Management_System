cd DataBases
    docker-compose up -d
cd ..

timeout /t 20 /nobreak

docker-compose up -d