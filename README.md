# TableForTwo Backend

Before running this project, you must install Java >=1.8 on your machine.

- Install Maven: https://maven.apache.org/download.cgi
- Install Visual Studio Code: https://code.visualstudio.com/download
- Install Lombok plugin for Visual Studio Code

To get help, search for documentation on Spring Boot.

# Running the application locally
1. `cd` into the project root
2. `mvn package` to build the jar file
3. `java -jar ./target/*.jar` to run locally

### Deploy to the server
Whenever you push your committed code to gitlab, the project will be compiled and copied over to the EC2 server. This process should take 5 minutes or less. You can check the status in the jobs section of the gitlab repo.

### Pushing your changes to gitlab
- Note: Only push your code to gitlab if it compiles and runs. Otherwise, it will be a headache for others when they pull your incomplete changes.
```
git add .
git commit -m "<INSERT MESSAGE>"
git push
