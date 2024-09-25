package lma.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {

    public static final String POST_KAFKA_TOPIC_NAME = "post";

    public static final Integer KAFKA_PARTITION_COUNT = 1;

    public static final Integer KAFKA_REPLICA_COUNT = 1;

    public static final String KAFKA_POST_GROUP_ID = "postGroup";

    public static final String KAFKA_TRUSTED_PACKAGES = "lma.dto";

    public static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG_NAME = "${spring.kafka.bootstrap-servers}";

    public static final String TELEGRAM_POST_MESSAGE_FORMAT = """
            URL: %s
            
            Info: 
            
            %s
            """;

    public static final String THREAD_POLL_TASK_EXECUTOR_NAME = "threadPollTaskExecutor";

    public static final String SEND_MESSAGE_VIA_TG_API_URL_FORMAT =
            "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

    public static final String TELEGRAM_SEND_MESSAGE_EXCEPTION_MESSAGE = "Error sending message to user!";

}
