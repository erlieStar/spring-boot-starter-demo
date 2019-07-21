package com.st.teststarter;

import com.st.demoservice.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStarterApplicationTests {

    @Autowired
    DemoService demoService;

    @Test
    public void contextLoads() {
        String str = demoService.sayHello();
        // hello, 127.0.0.1 8080
        System.out.println(str);
    }
}
