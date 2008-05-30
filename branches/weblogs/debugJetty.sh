export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4876,server=y,suspend=n -Xms256M -Xmx512m" 
mvn jetty:run-war

