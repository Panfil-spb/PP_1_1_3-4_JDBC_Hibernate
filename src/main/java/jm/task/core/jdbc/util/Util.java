package jm.task.core.jdbc.util;

import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private Connection connection;
    private static String URL = "jdbc:mysql://localhost:3306/mydb";
    private static String LOGIN = "root";
    private static String PASSWORD = "root";

    public Util() {

    }

    public Connection getConnection() {
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
        }
        return connection;
    }

}
