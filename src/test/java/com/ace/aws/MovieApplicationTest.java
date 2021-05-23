package com.ace.aws;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "/com/ace/aws/spring-h2-application-context-test.properties")
class MovieApplicationTest
{
    @Test
    void contextLoads()
    {
    }
}
