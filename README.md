# MICROSERVICES IN AWS

This application is a minimal version of the well-known X (Twitter) app, developed in Java and Spring Boot. Initially, it started as a monolith that included three entities (**User, Stream, and Post**), connected to a **MySQL database** running in a **Dockerized local environment**. For testing with **AWS Lambda and API Gateway**, it was deployed on an **AWS EC2 instance**. Later, the system was divided into **microservices**, implemented in **pure Java without a framework**.

## Getting Started

The following instructions will allow you to run the project locally on your machine.

### Prerequisites

You need to have the following installed:

1. **Java** (versions 17 or 21)
   To verify the version in a console or terminal, run:

   ```sh
   java -version
   ```

   The output should look something like this:

   ```sh
   java version "17.0.12" 2024-07-16 LTS
   Java(TM) SE Runtime Environment (build 17.0.12+8-LTS-286)
   Java HotSpot(TM) 64-Bit Server VM (build 17.0.12+8-LTS-286, mixed mode, sharing)
   ```

2. **Maven**
   - To download, visit [here](https://maven.apache.org/download.cgi).
   - Follow the installation instructions [here](http://maven.apache.org/download.html#Installation).
   To verify the installation, run:

   ```sh
   mvn -v
   ```

   The output should look something like this:

   ```sh
   Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
   Maven home: /Applications/apache-maven-3.9.9
   Java version: 17.0.12, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
   Default locale: es: `CO, platform encoding: UTF-8
   OS name: "mac os x", version: "12.7.6", arch: "x86: `64", family: "mac"
   ```

3. **Git**
   - To download, visit [here](https://git-scm.com/downloads).
   - Verify the installation by running:

   ```sh
   git --version
   ```

   The output should look something like this:

   ```sh
   git version 2.46.0
   ```

4. **Docker**
   - To download, visit [here](https://www.docker.com/).
   - Verify the installation by running:

   ```sh
   docker --version
   ```

   The output should look something like this:

   ```sh
   Docker version 25.0.3, build 4debf41
   ```

### Installation

1. Clone the repository and navigate to the folder containing the `pom.xml` file using the following commands:

   ```sh
   git clone https://github.com/Mar972310/AREP_-MICROSERVICIOS.git
   cd AREP_-MICROSERVICIOS
   ```
2. Create a database in a Docker container using the following command:

```
  docker run -p 3306:3306 --name properties  -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:latest
``` 

3. Build the monolith:

   ```sh
   cd microservices 
   mvn clean package
   ```

   The console output should look something like this:

   ```sh
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  21.968 s
    [INFO] Finished at: 2025-03-06T22:51:13-05:00
    [INFO] ------------------------------------------------------------------------
   ```

4. Run the monolith:

      ```sh
      mvn spring-boot:run
      ```
      The console should display the following message:
      ```sh
        SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
        2025-03-20T21:41:57.826-05:00  INFO 75454 --- [microservices] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
        2025-03-20T21:41:58.400-05:00  INFO 75454 --- [microservices] [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl@5bd3ca3c
        2025-03-20T21:41:58.404-05:00  INFO 75454 --- [microservices] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
        2025-03-20T21:41:58.644-05:00  INFO 75454 --- [microservices] [           main] org.hibernate.orm.connections.pooling    : HHH10001005: Database info:
                Database JDBC URL [Connecting through datasource 'HikariDataSource (HikariPool-1)']
                Database driver: undefined/unknown
                Database version: 9.0.1
                Autocommit mode: undefined/unknown
                Isolation level: undefined/unknown
                Minimum pool size: undefined/unknown
                Maximum pool size: undefined/unknown
        2025-03-20T21:42:00.160-05:00  INFO 75454 --- [microservices] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
        2025-03-20T21:42:00.826-05:00  INFO 75454 --- [microservices] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
        2025-03-20T21:42:01.658-05:00  WARN 75454 --- [microservices] [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
        2025-03-20T21:42:01.987-05:00  WARN 75454 --- [microservices] [           main] ion$DefaultTemplateResolverConfiguration : Cannot find template location: classpath:/templates/ (please add some templates, check your Thymeleaf configuration, or set spring.thymeleaf.check-template-location=false)
        2025-03-20T21:42:02.550-05:00  INFO 75454 --- [microservices] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
        2025-03-20T21:42:02.578-05:00  INFO 75454 --- [microservices] [           main] e.e.e.a.m.MicroservicesApplication       : Started MicroservicesApplication in 8.695 seconds (process running for 9.597)
      ```

## Class Diagram (monilith)

![alt text](imagenes/clases.png)

### **Class Overview:**

1. **CorsConfig (Configuration Layer)**  
   - **`addCorsMappings(CorsRegistry registry)`**: Configures CORS settings to allow cross-origin requests.

2. **Model Layer (Entities)**  
   - **`Post`**: Represents a social media post, containing an ID, message, number of likes, and the associated user.  
   - **`User`**: Represents a user with an ID, username, and password.  
   - **`Stream`**: Represents a collection of posts under a specific stream name.  

   **Relationships:**  
   - A **Post** belongs to a **User**.  
   - A **Stream** contains multiple **Posts**.

3. **DTO Layer (Data Transfer Objects)**  
   - **`PostDTO`**, **`StreamDTO`**, **`UserDTO`**: Serve as intermediary objects between the controller and service layers, ensuring proper encapsulation of data when transferring between layers.

4. **Repository Layer**  
   - **`PostRepository`**, **`StreamRepository`**, **`UserRepository`**: Interfaces extending `JpaRepository`, providing CRUD operations for each entity.  
   - **`StreamRepository`** includes a custom method `findByName(String name)` to retrieve a stream by name.

5. **Service Layer (Interfaces and Implementations)**  
   - Defines business logic for posts, users, and streams.  
   - The interfaces define service methods, and the implementations provide concrete logic.  

   **Relationships:**  
   - `PostService` works with **PostDTO** and **Post**.  
   - `StreamService` works with **StreamDTO** and **Stream**.  
   - `UserService` works with **UserDTO** and **User**.  

6. **Controller Layer (API Endpoints)**  
   - **`PostController`**: Handles post-related operations (retrieve, create, delete, add likes).  
   - **`StreamController`**: Manages stream-related operations.  
   - **`UserController`**: Manages user-related operations (retrieve, register users).  


## AWS S3: **Deployment of a static website.**

To host a static website on AWS S3, an S3 bucket was created with a unique name, and public access was enabled by disabling "Block all public access." The website files, including HTML, CSS, JavaScript, and assets, were uploaded while maintaining the correct folder structure. Static website hosting was then enabled in the bucket's "Properties" tab, with the index document (e.g., `index.html`) specified. Finally, the bucket policy was updated to allow public read access, ensuring the website could be accessed by users.

![alt text](imagenes/s3.gif)
![alt text](imagenes/s3.png)

## AWS Lambdas

After splitting the microservices (as suggested by the instructor, these will be Maven projects without using frameworks), we will begin creating the different AWS Lambda functions via the AWS Lambda console. The microservices will be connected to a database hosted on an EC2 instance.

## Steps to deploy the microservices' Lambda functions

![alt text](<imagenes/creacion de lambda.gif>)

### Post Service

1. **Create a Lambda function and perform initial configurations**, including selecting Java 21, naming the function, and assigning the execution role (labRole).

   ![alt text](imagenes/createPostL.png)
   ![alt text](imagenes/updatePostL.png)
   ![alt text](imagenes/getAllPostL.png)
   ![alt text](imagenes/getpostL.png)
   ![alt text](imagenes/deletepostL.png)

2. **Compile the project** to create a JAR that includes the dependencies needed to connect to the database. To compile, execute the following commands:

   ```sh
   cd AREP_-MICROSERVICIOS/lambdas/post
   mvn clean package
   ```

   You should see output similar to the following:

   ```
   [INFO] Replacing D:\2025-1\Maria\delete\AREP_-MICROSERVICIOS\lambdas\post\target\post-1.0-SNAPSHOT.jar with D:\2025-1\Maria\delete\AREP_-MICROSERVICIOS\lambdas\post\target\post-1.0-SNAPSHOT-shaded.jar
   [INFO] Dependency-reduced POM written at: D:\2025-1\Maria\delete\AREP_-MICROSERVICIOS\lambdas\post\dependency-reduced-pom.xml
   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD SUCCESS
   [INFO] ------------------------------------------------------------------------
   [INFO] Total time:  3.028 s
   [INFO] Finished at: 2025-03-20T09:41:18-05:00
   [INFO] ------------------------------------------------------------------------
   ```

   ![alt text](imagenes/jarPost.png)

3. **Upload the JAR file** to all five Lambda functions created earlier. The following images illustrate the process for one function, but it must be repeated for all of them:

   ![alt text](imagenes/subjarl.png)
   ![alt text](imagenes/subirjarL.png)

4. **Run tests** to verify the functions before integrating them into API Gateway:

   - **Create Post**
     ![alt text](imagenes/createPostP.png)
   - **Update Post**
     ![alt text](imagenes/updatePostP.png)
   - **List all Posts**
     ![alt text](imagenes/getAllPostsP.png)
   - **Get a Post**
     ![alt text](imagenes/getPostP.png)

### User Service

1. **Create a Lambda function and configure it**, selecting Java 21, setting the function name, and assigning the execution role (labRole).

   ![alt text](imagenes/createUserL.png)
   ![alt text](imagenes/getAllUserL.png)
   ![alt text](imagenes/getUserL.png)

2. **Compile the project** to generate the JAR with the necessary dependencies:

   ```sh
   cd AREP_-MICROSERVICIOS/lambdas/user
   mvn clean package
   ```

   The expected output will be similar to:

   ```
   [INFO] Replacing /Users/maritzamonsalvebautista/Documents/AREP_-MICROSERVICIOS/lambdas/user/target/user-1.0-SNAPSHOT.jar with /Users/maritzamonsalvebautista/Documents/AREP_-MICROSERVICIOS/lambdas/user/target/user-1.0-SNAPSHOT-shaded.jar
   [INFO] Dependency-reduced POM written at: /Users/maritzamonsalvebautista/Documents/AREP_-MICROSERVICIOS/lambdas/user/dependency-reduced-pom.xml
   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD SUCCESS
   [INFO] ------------------------------------------------------------------------
   [INFO] Total time:  6.411 s
   [INFO] Finished at: 2025-03-20T17:44:20-05:00
   [INFO] ------------------------------------------------------------------------
   ```

3. **Upload the JAR file** to all five Lambda functions:

   ![alt text](imagenes/subirUser.png)
   ![alt text](imagenes/subirUserL.png)

4. **Run tests** before integrating with API Gateway:

   - **Create User**
     ![alt text](imagenes/createUserP.png)
   - **List all Users**
     ![alt text](imagenes/getAllUserP.png)
   - **Get User by ID**
     ![alt text](imagenes/getUserP.png)

### Stream Service

1. **Create a Lambda function and configure it**, selecting Java 21, setting the function name, and assigning the execution role (labRole).

   ![alt text](imagenes/getStreamL.png)
   ![alt text](imagenes/getAllUserL.png)
   ![alt text](imagenes/removePostL.png)

2. **Compile the project** to generate the JAR with the necessary dependencies:

   ```sh
   cd AREP_-MICROSERVICIOS/lambdas/stream
   mvn clean package
   ```

   The expected output will be similar to:

   ```
   [INFO] Replacing /Users/maritzamonsalvebautista/Documents/AREP_-MICROSERVICIOS/lambdas/user/target/stream-1.0-SNAPSHOT.jar with /Users/maritzamonsalvebautista/Documents/AREP_-MICROSERVICIOS/lambdas/stream/target/stream-1.0-SNAPSHOT-shaded.jar
   [INFO] Dependency-reduced POM written at: /Users/maritzamonsalvebautista/Documents/AREP_-MICROSERVICIOS/lambdas/stream/dependency-reduced-pom.xml
   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD SUCCESS
   [INFO] ------------------------------------------------------------------------
   [INFO] Total time:  6.411 s
   [INFO] Finished at: 2025-03-20T17:44:20-05:00
   [INFO] ------------------------------------------------------------------------
   ```

3. **Upload the JAR file** to all five Lambda functions:

   ![alt text](imagenes/subirStreamL.png)

4. **Run tests** before integrating with API Gateway:

   - **Get Stream**
     ![alt text](imagenes/getStreamP.png)
   - **Add Post**
     ![alt text](imagenes/addPostP.png)
   - **Remove Post**
     ![alt text](imagenes/removePostP.png)

## Configuracion del API Gateway

![alt text](imagenes/apigateway.gif)


### Test API Gateway

![alt text](imagenes/apiG.gif)




