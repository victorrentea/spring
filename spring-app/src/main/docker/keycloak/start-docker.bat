docker run --name kc  -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -p 8180:8180 -e JAVA_OPTS_APPEND="-Djboss.socket.binding.port-offset=100" jboss/keycloak
