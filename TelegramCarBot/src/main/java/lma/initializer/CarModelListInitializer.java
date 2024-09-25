package lma.initializer;

import lma.service.ScheduledService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarModelListInitializer implements ApplicationRunner {

    private final ScheduledService scheduledService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        scheduledService.updateCarAndModelList();
    }
}
