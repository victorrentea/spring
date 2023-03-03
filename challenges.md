## Spring One Project ##

#### Main Support project for Spring Training of [Victor Rentea](https://victorrentea.ro)




## Assignments

### Bean definition and Dependency Injection
- Define a bean using
  - @Component + Component Scanning
  - Using your custom annotation annotated with @Component !Note: don't forget about @Retention
  - @Import without Component Scanning
  - @Bean method
- Inject a bean in a field, constructor and method
- Define as beans two classes X1 and X2 implementing the same interface X 
  - Inject in a field an object of type X (interface) > Enjoy the error, then fix it as follows:
  - If the profile 'local' is active X2 should be injected; otherwise X1. Test.
  - Inject in a field an @Autowired List<X>: what happens?
- Define two beans of the same type Y but with different name
  - Inject an Y somewhere. Did it crash?
  - Decide which of the two beans to inject using @Qualifier, and then using Injection Point Name 

### Properties
- Inject one integer (eg. max.age=10) defined in the application.property/.yaml file
- Define 3 properties (eg. prop.a, prop.b and prop.c) and inject all 3 properties in fields of a class
  - Validate that all three properties are set
  - Configure a default for one of them
- Create an application-local.properties/.yaml in which you change one of the properties defined earlier.
- Run mvn package and build the .jar
- Start the java -jar app.jar and activate the 'local' profile via:
  - VM options (-D...) !Note the order
  - Environment variable ($ export ...) !Note: in SCREAMING_SNAKE_CASE 
  - devtools file in ~

# ConditionalOn...
- Print "Hello Condition" if the property "hello.message" is set to 'true' in the properties
  - Using CommandLineRunner
  - Using @EventListener
  - Using @PostConstruct
- Define a bean of type String in the context if there is no other existing bean of type String in the context;
  - then manually define a @Bean String and check that the default one backs off

### Dev Comfort
- Add devtools to your pom and prove that 
  - on build if any class changed, the app is auto-restarted
  - any change if a static file is immediately loaded: eg from /src/resource/static/index.html
- Add spring-boot-configuration-processor and see if the properties in your app are now auto-completed in properties
- Experiment with changing the log level at runtime without restart - https://www.baeldung.com/spring-boot-changing-log-level-at-runtime

### Proxies
- Create an aspect that intercepts all methods annotated with @DeleteMapping from a @RestController 
  and log the argument of the methods along with the current logged in user
- Using the aspect above demonstrate that proxies DON'T INTERCEPT:
  - local method calls
  - static and final methods
  - methods in final class (crash!)
  - methods of manually created objects (new)
- Annotate with @Cacheable a method and check if calling it twice with the same params
  it returns the same result without actually running (Note: you'll need @EnableCaching on a @Configuration)

- Create an @Aspect that intercepts all the public methods called on any @Service-annotated bean. 
  - In that aspect, print the name of the method + return value (tostring if not null) + the execution duration time (in milliseconds).
  - Should work even for exceptions. 
  - Serialize as JSON any non-primitive values (arguments or return values).
  - Create an annotation @NotLogged. Any method annotated with it must NOT be logged by your aspect 
    - Hint: java.lang.reflect.Method#isAnnotationPresent 

### Transactions
- Workout victor.training.spring.transaction.exercises.propagation.PropagationExercises
- Workout victor.training.spring.transaction.exercises.exceptions.ExceptionsExercises

### Async
- Start a long-running task from a REST API (the api call should not wait for it to complete)
- Restrict the maximum number of such tasks that can run at some point in parallel
  - Hint: you'll need to define a ThreadPoolTaskExecutor @Bean
- Start a function async with CompletableFuture.supplyAsync but using an executor managed by Spring.
- Fire an event from a Spring bean and register 2 other beans to listen to it.
  - Annotate one @EventListener method with @Async -> what happens?
  - Annotate the other method with @TransactionalEventListener(AFTER_COMMIT) -> what happens