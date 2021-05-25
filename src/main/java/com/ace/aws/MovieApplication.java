package com.ace.aws;

import com.ace.aws.common.SystemPropertyBuilder;
import org.apache.commons.cli.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieApplication
{
    public static void main(String[] args) throws ParseException
    {
        if (args.length > 0)
        {
            new SystemPropertyBuilder(args)
                    .withProperty("hostname", "SYSTEM_HOST_NAME")
                    .withProperty("jdbcUrl", "JDBC_URL")
                    .withProperty("dbUsername", "DB_USER")
                    .withProperty("dbPassword", "DB_PASS")
                    .build();
        }

        if (System.getProperty("JDBC_URL") != null)
        {
            SpringApplication.run(MovieApplication.class, args);
        }
        else
        {
            System.out.println("========================================");
            System.out.println("APPLICATION PROPERTIES NOT SET");
            System.out.println("--hostname localhost --jdbcUrl jdbc:postgresql://localhost:5432/moviedb --dbUsername app1 --dbPassword password");
            System.out.println("========================================");
        }
    }
}
