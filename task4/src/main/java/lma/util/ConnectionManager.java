package lma.util;

import lma.exception.ConnectionPoolException;
import lma.exception.NoAvailableConnectionsException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static lma.constants.DatabasePropertiesConstants.PASSWORD_PROPERTY_KEY;
import static lma.constants.DatabasePropertiesConstants.URL_PROPERTY_KEY;
import static lma.constants.DatabasePropertiesConstants.USER_PROPERTY_KEY;
import static lma.constants.ExceptionConstants.CONNECTION_POOL_EXCEPTION_MESSAGE;
import static lma.constants.ExceptionConstants.NO_AVAILABLE_CONNECTIONS_EXCEPTION_MESSAGE;

@UtilityClass
public class ConnectionManager {

    private static final Integer POOL_SIZE = 10;

    private static final String CLOSE_METHOD_NAME = "close";

    private static Queue<Connection> pool;

    private static List<Connection> sourceConnections;

    static {
        try {
            initConnectionPool();
        } catch (SQLException e) {
            throw new ConnectionPoolException(CONNECTION_POOL_EXCEPTION_MESSAGE);
        }
    }

    private static void initConnectionPool() throws SQLException {
        pool = new LinkedList<>();
        sourceConnections = new ArrayList<>(POOL_SIZE);

        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = open();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals(CLOSE_METHOD_NAME)
                            ? pool.add((Connection) proxy)
                            : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    public static Connection get() {
        if (pool.isEmpty()) {
            throw new NoAvailableConnectionsException(NO_AVAILABLE_CONNECTIONS_EXCEPTION_MESSAGE);
        }
        return pool.poll();
    }

    private static Connection open() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.get(URL_PROPERTY_KEY),
                PropertiesUtil.get(USER_PROPERTY_KEY),
                PropertiesUtil.get(PASSWORD_PROPERTY_KEY)
        );
    }

    public static void closePool() throws SQLException {
        for (Connection sourceConnection : sourceConnections) {
            sourceConnection.close();
        }
    }

    public static int poolSize() {
        return pool.size();
    }
}