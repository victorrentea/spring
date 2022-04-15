

docker container ls
docker exec keycloak_keycloak_1 /opt/jboss/keycloak/bin/add-user-keycloak.sh -u admin -p admin
docker restart keycloak_keycloak_1