# NinjaRMM test service

## About

This is a REST API built in java with data persistence to provide the following functionalities:

1- Customer should be able to get, add, update or delete devices. Implement endpoints
to provide these operations.

2- Customer should be able to add or delete available services to his/her account. Also,
customer can see all services selected. Implement endpoints to provide these
operations. To add the same service more than once is not allowed.

3- Customer needs to calculate the total monthly cost of the deal depending on
selected services and the number of devices in database. Each device cost $4.
4- Secure the API.

It was made using **Spring Boot**, **Spring Security**, **Spring Data JPA**, 

Database is in memory **PosgreSQL**.


## Configuration

### Configuration Files

Folder **src/main/resources/** contains config files for **rmm-services-server-app** Spring Boot REST API.

* **src/main/resources/application.properties** - main configuration file. 

## How to run

You can run it from the command line with included Maven Wrapper or installed maven.

Once the app starts, go to the web browser and visit `http://localhost:8070/rmm-services`

Admin username: **admin**

Admin password: **root**

User username: **esalazar**

User password: **esalazar**

### Maven

Open a terminal and run the following commands to ensure that you have valid versions of Java (1.8) and Maven (>= 3.3.9 ) installed:

```bash
$ java -version
```

```bash
$ mvn -v
```

#### Using Executable Jar

To create an executable jar run:

```bash
$ mvn clean package
``` 

To run that application, use the java -jar command, as follows:

```bash
$ java -jar target/rmm-services-server-app-1.0.0.jar
```

To exit the application, press **ctrl-c**.

## Tests

Tests can be run by executing following command from the root of the project:

```bash
$ mvn test
```

### PostgreSQL Database configuration

It's necessary to have a local instance of PosgreSQL running in the workstation.

It's all necessary to define a database called **rmm-operation-bdd**, that name 
is defined in the `/src/main/resources/application.properties` file.
