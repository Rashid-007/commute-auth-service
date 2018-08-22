package com.commute.identity;

import com.commute.identity.cfg.SystemProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
@EnableConfigurationProperties(SystemProperties.class)
public class CommuteIdentityServiceApp {
    /**
     * Application starts
     * @param args
     */
    public static void main(final String[] args) throws UnknownHostException {
        final SpringApplication app = new SpringApplication(CommuteIdentityServiceApp.class);
        /**
         * Run the spring application and then get related env data.
         */
        final Environment env = app.run(args).getEnvironment();
        log.info("\n\t..................................................\n\t" +
                        "| Application {} is running:\n\t" +
                        "| URLs: \n\t" +
                        "| Local:\t\t http://127.0.0.1:{} \n\t" +
                        "| External:\t\t http://{}:{}",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port") +
                        "\n\t..................................................\n\t");

    }
}
