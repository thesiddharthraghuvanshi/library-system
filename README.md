# Library System
RESTful API that manages a Library System

## Prerequisite
    Java (version- 17)
    Maven (version- 4.0.0), command to check Maven version - "mvn -v"
    Spring Boot (version 3.3.2)
    MySQL Server - for database

## Database Set-up
    To create and set up a database for your Spring Boot application based on the provided properties file and entity classes, follow these steps:

    1. Ensure MySQL Server is Running

        Make sure your MySQL server is up and running. 
        You can check this using a MySQL client or by running the MySQL service on your machine.
        
    2. Create the Database

        You need to create a database that matches the spring.datasource.url configuration (librarysystem). 
        You can do this using a MySQL client like mysql command line, MySQL Workbench, or any other database management tool.
        
        Using MySQL Command Line:

        Open your command line interface (CLI).
        Log in to MySQL as the root user: "mysql -u your_userName -p"
        Enter your password when prompted.
        Create the database: "CREATE DATABASE librarysystem;"
        Exit MySQL: "EXIT;"

    3. Configure Spring Boot Properties

    Make sure your application.properties file is correctly set up. Based on your provided properties file, 
    it should look like this:
            
            spring.application.name=library-system
            spring.datasource.url=jdbc:mysql://your_host:your_port/librarysystem
            spring.datasource.username=your_userName
            spring.datasource.password=your_password
            spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
            spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
            spring.jpa.hibernate.ddl-auto=update

            # Swagger UI custom path
            springdoc.swagger-ui.path=/swagger-ui.html
            springdoc.api-docs.path=/api-docs

    for reference, you can check my application properties file under resource package.

## Installing Dependency
    Open terminal & Navigate to the project (your_base_path_to_project\library-system)
    run command - "mvn clean install"

## Building the project
    If you haven't already built the project or need to rebuild it, run:
    "mvn clean package"

## RUN the application
    Method 1-
    After building the project, you can start your Spring Boot application with Maven by using the Spring Boot Maven Plugin.
    Run the following command:
    "mvn spring-boot:run"
    This command uses the Spring Boot Maven Plugin to start the application directly from the source code.

    Running the JAR File (Alternative Method) - 
    If you prefer or if you have a packaged JAR file, you can run the application directly from the JAR file created during the build process.

    Locate the JAR File:
    After running "mvn clean package", the JAR file is typically located in the target directory of your project. 
    The JAR file will be named something like library-system-0.0.1-SNAPSHOT.jar based on pom.xml.
    Run the JAR File:
    Use the java -jar command to run the JAR file. Replace your-jar-file.jar with the actual name of your JAR file:
    "java -jar target/library-system-0.0.1-SNAPSHOT.jar"

    Once you start the application, you should see log output indicating that Spring Boot has started. 
    By default, Spring Boot applications run on port 8080. 
    You can access the application by opening a web browser and navigating to:
    
## Swagger
Use below link to open swagger html where you can hit and try the REST calls
    http://localhost:8080/swagger-ui/index.html

### Asumptions
    1- ISBN number is a string (alpah-numeric)

    2- different app.props file can be find "./resource/..."

    3- used springdoc-openapi-starter-webmvc-ui for documentation

    4- SecurityConfigs file can be find "./common/config/..."

### Database - MySQL
    1- Open Source and Cost-Effective: 
        MySQL is open-source software, which means it's free to use for most applications. 
        This can significantly reduce licensing costs compared to proprietary databases.

    2- ACID Compliance: 
        MySQL ensures ACID (Atomicity, Consistency, Isolation, Durability) compliance for transactions, 
        which ensures data integrity and reliability.

    3- Performance: 
        MySQL is known for its speed and performance. 
        It can handle a large number of concurrent connections and transactions efficiently, which is crucial for scalable applications.

    4- Scalability: 
        MySQL can scale both vertically (by adding more resources to a single server) and 
        horizontally (by distributing data across multiple servers). This scalability makes it suitable for growing applications.
