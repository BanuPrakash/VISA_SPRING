# SPRING and JPA
https://github.com/BanuPrakash/VISA_SPRING

Spring Framework and JPA Framework

Spring Framework:

```
Spring is a lightweight inversion of control (IoC) Java framework for building Enterprise Application. 

At the heart of Spring is support for dependency injection (DI) and aspect-oriented programming (AOP). 

It was originally developed as an alternative to the heavyweight J2EE containers. Spring has become very popular because of its practical and consistent approach to Java development. Almost anything that we want to develop in Java is much easier to do with Spring.

Spring provides elegant solutions to common problems in many domains. We can think of Spring as a framework of frameworks.

Spring continues to evolve and grow with every new technology. Developers use Spring extensively for developing Java-based enterprise systems. Over the years, it’s become mature enough to become a de-facto standard for large scale enterprise Java development.

Many projects use Spring Framework as their backbone. The framework is divided into modules which makes it easy to pick and choose the parts we need in an application:

Core: Provides core features like DI (dependency injection), AOP (aspect oriented programming), internationalisation (i18n), validation, event publishing, cacheing and JMX management.

Data Access: Supports data access with JDBC (Java Database Connectivity), JTA (Java Transaction API) and JPA (Java Persistence API).

Web: Supports both the Servlet API (with Spring MVC) and the Reactive API (with Spring WebFlux). Also supports other web technologies like REST web services, WebSockets and STOMP.

Integration: Supports integration to JEE with JMS (Java Message Service), RMI (Remote Method Invocation), and channels and gateways to communicate with external systems.

Testing: Wide support for unit and integration testing through mock objects and test fixtures.

```

Bean: Any object managed by Spring Container is termed as "bean"

```
public interface EmployeeDao {
    void addEmployee(Employee e);
}

public class EmployeeDaoJdbcImpl implements EmployeeDao {
    public  void addEmployee(Employee e) {..}
}


public class EmployeeDaoMongoImpl implements EmployeeDao {
    public  void addEmployee(Employee e) {..}
}

public class AppService {
    private EmployeeDao empDao;
    public void setEmployeeDao(EmployeeDao empDao) {
        this.empDao = empDao;
    }

    public void insert(Employee e) {
        empDao.addEmployee(e);
    }
}

XML:

<bean id="employeeDao" class="com.visa.prj.dao.EmployeeDaoJdbcImpl" />

<bean id="employeeMongoDao" class="com.visa.prj.dao.EmployeeDaoMongoImpl" />

<bean id="service" class="com.visa.prj.service.AppService" >
    <property name="employeeDao" ref="employeeMongoDao">
</bean>

ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

The <context:annotation-config> annotation is mainly used to activate the dependency injection annotations.

```

Spring internally uses ByteBuddy / Java Assist / CGLib for byte code instrumentation

Annotations:
Spring instantiates classes if it contains one of these annoations:
1) @Component
2) @Repository
3) @Service
4) @Configuration
5) @Controller [serves rendered pages ==> HTML / PDF]
6) @RestController [ serve representation of Data like XML / JSON / CSV / RSS ...]
7) @ControllerAdvice

ApplicationContext ctx = new AnnotationConfigApplicationContext();


```
public interface EmployeeDao {
    void addEmployee(Employee e);
}

employeeDaoJdbcImpl

@Repository
public class EmployeeDaoJdbcImpl implements EmployeeDao {
    public  void addEmployee(Employee e) {..}
}

appService

@Service
public class AppService {
    @Autowired
    private EmployeeDao empDao;
  
    public void insert(Employee e) {
        empDao.addEmployee(e);
    }
}

```

Spring Boot is a framework on top of Spring Framework
* Highly opinionated framework ==> 
1) Configures Tomcat embedded server out of box if we choose to build web application
2) creates a pool of database connections if we choose to connect to RDBMS
* lots of configuration comes out of the box

SpringApplication.run() to start Spring Container instead of 

