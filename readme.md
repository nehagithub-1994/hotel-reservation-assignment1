# The tech stack that is being used
* Java 8
* Spring Boot
* In memory H2 database
* Junit Mockito for testing

As per requirement I have provided 3 APIs.

1. For booking presidential suite "localhost:8080/booking/presidential-suite" can be used.This API required JSON body to be provided.
   the sample format can be found at testData.json file inside resources folder in test.
2. For getting reservation details use the reservation ID provided in booking response as a path variable to:
   localhost:8080/booking/reservation-details/{reservationId}
3. API for booking cancellation localhost:8080/booking/cancel/{reservationId}

To run the application:

1. Go to the project directory and do a maven build and generate .jar file using command
   mvn clean install
3. .Run that jar using command
   java -jar api-0.0.1.jar
4. Application will be started and can be accessible at default port 8080




