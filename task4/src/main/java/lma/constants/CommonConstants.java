package lma.constants;


import lma.entity.description.AnalyticsEntityDescription;
import lma.entity.description.CarEntityDescription;
import lma.entity.description.CustomerEntityDescription;
import lma.entity.description.DiscountEntityDescription;
import lma.entity.description.EmployeeEntityDescription;
import lma.entity.description.EntityDescription;
import lma.entity.description.ModelEntityDescription;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommonConstants {

    public static final List<EntityDescription> AVAILABLE_ENTITIES;

    static {
        AVAILABLE_ENTITIES = new ArrayList<>();
        AVAILABLE_ENTITIES.add(new AnalyticsEntityDescription());
        AVAILABLE_ENTITIES.add(new CarEntityDescription());
        AVAILABLE_ENTITIES.add(new CustomerEntityDescription());
        AVAILABLE_ENTITIES.add(new DiscountEntityDescription());
        AVAILABLE_ENTITIES.add(new EmployeeEntityDescription());
        AVAILABLE_ENTITIES.add(new ModelEntityDescription());
    }
}