ApplicationContext ctx = new AnnotationConfigApplicationContext();

@SpringBootApplication is 3 in one:
1) @ComponentScan
scans of above mentioned 7 annotations and creates instances of those classes

2) @EnableAutoConfiguration
    creates built in services like --> EmbeddedTomcat Container

3) @Configuration

```

Description:

Field employeeDao in com.visa.prj.service.AppService required a single bean, but 2 were found:
	- employeeDaoJdbcImpl
	- employeeDaoMongoImpl

```
Solution 1:

```
@Repository
@Primary
public class EmployeeDaoMongoImpl implements  EmployeeDao{

@Repository
public class EmployeeDaoJdbcImpl implements  EmployeeDao{

```

Solution 2:

```
@Repository
public class EmployeeDaoMongoImpl implements  EmployeeDao{

@Repository
public class EmployeeDaoJdbcImpl implements  EmployeeDao{


@Service
public class AppService {
    @Autowired
    @Qualifier("employeeDaoJdbcImpl")
    private EmployeeDao employeeDao;
```

Solution 2: using Profiles

```
@Repository
@Profile("prod")
public class EmployeeDaoMongoImpl implements  EmployeeDao{

@Repository
@Profile("dev")
public class EmployeeDaoJdbcImpl implements  EmployeeDao{

@Service
public class AppService {
    @Autowired
    private EmployeeDao employeeDao;

More Run/Debug

Active Profiles: dev

```

Different ways of wiring:
```

1)
@Service
public class AppService {
    @Autowired
    private EmployeeDao employeeDao;

2) using Constructor:
@Service
@RequiredArgsConstructor
public class AppService {
    private final EmployeeDao employeeDao; // constructor dependency injection

3) using implicit constructor:

@Service
public class AppService {
    private final EmployeeDao employeeDao; // constructor dependency injection
    // @Autowired optional
    public AppService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

4) using Setter dependency Injection


@Service
public class AppService {
    private  EmployeeDao employeeDao; // constructor dependency injection
    
    @Autowired
    public void setDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
```

JPA --> Java Persistence API

ORM --> Object Relational Mapping

Java Class <----> RDBMS table
instance variables <----> columns of database

```
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="products")
public class Product {
    @Id
    private int id;
    private String name;
    private double price;
    @Column(name="qty")
    private int quantity;
}


ORM generates SQL for CRUD operations

  EntityManager em;
  @Override
   public void addProduct(Product product)  {
        em.save(product);
  }

```

ORM Frameworks:
1) Hibernate
2) KODO
3) TopLink
4) EclipseLink
5) OpenJPA
6) JDO

JPA Specification for ORM frameworks

PersistenceContext, EntityManager, DataSource --> Pool of database connection, EntityManagerFactory

======

@Bean ==> Factory method


* need to instantiate 3rd party class and after that manged by Spring Framework

@Configuration
public class AppConfig {
    @Bean("postgres")
    public DataSource getDataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
        cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
        cpds.setUser("swaldman");                                  
        cpds.setPassword("test-password");                                  
	
        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);                                     
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
            
        return cpds;
    }

    @Bean
    public LocalContainerEntityManagerFactory emf(DataSource ds) {
        LocalContainerEntityManagerFactory emf = new LocalContainerEntityManagerFactory();
        emf.setDataSource(ds);
        emf.setJpaVendor(new HibernateJpaVendor());
        emf.setPackagestoScan("com.visa.prj.entity");
        // properties
        return emf;
    }
}

@Repository 
public class EmployeeDaoJpaImpl implements EmployeeDao {
    @PersistenceContext
    EntityManager em;

      public void addEmployee(Employee e) {
        em.persist(e);
      }
}

@Repository
public class EmployeeDaoJdbcImpl implements EmployeeDao {
    @Autowired
    DataSource ds;

    public void addEmployee(Employee e) {
        Connection con = ds.getConnection();
        //...
    }
}

--

