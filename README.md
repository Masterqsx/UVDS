# Unified Vision Data Service
## Story 1 - Project Plan
### Target
This goal of this project is to provide a platform for vision data analytics as a service.
To provide analytical vision content that can drive innovation, vision data stored in different resources can be brought together to perform analysis.
### General Pipeline
![General Pipeline](docs/img/GeneralPipeline.jpg)

### Framework Dependency
#### [Spring Boot](https://spring.io/guides/gs/spring-boot/)
  - Lightweight Java Web Framework
  - Easily solve IOC and AOP
  - Integration with well known patterns(Tomcat, Jackson, Hibernate, Log4j)
  - Alternative: Spring(complex configuration), Dropwizard

#### [Gradle](https://gradle.org/guides/)
  - Project Build and Management Framework
  - Alternative: Maven(better script)

#### [MySQL 8.0](https://dev.mysql.com/doc/refman/8.0/en/tutorial.html)
  - RDBMS
  - Alternative: PostgreSQL

#### [Kafka](https://kafka.apache.org/intro)
  - Distributed Message Queue
  - Alternative: RabbitMQ

#### [Hadoop](http://hadoop.apache.org/docs/current/)
  - Distributed Big Data Processing Framework
  - Alternative: Spark

#### [HBase](http://hbase.apache.org/book.html#getting_started)
  - Distributed NoSQL Store

#### [Avro](http://avro.apache.org)
  - Data Serialization Framework
  - Compatible with Hadoop Eco
  - Alternative: protobuf

#### [Redis](https://redis.io)
  - In-Memory Distributed Key-Value Store
  - Best for Buffer

#### [Jenkins](https://jenkins.io)
  - Automate Build and Deployment Tool

#### [Docker](https://www.docker.com)
  - Web Application Virtualization Tool
  - Alternative: VM(heavy)

#### [Hibernate](http://hibernate.org)
  - ORM Framework
  - Mapping Relational Data into Object
  - Alternative: MyBatis

#### [Tomcat](http://tomcat.apache.org)
  - Servlet Container
  - Alternative: Jetty

#### [Jackson](https://github.com/FasterXML/jackson-docs)
  - Serialize POJOs to Json or XML

#### [Log4j](https://logging.apache.org/log4j/2.x/javadoc.html)
  - Logging Framework
