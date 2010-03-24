OpenStrategies-org
http://github.com/sfrancolla/openstrategies-org

Copyright (c) 2010 OpenStrategies, Steven Francolla (sfrancolla@gmail.com)

Released under the LGPLv3.0 license: 
http://www.gnu.org/licenses/lgpl-3.0.txt

All levels of collaboration strongly encouraged.  Simply fork and go on github.com.


DESCRIPTION

This project started as a quick proof-of-concept of a real-time strategy for the open-souce 
Marketcetera trading platform.  The intention now is to mature the existing and introduce new, 
progressively more complex proof-of-concept units.  


INSTALLATION

Prerequisites:
- Maven
- Marketcetera
- Eclipse (optional)

Set-up:
- Extract the project
- cd <project-root>
- mvn install
- mvn package
- mvn eclipse:eclipse
- Eclipse | File -> Import -> Existing projects into workspace...


RUN A STRATEGY

For the example RealTimeMovingAverageSuggestions:
- Open RealTimeMovingAverageSuggestions.txt 
- Update createModule command to reflect the absolute path to RealTimeMovingAverageSuggestions.java 

Then, be sure to include <project-root>/target/openstrategies-org-1.0-SNAPSHOT.jar on the classpath
when invoking StrategyAgent.


CONTACT:

sfrancolla@gmail.com