Scope of Bean:
```
@Repository
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EmployeeDaoJdbcImpl implements EmployeeDao {

}
@Service
public class AppService {
     @Autowired
    EmployeeDao empDao;
}

@Service
public class EmployeeService {
    @Autowired
    EmployeeDao empDao;
}
```

Spring Data Jpa: simplifies way you use JPA
* configures DataSource , EntityManagerFactory, EntityManager out of the box

https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html

SQL and JP-QL [ Java Persistence Query Language]
```

books

book_id book_title  price

@Entity
@Table(name="books")
public class Book {
    @Id
    @Column(name="book_id")
    id
    @Column(name="book_title")
    title;
    @Column(name="price")
    amount;
}

select * from books
select book_title, price from books;

from Book
select title, amount from Book
update Book set amount = 999 where id = 2

from Object


User
    email
    password

List<User> findByEmailAndPassword(String email, String password);

```

Transactions:
In a database management system, a transaction is a single unit of logic or work, sometimes made up of multiple operations. 
Any logical calculation done in a consistent mode in a database is known as a transaction. 
One example is a transfer from one bank account to another: the complete transaction requires subtracting the amount to be transferred from one account and adding that same amount to the other.

A database transaction, by definition, must be atomic (it must either be complete in its entirety or have no effect whatsoever), consistent (it must conform to existing constraints in the database), isolated (it must not affect other transactions) and durable (it must get written to persistent storage).Database practitioners often refer to these properties of database transactions using the acronym ACID.


Declarative Transaction or Programmatic Transaction


Programmatic Transaction:
```
public void transferFunds(Account fromAcc, Account toAcc, double amt) {
    Connection con = ...
    try {
        con.setAutoCommit(false);
        PreparedStatement ps1 = con.prepareStatement("update accounts ... where id =" + fromAcc.getAcc());
        PreparedStatement ps2 = con.prepareStatement("update accounts ... where id =" + toAcc.getAcc());
        PreparedStatement ps3 = con.prepareStatement("insert into transaction_table ...");
        sendSms(); // service
        ///

        con.commit();        
    } catch(Exception ex) {
        con.rollback();
    }
}

Declarative Tx:
@Transactional
public void transferFunds(Account fromAcc, Account toAcc, double amt) {
    ..
}
```

Association Mapping
* oneToMany
* ManyToOne
* OneToOne
* ManyToMany

=======

Task:

```
Vehicle Rental application

New database 
spring.datasource.url=jdbc:mysql://localhost:3306/RENTAL_DB?createDatabaseIfNotExist=true

Customers --> same as orderapp

vehicles 
REG_NO FUEL_TYPE COST_PER_DAY VEHICLE_TYPE

rentals
RENTAL_ID   CUSTOMER_FK     VECHICLE_FK     RENT_FROM   RENT_TO     AMOUNT


1) insert few customers
2) insert few vehicles
3) rent a vehicle
rentals
RENTAL_ID   CUSTOMER_FK           VEHICLE_FK     RENT_FROM       RENT_TO     AMOUNT
123         gavin@visa.com          KA50N1234      18-9-2023      NULL          0

4) return the vehicle
rentals
RENTAL_ID   CUSTOMER_FK           VEHICLE_FK     RENT_FROM       RENT_TO     AMOUNT
123         gavin@visa.com          KA50N1234      18-9-2023      21-9-2023       6000


```

Task 2:

Assign employee to Project

```
projects [Project]
pid name start_date end_end client

employees [ Employee]
email first_name last_name start_date  end_date

project_employees [ProjectEmployee ]

id pid email            start_date         end_date    role
1    1   smitha@visa.com     2012-09-22  2014-10-30      JR.DEVELOPER

1) Create tables
2) in backend insert records for projects and employees
3) Assign Employee to a project

id pid email            start_date         end_date    role
1  1   smitha@visa.com     2012-09-22      NULL      JR.DEVELOPER

4) employee released from project

```

	long days = ChronoUnit.DAYS.between(LocalDate.of(2023,9,10),LocalDate.of(2023,9,14));
	

