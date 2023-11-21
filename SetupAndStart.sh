cd Setup
  sh Install.sh
cd ..
cd DataBases
    docker-compose up -d
cd ..

sleep 20

docker-compose up 
