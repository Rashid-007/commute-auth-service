package com.commute.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
@EnableAuthorizationServer // Adds several oauth endpoints used internally (REST bases)
@Slf4j
public class AuthApp {
    /**
     * Application starts
     * @param args
     */
    public static void main(final String[] args) throws UnknownHostException {
        final SpringApplication app = new SpringApplication(AuthApp.class);
        /**
         * Run the spring application and then get related env data.
         */
        final Environment env = app.run(args).getEnvironment();
        log.info("\n\t..................................................\n\t" +
                        "| Application {} is running:\n\t" +
                        "| URLs: \n\t" +
                        "| Local:\t\t http://127.0.0.1:{} \n\t" +
                        "| External:\t\t http://{}:{}",
                env.getProperty("application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port") +
                        "\n\t..................................................\n\t");

    }
}
