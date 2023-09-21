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
	





