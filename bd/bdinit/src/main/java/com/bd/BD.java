package com.bd;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class BD {

    private static BD instance;

    private static final Properties properties = new Properties();
    private Connection connection;

    public static BD getInstance() {
        if (instance == null) {
            instance = new BD();
        }
        return instance;
    }

    private BD() {
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

    public void initializeBD() {
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `Article` ("
                    + "  `Reference` int NOT NULL AUTO_INCREMENT,"
                    + "  `Type` varchar(25) NOT NULL,"
                    + "  `Prix` decimal(10,2) NOT NULL,"
                    + " `Stock` int NOT NULL,"
                    + "  PRIMARY KEY (`Reference`)"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `Facture` ("
                    + "  `Num_Facture` int NOT NULL AUTO_INCREMENT,"
                    + "  `Mode_paiement` VARCHAR(50),"
                    + "  `Date_fac` date,"
                    + "  `Prix_total` decimal(10,2) NOT NULL,"
                    + "  PRIMARY KEY (`Num_Facture`)"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `Panier` ("
                    + "  `Num_Facture` int NOT NULL,"
                    + "  `Reference` int NOT NULL,"
                    + "  `Quantite` int NOT NULL,"
                    + "  PRIMARY KEY (`Num_Facture`, `Reference`),"
                    + "  FOREIGN KEY (`Num_Facture`) REFERENCES `Facture`(`Num_Facture`) ON DELETE CASCADE,"
                    + "  FOREIGN KEY (`Reference`) REFERENCES `Article`(`Reference`) ON DELETE CASCADE"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

            System.out.println("Database initialized successfully !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement preparStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to prepare statement: " + sql);
        }
    }
}
