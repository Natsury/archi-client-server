package com.bd;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Starting the application...");
        // Initialize the database connection and setup
        BD bd = BD.getInstance();
        bd.initializeBD();
        // Create initial data in the database
        InitData.createData();
        System.out.println("Application started successfully!");
    }
}
