## Spring Boot Keycloak

This module contains articles about Keycloak in Spring Boot projects.

## Relevant articles:
- [A Quick Guide to Using Keycloak with Spring Boot](https://www.baeldung.com/spring-boot-keycloak)
- [Custom User Attributes with Keycloak](https://www.baeldung.com/keycloak-custom-user-attributes)
- [Customizing the Login Page for Keycloak](https://www.baeldung.com/keycloak-custom-login-page)
- [Keycloak User Self-Registration](https://www.baeldung.com/keycloak-user-registration)
- [Customizing Themes for Keycloak](https://www.baeldung.com/spring-keycloak-custom-themes)



Run keycloak: 
1. Make sure in standalone.bat JAVA_OPTS include -Djboss.socket.binding.port-offset=100
2. Launch C:\workspace\keycloak-12.0.4\bin\standalone.bat
3. Open http://localhost:8180 : login with initial1 / zaq1!QAZ
4. Start this app and open the "internal app portal": http://localhost:8081



To see access token, go to Clients > CLIENT > Clients Scopes > evaluate