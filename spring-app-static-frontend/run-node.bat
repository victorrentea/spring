rem this is a more traditional approach: serving static content from a NodeJS instance.

call npm install --global http-server
http-server ../spring-app/src/main/resources/static -p 8081
