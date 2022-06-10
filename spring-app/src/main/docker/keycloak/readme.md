## Spring Boot OAuth/OpenID Connect with Keycloak

#### How to setup
- Make sure you have installed Docker Desktop
- run start-docker.bat  (takes minutes)
- Access KeyCloak admin console at localhost:8180
  - login with god/god
- add realm, importing LearningRealm.json
- add 2 users : user and admin
  - Set them passwords
  - Assign "USER" and "ADMIN" roles to then, respectively
- Access localhost:8080 and expect to be redirected to :8180


### Troubleshooting
- If you are stuck in an infinite redirect loop after arriving back from KC, check not to have Basic authenticaation shill sent bty Chrome. Try chrome://restart










## Relevant articles:
- [A Quick Guide to Using Keycloak with Spring Boot](https://www.baeldung.com/spring-boot-keycloak)
- [Custom User Attributes with Keycloak](https://www.baeldung.com/keycloak-custom-user-attributes)
- [Customizing the Login Page for Keycloak](https://www.baeldung.com/keycloak-custom-login-page)
- [Keycloak User Self-Registration](https://www.baeldung.com/keycloak-user-registration)
- [Customizing Themes for Keycloak](https://www.baeldung.com/spring-keycloak-custom-themes)

