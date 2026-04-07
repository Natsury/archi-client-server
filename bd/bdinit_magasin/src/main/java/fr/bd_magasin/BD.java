package fr.bd_magasin;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class BD {

    private static BD instance;

    private static final Properties properties = new Properties();
    private Connection connectionMagasin;
    private Connection connectionSiege;

    public static BD getInstance() {
        if (instance == null) {
            instance = new BD();
        }
        return instance;
    }

    private BD() {
        try {
            System.out.println("Connecting to the Magasin database...");
            connectMagasin();
            System.out.println("Connecting to the Siege database...");
            connectSiege(); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database: " + e);
        }

        initializeBD();
        updateFromSiegeData();
    }

    private void connectMagasin() {
        int tries = 10;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.env");

        while (tries > 0 && connectionMagasin == null) {
            try {
                properties.load(inputStream);
                Class.forName("com.mysql.cj.jdbc.Driver");
                connectionMagasin = DriverManager.getConnection("jdbc:mysql://"
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
        if (connectionMagasin == null) {
            throw new RuntimeException("Failed to connect to the database after multiple attempts.");
        }
    }

    private void connectSiege() {
        int tries = 10;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.env");

        while (tries > 0 && connectionSiege == null) {
            try {
                properties.load(inputStream);
                Class.forName("com.mysql.cj.jdbc.Driver");
                connectionSiege = DriverManager.getConnection("jdbc:mysql://"
                        + properties.getProperty("MYSQL_HOST_SIEGE")
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
        if (connectionSiege == null) {
            throw new RuntimeException("Failed to connect to the database after multiple attempts.");
        }
    }
    public void disconnect() {
        try {
            if (connectionMagasin != null && !connectionMagasin.isClosed()) {
                connectionMagasin.close();
                System.out.print("Connection to the database closed successfully!");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close the database connection: " + e);
        }
    }

    public void initializeBD() {
        try {
            connectionMagasin.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `Article` ("
                    + "  `Reference` int NOT NULL AUTO_INCREMENT,"
                    + "  `Type` varchar(25) NOT NULL,"
                    + "  `Prix` decimal(10,2) NOT NULL,"
                    + " `Stock` int NOT NULL,"
                    + "  PRIMARY KEY (`Reference`)"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

            connectionMagasin.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `Facture` ("
                    + "  `Num_Facture` int NOT NULL AUTO_INCREMENT,"
                    + "  `Mode_paiement` VARCHAR(50),"
                    + "  `Date_fac` date,"
                    + "  `Prix_total` decimal(10,2) NOT NULL,"
                    + "  PRIMARY KEY (`Num_Facture`)"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

            connectionMagasin.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `Panier` ("
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

    private void updateFromSiegeData() {
        try {
            String query = "SELECT * FROM Article";
            PreparedStatement stmt = connectionSiege.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

                while (resultSet.next()) {
                    int reference = resultSet.getInt("Reference");
                    String type = resultSet.getString("Type");
                    double prix = resultSet.getDouble("Prix");
                    int stock = resultSet.getInt("Stock");
    
                    // Update local database with the retrieved data
                    String updateQuery = "INSERT INTO Article (Reference, Type, Prix, Stock) VALUES (?, ?, ?, ?) "
                            + "ON DUPLICATE KEY UPDATE Type = VALUES(Type), Prix = VALUES(Prix), Stock = VALUES(Stock)";
                    PreparedStatement updateStmt = connectionMagasin.prepareStatement(updateQuery);
                    updateStmt.setInt(1, reference);
                    updateStmt.setString(2, type);
                    updateStmt.setDouble(3, prix);
                    updateStmt.setInt(4, stock);
                    updateStmt.executeUpdate();
                }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update data from siege: " + e);
        }
    }

    public PreparedStatement preparStatement(String sql) {
        try {
            return connectionMagasin.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to prepare statement: " + sql);
        }
    }
}
