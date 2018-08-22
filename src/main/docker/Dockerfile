# To execute the shipped application, a java runtime is required
# Use an openjdk runtime by refering to image tag of openjdk on docker hub
FROM openjdk:8-jdk-alpine

RUN  apk update && apk upgrade && apk add netcat-openbsd

RUN mkdir -p /usr/local/commute-identity-service
ADD *.jar /usr/local/commute-identity-service/app.jar

RUN  ls -al /usr/local/commute-identity-service/

# ADD extracts the *.tar.gz and copies dockerize to the account commands folder
ADD dockerize-linux-amd64-v0.2.0.tar.gz /usr/local/bin

# Expose port on container for inter-container communication
EXPOSE 8080
EXPOSE 8000


# Start application with a source of entropy
ENTRYPOINT ["/bin/sh", "-c", "dockerize -wait tcp://$(getent hosts commute-identity-service-postgres | cut -d  ' ' -f 1):5432 -timeout 10m java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -Djava.security.egd=file:/dev/./urandom -jar /usr/local/commute-identity-service/app.jar --server.port=8080"]


#ADD run.sh run.sh
#RUN chmod +x run.sh
#CMD ./run.sh

