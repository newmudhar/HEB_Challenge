# HEB_Challenge
This is to upload HEB code challenge project to be shared via email

Please note the app is not 100% completed due time constraints, but it should present the idea/requirement and design.

To get the app up and running, below are the tools/environments used to implement:

- IDE: IntelliJ 14 (highly recommended for this up for setting compatibility)
- Web-Service: http restful with the resteasy framework that ships with Jboss
- JVM Container: Jboss/wildfly 8.2.1 (newer ones should has backward compatibility)
- Build tool: Maven using the IDE plugin (pom.xml included in the uploaded package)
- App Server Implementation: Java-Servlet (J2EE 7) - mapping done through the deployment descriptor
- UI: all gui pages were built with jsp/html, only functionality no style.
- DB: MySql, server has to be up and running on localhost:3306 with schema (included in the uploaded package) deployed
	- only the url of the giphy gifs were stored into the DB (light database usage yet high dependency on giphy APIs/Servers
- all rest service should reachable using any rest client (ARC was used for testing purposes)
- dependencies: all required jars are included in pom.xml
- Security: a basic security implementation for a random token to be issued by the server and persisted into the DB with a timestamp, every request related to the user should have the username and the token in the http request header where the server will examine the validity
	- all tokens will purged automatically by the DB server every 24 hours.