=============

Day 4

@OneToMany
* @JoinColumn ==> introduces FK on Child side
* cascade
* fetch => DEFAULT is LAZY , we can set it to EAGER

@ManyToOne
* @JoinColumn ==> introduces FK on owning side
* default fetch is EAGER

---

Customer c = customerDao.findById("sam@visa.com").get();

List<Order> orders = c.getOrders();

----

Employee <---> Laptop
```

laptops
serial_no   name    details ram   size  employee_fk
Ylwvr091    Intel                       leena@visa.com
2lw4qb15    Mac                         julia@visa.com
Plzd2324    Mac                         sam@visa.com 
employees

employees
email             name  
sam@visa.com        
julia@visa.com
leena@visa.com

public class Laptop {

    @OneToOne
    @JoinColumn("employee_fk")
    Employee emp;
}

public class Employee {
    @OneToOne(mappedBy="emp")
    Laptop laptop;
}
```

ManyToMany

movies
mid name
34  A
83  B
72  C

actors
aid name
55  G0
62  G1
72  G3

movies_actors
mid     aid
34      G1
34      G3
83      G0
83      G1

Query:
https://www.baeldung.com/spring-data-jpa-projections

```
SELECT 
 c.first_name, c.email, o.order_date, o.total, p.name, p.price, i.qty, i.amount
FROM 
  customers c,
  orders o,
  line_items i,
  products p
WHERE
  c.email = 'harry@visa.com' 
  AND o.customer_fk = c.email
  AND o.oid = i.order_fk
  AND i.product_fk = p.id
```

https://datamodels.databases.biz/

Joins



new state, managed state/ persistent state, detached and destroyed state.

-----------------

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>

Spring Web Module:
1) provides Embedded TomcatServer / Container
2) DispatcherServlet --> FrontController [*]
3) Jackson library --> used for Java <---> JSON

RESTful Web Services

* Resources --> is what is present on server
* Representation --> state of the resource in various format [ XML / JSON / CSV / ATOM ...]

HTTP Protocol
* URL to identify resource [plural nouns --> products, orders , customers]
http://localhost:8080/api/products

* HTTP methods for actions
CREATE --> POST
READ --> GET
UPDATE --> PUT / PATCH
DELETE --> DELETE

1) GET
http://localhost:8080/api/products
get all products

2) GET
http://localhost:8080/api/products/4

use PathParameter [4] is by ID --> a single product

GET
http://localhost:8080/api/customers/harry@visa.com/orders

3) GET
http://localhost:8080/api/products?category=mobile

QueryParameter --> subset 

http://localhost:8080/api/products?page=1&size=20

4) POST
http://localhost:8080/api/products

payload contains new product data to be added to "products"


5) PUT
http://localhost:8080/api/products/3

payload contains new product data to be updated to product with id "3" in "products"


6) DELETE --> Never used
http://localhost:8080/api/products/4

delete a product with id "4"

---------------------

@RestController
@RequestMapping("api/products")
public class ProductController {

    @GetMapping()
    public List<Product> getProducts() {
        return orderService.getProducts();
    }


}


Http Header:
Accept: text/xml

Accept: application/json

HttpMessageConverter
ContentNegotiationHandler

Jackson Library

Jettison / Moxy / GSON

[
    {id:1, name : 'A'},
    {id: 2, name: 'B'}
]

Content-type: application/json

==============

RESTful WS

Accept: application/json
Content-type:application/json


Characteristics of RESTful:
1) client-server 
2) Stateless
3) Uniform URL

@RestController [CSR] instead of @Controller [SSR]
@RequestMapping
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping

@ResponseBody [ optional] Java --> JSON / XML

@RequestBody Representation =--> Java 

@PathVariable [ PathParameter /]
@RequestParam [ Query Parameter ?]

============

AOP -> Aspect Oriented Programming

