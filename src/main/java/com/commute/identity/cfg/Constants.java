package com.commute.identity.cfg;

public class Constants {
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    public static final String SPRING_PROFILE_NO_SWAGGER = "no-swagger";
    public static final String SPRING_PROFILE_NO_EUREKA = "no-eureka";
    public static final String SPRING_PROFILE_NO_RABBIT_MQ = "no-rabbitmq";
    public static final String SPRING_PROFILE_TEST =
            "test," + SPRING_PROFILE_NO_SWAGGER + "," + SPRING_PROFILE_NO_EUREKA + "," + SPRING_PROFILE_NO_RABBIT_MQ;

    private Constants() {}
}
