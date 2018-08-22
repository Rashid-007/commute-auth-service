# Commute Service - Commute Identity Service

This service offers jwt based authentication services (jwt based) with which the other resources can be accessed based on the account role

## Build
`mvn package`

## Local docker image build
 `mvn package docker:build "-Ddocker.image.tag=local"`

## Run
` mvn spring-boot:run`
