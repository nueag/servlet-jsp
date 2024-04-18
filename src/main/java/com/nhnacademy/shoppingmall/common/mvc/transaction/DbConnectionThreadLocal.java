package com.nhnacademy.shoppingmall.common.mvc.transaction;

import com.nhnacademy.shoppingmall.common.util.DbUtils;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DbConnectionThreadLocal {
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> sqlErrorThreadLocal = ThreadLocal.withInitial(() -> false);

    public static void initialize() {

        try {
            Connection connection = DbUtils.getDataSource().getConnection();

            connection.setTransactionIsolation(connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            log.debug("connection setting: {}", connection);
            connectionThreadLocal.set(connection);
        } catch (SQLException e) {
            log.error("Error initializing database connection: {}", e.getMessage());
            throw new RuntimeException("DbConnectionThreadLocal initialize error!");
        }

    }

    public static Connection getConnection() {
        return connectionThreadLocal.get();
    }

    public static void setSqlError(boolean sqlError) {
        sqlErrorThreadLocal.set(sqlError);
    }

    public static boolean getSqlError() {
        return sqlErrorThreadLocal.get();
    }

    public static void reset() {
        Connection connection = connectionThreadLocal.get();
        try {
            if (getSqlError()) {
                connection.rollback();
            } else {
                connection.commit();
            }
            if (connection != null) {
                connection.close();
            }
            connectionThreadLocal.remove();
        } catch (SQLException e) {
            log.error("Error resetting database connection: {}", e.getMessage());
            throw new RuntimeException("DbConnectionThreadLocal reset error!");
        }

    }
}
