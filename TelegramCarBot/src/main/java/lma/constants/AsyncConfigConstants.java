package lma.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AsyncConfigConstants {

    public static final int CORE_POOL_SIZE = 5;

    public static final int MAX_POOL_SIZE = 10;

    public static final int QUEUE_CAPACITY = 100;

    public static final boolean WAIT_FOR_TASKS_TO_SHUTDOWN = true;

    public static final int AWAIT_TERMINATION_SECONDS = 60;
}
