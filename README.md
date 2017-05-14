# Count Me Up
Count me up is a vote counting and results generating system, and it is a web service with a HTTP RESTFul API.

## Technologies Used:
- Java 8
- Spring Boot and Spring MVC
- JPA as the ORM
- Maven building tool
- H2 Embedded Database
- JUnit, and Mockito frameworks for unit and integration testing

## Running the application:
- Clone the repository
- Build the application using the following command on the root project: mvn clean install
- Two ways for running the application:
	1. A jar file will be create at './target' folder, copy the file and deploy it to a web server
	2. Simply you can either run the application by executing '/src/main/java/com/countme/up/spring/config/ApplicationConfig.java' or use the following command: mvn spring-boot:run
	
### Assumtions:
- Only one poll is being considered
- Data is being initialized when first run the server: 4 candidates, and 20 registered voters were created

## RESTful Web Service
### Voters End-Points:
|              URI                   |                  Description                     		              |    Method   |
|------------------------------------|------------------------------------------------------------------------|-------------|
| /voters                            | Returns the list of all voters           							  |     GET     |
| /voters                         	 | Adds a new voter, given a voter JSON object as the request body        |    POST     |
| /voters/{id}                       | Deletes a Voter, given a voter's Id                                    |    DELETE   |
| /voters/{id}                 		 | Returns a voter, given a voter's id       						      |     GET     |

### Body Request Examples:
#### Add Voter
```json
{
  	"firstname": "Ahmed",
  	"lastname": "Hamouda",
  	"email": "ahmed@hotmail.com"
}
```

### Candidates End-Points:
|              URI                   |                  Description                     		              |    Method   |
|------------------------------------|------------------------------------------------------------------------|-------------|
| /candidates                        | Returns the list of all candidates           						  |     GET     |
| /candidates                        | Adds a new candidate, given a candidate JSON object as the request body|    POST     |
| /candidates/{id}                   | Deletes a candidate, given a candidate's Id                            |    DELETE   |
| /candidates/{id}                   | Returns a candidate, given a candidate's id       					  |     GET     |

### Body Request Examples:
#### Add Candidate
```json
{
  	"firstname": "Ahmed",
  	"lastname": "Hamouda",
  	"email": "ahmed@hotmail.com",
  	"programDesc": "Description..."
}
```

### Votes End-Points:
|              				URI                                 |                  Description                |    Method   |
|---------------------------------------------------------------|---------------------------------------------|-------------|
| /votes?cid={cid}&vid={vid}&date={date}                        | Adds a vote                                 |     GET     |
| /votes/{id}                       							| Deletes a vote, given a vote's Id           |    DELETE   |
| /votes/{id}                   							    | Returns a vote, given a vote's Id           |     GET     |
| /votes/results?cid={cid}&vid={vid}&fdate={fdate}&tdate={tdate}| Returns results of the poll in a map format |     GET     |
| /votes/search?cid={cid}&vid={vid}&fdate={fdate}&tdate={tdate} | Searches the votes      					  |     GET     |

##### Query Parameters
```json
cid = Candidate Id
vid = Voter Id
fdate = From/Start date
tdate = To/End date
```

> Note: Difference between results and search end-points is that the results returns each candidate along with the number of received votes, while the search end-point returns detailed information about the votes, such as date, voters, ...etc

> Note: Date provided should be in the following format: MM-dd-yyyy. For example: 08-21-2017