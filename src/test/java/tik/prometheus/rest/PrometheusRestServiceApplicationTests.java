package tik.prometheus.rest;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;

class PrometheusRestServiceApplicationTests {


    @Test
    void runParallel() throws InterruptedException {
        LosCaller t1 = new LosCaller();
        LosCaller t2 = new LosCaller();

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    static class LosCaller extends Thread {
        @Override
        public void run() {
            RestTemplate client = new RestTemplate();
            ResponseEntity<HashMap> res = client.getForEntity("http://127.0.0.1:8001/api/v2/test-service?service=gateway-service&path=workflow/", HashMap.class);
            String v = res.getHeaders().get("x-process-time").toString();
            System.out.println(v);
        }
    }
}
