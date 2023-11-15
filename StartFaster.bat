cd DataBases
    docker-compose up -d
cd ..

timeout /t 10 /nobreak

docker-compose up -d