package task.logic.daoimp;

import task.logic.model.Settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static Connection connection;
    private static Settings settings;

    static {
        try (InputStream input = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/mysql.jdbc.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            settings = new Settings(prop.getProperty("jdbc.url"), prop.getProperty("jdbc.username"), prop.getProperty("jdbc.password"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean createConnection() {
        try {
            connection = DriverManager.getConnection(settings.getLocation(), settings.getUser(), settings.getPassword());
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            if (!createConnection()) {
                System.out.println("Fail to connect");
            }
        }
        return connection;
    }


    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Fail to close connection");
        }
    }
}
