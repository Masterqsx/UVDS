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
  - Alternative: Maven(worse script)

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

## Story 2 - Hello World
### Description
This task is about using Gradle to initialize the whole project, configure dependencies: Spring Boot and deploy to Docker container.

### Instruction
We initialize the whole project with type under our root directory `UVDS`:
```
gradle init
```
Then the basic Gradle configuration scripts and [Gradle Wrapper](https://docs.gradle.org/4.8/userguide/gradle_wrapper.html?_ga=2.128419721.428200427.1533059957-1761894786.1513880699) will be created. Gradle Wrapper is a stand-alone build program to make sure all users can share the same build environment. I recommend to use wrapper to execute any task like this under our root directory `UVDS`:
```
./gradlew [task name]
```
We introduce customized properties of this project such as description and version by using `gradle.properties` under `UVDS`.
```
touch gradle.properties
```
We put description and version into this file at first. So this file looks like:
```
description = "Unified Vision Data Service"
version = "0.0.0"
```
You can also put those properties into `build.gradle` but I prefer to group properties into a single file.

```
The difference between Gradle plugin, module dependecy and build classpath is:
1. Gradle plugin is Gradle preset task
2. module dependecy is the library compiled with your code
3. build classpath is needed for Gradle
```
```
We can see a lot Gradle script like this:
buildScript {
    repositories {
         mavenCentral()
    }
}

repositories {
     mavenCentral()
}
The buildScript block is for gradle script itself and the other is for the project itself. For Spring Boot, with old Gradle, you have to put its plugin in both buildScript and apply() method.
```

### Reference
[Gradle Guide](https://guides.gradle.org/creating-new-gradle-builds/?_ga=2.60313384.428200427.1533059957-1761894786.1513880699)
