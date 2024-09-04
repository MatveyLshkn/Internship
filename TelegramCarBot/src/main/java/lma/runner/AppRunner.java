package lma.runner;

import lma.parser.PageParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {


    private final PageParser pageParser;

    @Override
    public void run(String... args) throws Exception {

    }
}
