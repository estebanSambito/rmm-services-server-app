# NinjaRMM API REST test service

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

This API was made using **Spring Boot**, **Spring MVC**, **Spring Data JPA**, **JWT**, **spring-boot-starter-test**

Database **PosgreSQL**.

Database for testing **H2**.

## Configuration

### Configuration Files

Folder **src/main/resources/** contains config files for **rmm-services-server-app** Spring Boot REST API.

## PostgreSQL Database configuration

It's necessary to have a local instance of PosgreSQL running in the workstation.

Before run the application create a new database called **rmm-operation-bdd**, 
this value could be changed and is defined in the
`/src/main/resources/application.properties` file.

Its necessary to configure a user with Administration role.

## Initial data
In order to have default users and products it is necessary to run the Script
 **src/test/resources/initialData.sql** to register information in the database.



## How to run

You can run it from the command line with included Maven Wrapper or installed maven.

### Maven

Open a terminal and run the following commands to ensure that you have valid versions of Java (1.8) and Maven (>= 3.3.9 ) installed:

#### Using Executable Jar

To create an executable jar run inside the project root directory:

```bash
$ mvn clean package
```

**To run that application, use the java -jar command, as follows:**

```bash
$ java -Dspring.datasource.username=postgres -Dspring.datasource.password=root -jar target/rmm-services-server-app-0.0.1-SNAPSHOT.jar
```

Define the database credentials according with the database user to user for the JVM variables:

spring.datasource.username=postgres
spring.datasource.password=root

**To change the database url, port, or database name** set the property
spring.datasource.url=jdbc:postgresql://localhost:5432/rmm-operation-bdd

in the running command using the -D option

```bash
$ java -Dspring.datasource.username=postgres -Dspring.datasource.password=root -Dspring.datasource.url=jdbc:postgresql://localhost:5432/rmm-operation-bdd -jar target/rmm-services-server-app-0.0.1-SNAPSHOT.jar
```

The application will be running locally in **8040 port**

To exit the application, press **ctrl-c**.

## Tests

Tests can be run by executing following command from the root of the project:

```bash
$ mvn test
```

##REST API

The RMM REST API Exercise is described below.

#Authenticate user

Operation to validate user credential and get an authentication token
required for next requests.

`POST /login`

    curl -d '{"userName":"esalazar","password":"esalazar"}' -H 'Accept: application/json' http://localhost:8040/api/v1/login

### Response

    HTTP/1.1 200 OK
    Date: Fri, 27 Aug 2021 05:30:49 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 2

	{
	    "idUser": 0,
	    "userName": "esalazar",
	    "password": null,
	    "token": "Bearer ...",
	    "autenticado": true
	}
	
**If no token is provided	the response for all the request would be:**


    HTTP/1.1 404 Forbiden
    Date: Fri, 27 Aug 2021 06:40:54 GMT
    Status: 403 Forbiden
    Connection: close
    Content-Type: application/json
    Content-Length: 2

	{}

#Add Device

Operation to add service or device for the current month

`POST /detailDevice`

    curl -d '{"idDetail":1,"product":{"idProd":1},"quantity":1}' -H 'Accept: application/json' -H 'Authorization: Bearer ...' http://localhost:8040/api/v1/detailDevice


#Add Service

Operation to add service or device for the current month

`POST /detailservice`

    curl -d '{"idDetail":1,"product":{"idProd":1},"quantity":2}' -H 'Accept: application/json' -H 'Authorization: Bearer ...' http://localhost:8040/api/v1/detailservice

### Response Service or Device already registered

    HTTP/1.1 404 Not Found
    Date: Fri, 27 Aug 2021 05:30:49 GMT
    Status: 404 Not Found
    Connection: close
    Content-Type: application/json
    Content-Length: 2

	{
    "timestamp": "2021-08-27T05:46:54.944+00:00",
    "message": "Product id [1] already registered",
    "details": "uri=/api/v1/detailservice"
    }

### Response Service or Device registered

    HTTP/1.1 200 OK
    Date: Fri, 27 Aug 2021 05:30:49 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 2

	{
    "idDetail": 1,
    "product": {
        "idProd": 1,
        "name": "windows",
        "type": "DEV",
        "cost": 4.00
    },
    "invoice": null,
    "quantity": 2
	}    
    
#Update Device or Service

Operation to modify the quantity of service or device registred

`PUT /detailservice`

    curl -d '{"idDetail":1,"quantity":4}' -H 'Accept: application/json' -H 'Authorization: Bearer ...' -X PUT http://localhost:8040/api/v1/detailservice

### Response Service or Device updated

    HTTP/1.1 200 OK
    Date: Fri, 27 Aug 2021 05:30:49 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 2

	{
    "idDetail": 1,
    "product": null,
    "invoice": null,
    "quantity": 20
	}    
    
#Remove Device or Service

Operation to delete a service or device registred

`REMOVE /detailservice`

    curl -d '{"idDetail":1}' -H 'Accept: application/json' -H 'Authorization: Bearer ...' -X REMOVE http://localhost:8040/api/v1/detailservice

### Response Service or Device removed

    HTTP/1.1 200 OK
    Date: Fri, 27 Aug 2021 05:30:49 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 2

	{}    
        
#GET Monthly cost

Operation to get Monthly invoice cost for services and devices registred

`GET /detailservice/cost`

    curl -v -H 'Accept: application/json' -H 'Authorization: Bearer ...' http://localhost:8040/api/v1/detailservice/cost

### Response

    HTTP/1.1 200 OK
    Date: Fri, 27 Aug 2021 05:30:49 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 2

	{
    "idBilling": 0,
    "products": null,
    "user": {
        "idUser": 1,
        "userName": "esalazar",
        "password": null,
        "token": null,
        "autenticado": false
    },
    "creationDate": "2021-07-27T05:04:26.500+00:00",
    "total": 71.00,
    "status": null
    }   
        