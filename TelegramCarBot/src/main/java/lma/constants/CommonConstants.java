package lma.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {

    public static final String POST_KAFKA_TOPIC_NAME = "post";

    public static final Integer KAFKA_PARTITION_COUNT = 1;

    public static final Integer KAFKA_REPLICA_COUNT = 1;

    public static final String KAFKA_POST_GROUP_ID = "post-group";

    public static final String KAFKA_BOOTSTRAP_SERVERS_CONFIG_NAME = "${spring.kafka.bootstrap-servers}";

    public static final String TELEGRAM_POST_MESSAGE = """
            URL: %s
            Info: 
            %s
            """;

    public static final String POST_INFO_FORMAT = """
            Price(usd): %f
            Description: 
            %s
            """;

    public static final String AV_BY_JSON_TAG = "__NEXT_DATA__";

    public static final Integer INITIAL_CAR_UPDATE_DELAY = 1000;

    public static final String CAR_UPDATE_CRON_EXPRESSION = "0 0 0 * * SUN";

    public static final String OUTDATED_POSTS_CRON_EXPRESSION = "0 0 0 * * *";

    public static final Integer CHECK_FOR_NEW_POSTS_RATE = 1000 * 60 * 60 * 3;

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

}
