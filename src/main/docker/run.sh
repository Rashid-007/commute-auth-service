#!/bin/sh
getPort() {
    echo $1 | cut -d : -f 3 | xargs basename
}

echo "********************************************************"
echo "Waiting for the service discovery to start on port $(getPort $COMMUTESERVICEDISCOVERY_PORT)"
echo "********************************************************"
while ! `nc -z commuteservicediscovery  $(getPort $COMMUTESERVICEDISCOVERY_PORT)`; do sleep 3; done
echo "******* service discovery  has started"


echo "********************************************************"
echo "Starting Authentication Service                           "
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT   \
     -Deureka.client.serviceUrl.defaultZone=$COMMUTESERVICEDISCOVERY_URI             \
     -Dspring.profiles.active=$PROFILE                                   \
     -jar /usr/local/commute-identity-service/*.jar
