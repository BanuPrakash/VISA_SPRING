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
    @Autowired
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
