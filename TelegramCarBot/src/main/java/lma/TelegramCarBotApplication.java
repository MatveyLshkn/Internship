package lma;

import lma.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@EnableAsync
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class TelegramCarBotApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TelegramCarBotApplication.class, args);
    }
}
