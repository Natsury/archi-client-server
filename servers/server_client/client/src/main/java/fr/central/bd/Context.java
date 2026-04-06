package fr.central.bd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class Context {

    private static Context instance;
    private Connection connection;

    private static final Properties properties = new Properties();


    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    private Context() {
        try {
            connect();
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database: " + e);
        }
    }

    private void connect() {
        int tries = 10;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.env");

        while (tries > 0 && connection == null) {
            try {
                properties.load(inputStream);
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://"
                        + properties.getProperty("MYSQL_HOST")
                        + ":" + properties.getProperty("MYSQL_PORT")
                        + "/" + properties.getProperty("MYSQL_DATABASE"),
                        properties.getProperty("MYSQL_USER"),
                        properties.getProperty("MYSQL_PASSWORD"));
                System.out.println("Connection to the database established successfully!");
            } catch (SQLException e) {
                tries--;
                System.out.println("Connection failed, retrying... (" + tries + " left)");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {}
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        if (connection == null) {
            throw new RuntimeException("Failed to connect to the database after multiple attempts.");
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.print("Connection to the database closed successfully!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close the database connection: " + e);
        }
    }

    public ResultSet GetStatement(String sql) {
        try {
            if (connection != null && !connection.isClosed()) {
                PreparedStatement stmnt = connection.prepareStatement(sql);
                return stmnt.executeQuery();
            } else {
                throw new RuntimeException("No active database connection.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create a statement: " + e);
        }
    }

    public PreparedStatement ExecuteUpdate(String sql) {
        try {
            if (connection != null && !connection.isClosed()) {
                PreparedStatement stmnt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmnt.executeUpdate();
                return stmnt;
            } else {
                throw new RuntimeException("No active database connection.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create a statement: " + e);
        }
    }
}
