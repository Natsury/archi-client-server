package com.bd;

import java.sql.PreparedStatement;
import java.util.Random;

public class InitData {

    public static void createData() {

        // Statement de préparation pour l'insertion de données
        String insertSQL = "INSERT INTO Article (Type, Prix, Stock) VALUES (?, ?, ?)";
        String[] types = {"Laptop", "Smartphone", "Clavier", "Souris", "Ecran", "Casque"};
        Random random = new Random();
        System.out.println("Starting data generation...");
        try {
            PreparedStatement pstmt = BD.getInstance().preparStatement(insertSQL);
            for (int i = 0; i < types.length ; i++) {
                    String type = types[i];
                    double prix = 10 + (1000 - 10) * random.nextDouble(); // Prix entre 10 et 1000
                    int stock = random.nextInt(100) + 1; // Stock entre 1 et 100
    
                    pstmt.setString(1, type);
                    pstmt.setDouble(2, prix);
                    pstmt.setInt(3, stock);
                    
                    pstmt.executeUpdate();
                    System.out.println("Article " + i + " inséré : " + type);
                }
    
                System.out.println("Generation completed successfully !"); 
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert data: " + e);
        }
    }
}
