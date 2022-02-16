## Spring One Project ##

#### Main Support project for Spring Training of [Victor Rentea](https://victorrentea.ro)   

Among the things demonstrated in this project:
- Bean Lifecycle
- @Configuration/@Bean
- Spring Security, including XSS, CSRF, CORS, and SSO with KeyCloak
- Hibernate Repository (Spring Data)
- @Transactional
- Monitoring with Micrometer/Prometheus/Graphana
- Caching
- @Async, @Scheduled
- @EventListener
- @ConfigurationProperties- 
- Aspects
- REST Endpoints
- GraphQL


**REMEMBER** To run any Spring Boot Application in this project, you need to first start StartDatabase.java. 
Then, you can connect to this database using the url
jdbc:h2:tcp://localhost:9092/~/test
driver: H2
user: sa/sa

The storage of this database is located in the user in the file "test.mv.db"