Cross-Cutting Concerns


public void transferFunds(..) {
    isValidCustomer
    begin Tx
    log tx started
        debit from
        log debit done
        credit
        log credit done
    commit tx
    log transaction success
}

Aspect: Concern which leads code tangling and code scattering
JoinPoint: place in your code where Aspect can be weaved [ any method / any exception]
PointCut: selected JoinPoint
Advice: Before, After, Around, AfterThrowing, AfterReturing

https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html

Annotation:

@AnnotationName

1) Who uses it?
    a) COMPILER
    b) CLASSLOADER
    c) RUNTIME
2) Where can i Use  it?
    a) TYPE
    b) METHOD
    c) FIELD
    d) PARAMETER

Annotations can't have state and methods. Only properties can be there for annotation
"value" is a property
public @interface Query {
    String value() default "";
}

@Query(value=",,,") --> setter

query.value() --> getter

============

Validations

https://docs.oracle.com/javaee/7/api/javax/validation/constraints/package-summary.html

Resolved [org.springframework.web.bind.MethodArgumentNotValidException: Validation failed for argument [0] in public com.visa.shopapp.entity.Product com.visa.shopapp.api.ProductController.addProduct(com.visa.shopapp.entity.Product) with 2 errors:
 [Field error in object 'product' on field 'name': rejected value []; codes [NotBlank.product.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.name,name]; arguments []; default message [name]]; default message [Name is required]]
 
  [Field error in object 'product' on field 'price': rejected value [-8500.0]; codes [Min.product.price,Min.price,Min.double,Min]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.price,price]; arguments []; default message [price],10]; default message [Price -8500.0 should be greater than equal to 10]] ]

  MCQ Test for those who missed it:
  https://www.classmarker.com/online-test/start/?quiz=dmj6507cf61d5e84

================
@ControllerAdvice, @ExceptionHandler
@Validate @Valid, @NotBlank, @Min, @Max, @Pattern, @Future, @Past

Annotation
@Target, @RetentionPolicy [ Compiler, ClassLoader, Runtime]
property
@ValidationInput


=======

Day 6
Security: Authentication and Authorization

http://localhost:8080/api/hello

 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

By including above dependency, 
1) all resources are protected.
2) we get login page and logout page
3) it creates one user with username:user and password:generatedPassword


Using generated security password: c7457eb7-b8a3-4938-91b4-41f77a000b4a

http://localhost:8080/logout

=========

Custom Configuration --> Security

DAO and InMemory

https://bcrypt-generator.com/

https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

```
@Bean
public class MyUserDetailsManager implements UserDetailsManager {
     protected List<UserDetails> loadUsersByUsername(String username) {
        // SQL
    }
}
```
========

Stateless.

Token based Authorization --> JWT [Json Web Token]
JSON Web Tokens are an open, industry standard RFC 7519 method for representing claims securely between two parties.

jwt token:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

Header:
{
  "alg": "HS256",
  "typ": "JWT"
}

Payload:
{
    "sub": "banu@lucidatechnologies.com",
    "iss": "https://security.visa.com",
    "iat": 4535353453,
    "exp": 7343452133,
    "authorities": "admin,reader,writer"
}

