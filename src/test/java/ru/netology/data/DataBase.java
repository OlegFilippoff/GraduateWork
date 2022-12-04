package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {

    private static String url = System.getProperty("db.url");
    private static String user = System.getProperty("db.user");
    private static String password = System.getProperty("db.password");

    @SneakyThrows
    public static void clearTables() {
        QueryRunner runner = new QueryRunner();
        String deleteCredit = "DELETE FROM credit_request_entity";
        String deleteOrder = "DELETE FROM order_entity";
        String deletePayment = "DELETE FROM payment_entity";

        try  (
                Connection connection = DriverManager.getConnection(
                        url, user, password)

        ) {
            runner.update(connection, deleteCredit);
            runner.update(connection, deleteOrder);
            runner.update(connection, deletePayment);
        }

    }

    @SneakyThrows
    public static String getStatus(String status) {
        QueryRunner runner = new QueryRunner();

        try (
                Connection connection = DriverManager.getConnection(
                        url, user, password)

        ) {
            String result = runner.query(connection, status, new ScalarHandler<>());
            return result;
        }
    }

    @SneakyThrows
    public static String getStatusPayment() {
        String statusSQL = "SELECT status FROM payment_entity ORDER BY created ASC LIMIT 1";
        return getStatus(statusSQL);
    }

    @SneakyThrows
    public static String getStatusCredit() {
        String statusSQL = "SELECT status FROM credit_request_entity ORDER BY created ASC LIMIT 1";
        return getStatus(statusSQL);
    }
}
