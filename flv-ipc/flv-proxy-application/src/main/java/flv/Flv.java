package flv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Flv implements CommandLineRunner {

    public static void main(String[] arguments) {

        SpringApplication.run(Flv.class, arguments);

    }

    @Override
    public void run(String... args) throws Exception {
//        Thread.currentThread().join();
    }




}