SIGNATURE:
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  "topsecretsaltvalue234112"
) 
```

Symmetric key: same key is used for both encryption and decryption
Asymmetirc key: private key to generate token and public key to validate the token

=====

Complete Rental Application with RESTController

POSTMAN


========

Day 7

Security
Spring Security
DelegatingProxyFilter --> Filters UsernamePasswordAuthenticationFilter attemptAthentication(), successfulAuthentication()

AuthenticationManager --> JdbcManager, InMemory, Ldap

UserDetailsService
List<UserDetails> findUserByName(String username) 

SecurityContext

InMemoryUsers, JdbcManager, BcryptPasswordEncoder

SecurityFilterChain
    permitAll(), authenticated(), antMatchers(), requestMatchers("api/**").hasAnyRole("GUEST", "ADMIN)

JWT

@Secured("ADMIN")
public void doTask() {

}


@ConditionalOnMissingBean(AuthenticationManager.class)
public class DefaultManager {

}

USER:
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYXZpbkBnbWFpbC5jb20iLCJpYXQiOjE2OTU3OTQ2OTMsImV4cCI6MTY5NTc5NjEzMywicm9sZXMiOlsiUk9MRV9VU0VSIl19.uQ14INdtJM2_jXyIj-01hLTzjuoLca5qWrYXjaXv3C4

ADMIN:
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2RAZ21haWwuY29tIiwiaWF0IjoxNjk1Nzk0NzM4LCJleHAiOjE2OTU3OTYxNzgsInJvbGVzIjpbIlJPTEVfQURNSU4iXX0.4e_64ip--xC-iJLXFwLolAyB7LXSYfF6unUvwah78-g


https://www.codejava.net/frameworks/spring-boot/spring-security-jwt-role-based-authorization

```
User and Role
SigninRequest, SignUpRequest
UserDao
UserService
AuthenticationController
SecurityConfig:
@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService.userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

```

RESTful Documentation

http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui/index.html

============

MicroServices
 microservice architecture is a variant of the service-oriented architecture structural style. 
 It is an architectural pattern that arranges an application as a collection of loosely coupled, fine-grained services, communicating through lightweight protocols [HTTP].
 
The CAP theorem states that distributed databases can have at most two of the three properties: consistency, availability, and partition tolerance. 

https://www.researchgate.net/figure/Ubers-microservice-architecture-Source-Kappagantula-2018_fig2_342124274

1) Empty Project
2) discovery-service
    dependencies: eureka server, config client, actuator

3) copy discovery-service into created "empty project"
Setup SDK

4) File --> New -> Module From Existing Sources --> select pom.xml of "discovery-service"

5) @EnableEurekaServer
@SpringBootApplication
public class DiscoveryServiceApplication {

6) copy application.yml file which as config-client, discovery-server and port details

7) Start Discoverserver
http://localhost:8761/

https://cloud.spring.io/spring-cloud-netflix/reference/html/appendix.html

======

8) Student Service
https://github.com/BanuPrakash/VISA_SPRING/tree/main/microservices/student-service.zip

If done from scratch:
dependecies: web, jpa, mysql, lombok, cloud config client, eureka discovery client,
actuator

Extract it

copy it into created "empty project"

File --> New -> Module From Existing Sources --> select pom.xml of "student-service"

{
    "firstname": "{{$randomFirstName}}",
    "lastname": "{{$randomLastName}}",
    "email": "{{$randomEmail}}",
    "schoolId": 1
}

==================

9) School Service
If done from scratch:
dependecies: web, jpa, mysql, lombok, cloud config client, eureka discovery client,
actuator, feign client

Feign is a declarative web service client. It makes writing web service clients easier. To use Feign create an interface and annotate it.

Without Feign: we can use programatic API call using RestTemplate / WebClient/ HttpClient

@SpringBootApplication
@EnableFeignClients
public class Application {

---

@FeignClient(url = "http://localhost:8080")
public interface DemoClient {
    @GetMapping("demo")
    String getDemo();
}

Copy School-service into created "empty project"

File -> New -> Module from Existing Sources --> pom.xml of "school-service"

```
@FeignClient(name = "student-service", url = "http://localhost:8090/api/students")
public interface StudentClient {

    @GetMapping("/school/{school-id}")
    List<Student> findAllStudentsBySchool(@PathVariable("school-id") Integer schoolId);
}

POST http://localhost:8070/api/schools

{
    "name": "DPS",
    "email": "dps@gmail.com"
}

