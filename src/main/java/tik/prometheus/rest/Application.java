package tik.prometheus.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@SpringBootApplication
@Controller
@EnableFeignClients
@EnableEurekaClient
public class Application {

    public static void main(String[] args) {
        List<List<Integer>> l = List.of(List.of(1, 2, 3));
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/redoc", method = RequestMethod.GET)
    public String reDocs() {
        return "redoc.html";
    }
}
