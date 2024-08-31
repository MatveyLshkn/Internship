package lma.runner;

import jakarta.ws.rs.ext.ParamConverter;
import lma.parser.Parser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {


    private final Parser parser;

    @Override
    public void run(String... args) throws Exception {
        //parser.printAllBrands();
        //parser.printAllModelsByBrand(6L);
        //parser.printAllPostsByModel(6L, 2093L);
    }
}
