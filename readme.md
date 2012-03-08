#jbas7MemoryProbe

##What is it?
A deployable Maven 3, Java EE 6 project on JBoss AS 7.

A simple REST interface to the Jboss AS 7 memory metrics exposed by the JBoss CLI interfaces.

Provides a simple way to get permgen and heap stats on local dev instances of JBoss AS 7 so they 
can be exposed through things like Conky or other monitoring tools. 

Includes sample client implementations for:
- conky
- Syncrhonous Javascript
- Asynchronous Javascript using JQuery
- Embedded tiny javascript client

##System requirements
All you need to build and run this project is Java 6.0 (Java SDK 1.6) or better, Maven
3.0 or better, and JBoss AS 7.

##Deploying the application

You can copy the downloadable .war file to your JBOSS_HOME/standalone/deployments directory and then 
navigate to: http://localhost:8080/jbas7MemoryProbe

If you wish to build & deploy from source using Maven 3:
mvn package
mvn jboss-as:deploy
