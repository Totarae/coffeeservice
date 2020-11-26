# coffeeservice
Simple SpringBoot-based project for coffee sort formulary

Prerequisites:
1. MongoDB up and running on default port.
  - MongoDB authorization at will
 2. Dockerfile in project directory
 
How to:
1. ``` mvn clean package ```
2. ```java -jar target/coffeeservice-0.0.1.jar```
2. Navigate to project directory and ```docker build -t coffeeservice:0.0.1 .```
3. ``` docker image ls ``` 
4. ```docker run -d -p 8080:8080 -t coffeeservice:0.0.1```
  - management port at will
