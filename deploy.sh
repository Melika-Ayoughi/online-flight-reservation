#!/bin/bash
mvn package -Dmaven.test.skip=true
# mvn package
mv ./target/online_flight_reservation.war /Library/Tomcat/webapps/