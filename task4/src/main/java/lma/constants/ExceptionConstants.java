package lma.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionConstants {
    public static final String WRONG_ENUM_NAME_EXCEPTION_MESSAGE  = "Wrong enum name: %s";

    public static final String DATABASE_EXCEPTION_MESSAGE = "Database exception: %s";

    public static final String MODEL_NOT_FOUND_EXCEPTION_MESSAGE = "No model with id %d found";

    public static final String EMPLOYEE_NOT_FOUND_EXCEPTION_MESSAGE = "No employee with id %d found";

    public static final String ANALYTICS_NOT_FOUND_EXCEPTION_MESSAGE = "No analytics with id %d found";

    public static final String CAR_NOT_FOUND_EXCEPTION_MESSAGE = "No car with id %d found";

    public static final String CUSTOMER_NOT_FOUND_EXCEPTION_MESSAGE = "No customer with id %d found";

    public static final String DISCOUNT_NOT_FOUND_EXCEPTION_MESSAGE = "No discount with id %d found";

    public static final String CONNECTION_POOL_EXCEPTION_MESSAGE = "Failed to initialize connection pool";

    public static final String NO_AVAILABLE_CONNECTIONS_EXCEPTION_MESSAGE = "No available connections found";

    public static final String NO_SUCH_ENTITY_EXCEPTION_MESSAGE = "No such entity: %s";

    public static final String RECORD_NOT_FOUND_EXCEPTION_MESSAGE = "No record with id %d in table %s found";
}
