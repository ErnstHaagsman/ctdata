package net.ctdata.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.ctdata.common.Json.MapperSingleton;
import net.ctdata.common.Options.CliOptions;
import net.ctdata.common.Options.DefaultConfig;
import net.ctdata.common.Queue.RabbitMqConnection;
import net.ctdata.webapp.queue.QueueSingleton;
import net.ctdata.webapp.queuelistener.MyAddNodeResponseListener;
import net.ctdata.webapp.queuelistener.MyAddedNodeRequestListener;
import net.ctdata.webapp.queuelistener.MyHistoryResponseListener;
import net.ctdata.webapp.queuelistener.MyObservationListener;
import org.apache.commons.cli.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * Created by Nimesh on 11/17/2015.
 */
@SpringBootApplication
public class WebApp {


    public static void main(String[] args) throws URISyntaxException, KeyManagementException, TimeoutException, NoSuchAlgorithmException, IOException {
        Options options = CliOptions.getOptions();
        DefaultConfig configuration = CliOptions.readOptions(args,options, "java -jar WebApp.jar", DefaultConfig.class, true);

        // Just to make the compiler shut up.
        if(configuration == null) return;

        QueueSingleton.setUrl(configuration.getRabbitMqUrl());

        final RabbitMqConnection conn = QueueSingleton.getConnection();
        ApplicationContext ctx = SpringApplication.run(WebApp.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        conn.RegisterListener(new MyAddedNodeRequestListener(conn));
        conn.RegisterListener(new MyAddNodeResponseListener(conn));
        conn.RegisterListener(new MyObservationListener(conn));
        conn.RegisterListener(new MyHistoryResponseListener(conn));
    }

}
