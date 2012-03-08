#jbas7MemoryProbe
![picture alt](http://www.deepvoodoo.net/images/jbas7probe/sample.png "Example monitor clients") 

**Tired of bringing down JBoss with your latest deploy because you forgot to check Permgen?**

It happens to all of us. We all know it's going to happen...just not when.

This small set of REST services will expose the Permgen and Heap memory status on your local Jboss AS 7 
instance allowing you to display it in your favorite system monitor or javascript component and know 
before you push the next deployment that you should restart to clear permgen. 


##What is it?

A simple REST interface to the Jboss AS 7 memory metrics exposed by the JBoss CLI interfaces.

Provides a simple way to get permgen and heap stats on local dev instances of JBoss AS 7 so they 
can be exposed through things like Conky or other monitoring tools. 

Includes sample client implementations for:
- conky
- Syncrhonous Javascript
- Asynchronous Javascript using JQuery
- Embedded tiny javascript client

##System requirements
This is a deployable Maven 3, Java EE 6 project for JBoss AS 7.

All you need to build and run this project is Java 6.0 (Java SDK 1.6) or better, Maven
3.0 or better, and JBoss AS 7.

##Deploying the application

You can copy the downloadable .war file to your JBOSS_HOME/standalone/deployments directory and then 
navigate to: http://localhost:8080/jbas7MemoryProbe

If you wish to build & deploy from source using Maven 3:
	mvn package
	mvn jboss-as:deploy
