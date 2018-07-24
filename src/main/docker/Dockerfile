# To execute the shipped application, a java runtime is required
# Use an openjdk runtime by refering to image tag of openjdk on docker hub

FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/commute-auth-service
ADD *.jar /usr/local/commute-auth-service/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh

