# Pets tracking service

Pets tracking service enables end users to add new pets with tracking data, update pet tracking data, 
query tracking data of pets and provide stats related to out of zone pets.


## Approach
1. I have used spring boot framework to create the application to enable faster development.
2. Used jdbctemplate spring package as it provides easy mechanism of managing JDBC queries and also addresses the requirement of visibility of actual SQL queries.
3. I have used basic JAX-RS annotations viz. Rest Controller, @PostMapping.
5. Used flyway tool to manage database migrations as helps managing the schema changes easily.
6. Swagger library is used to provide appropriate API documentation.

## Problems faced
1. Understanding the jdbctemplate spring package.
2. Deciding on the approach of dockerizing the application.

## Getting Started

Instructions to get the application up and running on local setup.

### Prerequisites

Install following list of software

```
Maven 

Maven installation details: https://maven.apache.org/install.html

Docker

Docker installation details: https://docs.docker.com/desktop/install/mac-install/

```

### Deploying the service on local setup

A step by step series of examples that tell you how to get a development env running


1. Unzip the zip shared over the mail or download the zip from github. 



2. Copy the folder present inside the zip to your arbitrary file system location.

3. Browse to folder where folder is copied in the previous step.

```
   cd <Location of pets tracking service folder in your machine>
```

4. Maven build the pet tracking service application using the below command

```
mvn clean install 
```

5. Build docker container using the below command

```
docker build --tag=pets-tracking-service:latest .
```

6. Start the docker container using the below command

```
docker run -p8080:8080 -e DB_USER=tractive -e DB_PASS=test123 pets-tracking-service:latest
```

7. Check for successful start of application

```
Tomcat started on port(s): 8080 (http) with context path ''
```

## API details

URL at which application is available: http://localhost:8080/

1. API to onboard pet tracking data / API to create pet entity

```
HTTP Method: POST

Header: Content-Type:application/json

URI: /api/pets

Request Body:

{
    "name": "String",
    "petType": "String",
    "trackerType": "String",
    "ownerId": int,
    "inZone": boolean
}

Response:

HTTP status code 201

Response Body:
{
    "petId": int,
    "name": "String"
}
```
Sample curl request to create pet entity
```
curl --location 'http://localhost:8080/api/pets' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Tom",
    "petType": "Dog",
    "trackerType": "SMALL",
    "ownerId": 3,
    "inZone": false
}'
```

2. API to update zone of pet

```
HTTP Method: PUT

Header: Content-Type:application/json

URI: pets/{id}

Request Body:

{
    "inZone": boolean
}

Response:

HTTP status code 202

Body:
{
    "id": int,
    "name": String,
    "petType": String,
    "trackerType": String,
    "ownerId": int,
    "inZone": boolean
}
```
Sample curl request to update the zone data for a pet
```
curl --location --request PUT 'http://localhost:8080/api/pets/<id of the pet to update>' \
--header 'Content-Type: application/json' \
--data '{
    "inZone": false
}'

```

3. API to update the lost tracker data for cat

```
HTTP Method: PUT

Header: Content-Type:application/json

URI: /api/lost-tracker/pets/{petId}

Response:

HTTP status code 200

Body:
HTTP status code 202

Body:
{
    "id": int,
    "name": String,
    "petType": String,
    "trackerType": String,
    "ownerId": int,
    "inZone": boolean
}

```
Sample curl request to update lost tracker data
```

curl --location --request PUT 'http://localhost:8080/api/lost-tracker/pets/<id of the pet to update>' \
--header 'Content-Type: application/json' \
--data '{
    "lostTracker": true
}'

```

4. API to fetch number of pets currently outside the power saving zone grouped by pet type and tracker type"

```
HTTP Method: GET

Header: Content-Type:application/json

URI: out-of-zone/pets

Query parameter: groupBy=tracker-type / groupBy=pet-type


Response:
HTTP status code 200

Response Body:

{
    "countByTracker": [
        {
            "trackerType": String,
            "count": int
        },
        {
            "trackerType": String,
            "count": int
        },
        {
            "trackerType": String,
            "count": int
        }
    ]
}

```
Sample curl request to fetch number of pets currently outside the power saving zone grouped by pet type and tracker type
```
curl --location 'http://localhost:8080/api/out-of-zone/pets?groupBy=tracker-type' \
--header 'Content-Type: application/json'

```

Note: Detailed api documentation could be found after we start the docker container at following URL

```
UI format: http://localhost:8080/swagger-ui/index.html

API docs: http://localhost:8080/v2/api-docs

```

## Built With

* Spring boot: Application framework(Faster development)
* Maven: Dependency management
* H2: Embeded database
* API documentation: swagger
* Flyway: Database migration tool
* CI/CD: Docker

## Code repository

https://github.com/chinmay12/tractive


## Authors

Chinmay Nalawade email: nalawade.chinmay@gmail.com
