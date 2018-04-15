package boyi.battleship.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "boyi.battleship")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}