```
http://localhost:8070/api/schools/
http://localhost:8070/api/schools/with-students/1
Students are fetched from FeignClient --> Student Microservice

Simply put: you can't. Annotations arguments must be compile-time constants. Variables from application.yml are not.

bootstrap.yml
Bootstrap.yml is a YAML configuration file that Spring Boot reads during application startup. It is used to configure the application context before the application starts.

https://www.baeldung.com/spring-cloud-bootstrap-properties

=======

create project:
Gateway
    dependencies: gateway, eureka-client, actutator

Drag it into "empty project"
File --> New --> Existing Module --> pom.xml

No need to add any code
configure application.yml

http://localhost:9999/api/students
http://localhost:9999/api/schools/with-students/1

============

Spring Boot / Java --> MongoDB

1) spring-mongo

Language: java
Maven
groupId: com.visa
artifactId: spring-mongo
package: com.visa.spring-mongo

Next add dependecies
spring web
spring data mongoDB

========================


MicroServices 

A monolithic architecture is a traditional model of a software program, which is built as a unified unit that is self-contained and independent from other applications. 
one application --> all modules [customer, product, payment, order, ...]

1) Discovery Server --> Eureka Server
 <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>

@EnableEurekaServer
add details in application.properties / application.yml

http://localhost:8761/

2) School-service and student-service
 <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>


3) school-service 
   <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
  </dependency>

    Declarative REST client instead of programmatic client
    Declarative:
    ```
    @FeignClient(name = "student-service", url = "http://localhost:8090/api/students")
    public interface StudentClient {

    @GetMapping("/school/{school-id}")
    List<Student> findAllStudentsBySchool(@PathVariable("school-id") Integer schoolId);
}
    ```

    Programmatic client:
    ```
    RestTemplate
    String userJson = restTemplate.getForObject("/users/{id}", String.class, Map.of("id", "1"));

    ResponseEntity<String> responseEntity = restTemplate.getForEntity("/users/{id}", String.class, Map.of("id", "1"));

    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(responseEntity.getBody());
    ```
RDBMS vs NoSQL [MongoDB is document based, redis key/value pair, Cassandra --> column based, ... ]

orders, customer, products, address

{
    id: 199
    date:
    customer: {
        email: '',
        phone: ''
    },
    products: [

    ],
    address: {

    }
}

CQRS 
JS --> Mongodb [ can use low level mongodb driver just like JDBC for Java]
JS --> mongoose [ ODM like ORM {Hibernate} ]

Advantages of using mongoose
1) bring in schema
2) validation
3) easy to perform CRUD operations using mongoose model
db.products.find(); becomes
Product.findAll(); --> Product being the model

---

Spring boot using MongoRepository instead of JpaRepository and 
@Document instead of @Entity
@Field instead of @Column

====================================

MicroServices instead of monolithic application
TripManagment
DriverManagement
CustomerManagement
PaymentManagment

* Synchronous communication between microservices ==> RESTful or GraphQL
* Asynchronous --> Event driven ==> Kafka, Redis Pub-Sub, Kenesis, ...

===========

Spring Security

Dependencies: web, security, jpa, h2

<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
</dependency>

* by default all resources are protected
* creates a default login and logout pages
* creates a default user
username as "user" and generated password displayed in console
6a10bfa9-0c09-41d0-80c1-6eb2728de0cd

http://localhost:8080/products
redirects to login page ==> login then you can access all resources
http://localhost:8080/logout

ABF387E14FC53E7C1FE07F11E46930C5

JSESSIONID=ABF387E14FC53E7C1FE07F11E46930C5


-------------------------

* Custom Security Configuration

==================================

Using Cookies is stateful uses JSESSIONID to track user

Stateless:
Token based authorization

https://spring.io/guides/tutorials/react-and-spring-data-rest/


=======================

react> npm run build
build> will have all generated code; copy content into "src/main/resources"

First check by placing html in src/main/resources

http://localhost:8080/a.html

====



https://www.classmarker.com/online-test/start/?quiz=ace654cd08294e4e

1:30 min ==> 40 MCQ questions [ java, react, js, spring boot]

Before 3:30


