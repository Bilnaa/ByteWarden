package Classes;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        // Initialize the scanner for user input
        Scanner scanner = new Scanner(System.in);
        // Load the database file to manage existing databases
        File databasesFile = new File("databases.json");
        DatabasesManager dbManager = new DatabasesManager(databasesFile);

        // Display the main menu options
        System.out.println("Welcome to the Encryption/Decryption Program");
        System.out.println("Choose an option:");
        System.out.println("1. Choose an existing database");
        System.out.println("2. Create a new database");
        System.out.println("3. Help Menu");

        // Read the user's choice for the menu
        int dbChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt

        // Handle the case where the user wants to connect to an existing database
        if (dbChoice == 1) {
            System.out.println("Enter the name of the database:");
            String dbName = scanner.nextLine(); // Read the database name
            System.out.println("Enter the password:");
            String inputPassword = scanner.nextLine(); // Read the database password

            // Verify the database credentials
            if (dbManager.verifyDatabase(dbName, inputPassword)) {
                System.out.println("Successfully connected to the database: " + dbName);
                // Initialize the SiteManager to manage sites within the database
                SiteManager siteManager = new SiteManager(new File(dbName + ".json"));
                siteManager.manageSites(scanner); // Begin managing sites
            } else {
                System.out.println("Incorrect database name or password."); // Error message for invalid credentials
            }
        }
        // Handle the case where the user wants to create a new database
        else if (dbChoice == 2) {
            System.out.println("Enter the name of the new database:");
            String dbName = scanner.nextLine(); // Read the new database name
            System.out.println("Choose a password option:");
            System.out.println("1. Enter a custom password");
            System.out.println("2. Generate a random password");

            // Read the user's choice for password type
            int passwordChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt

            // Determine the password based on the user's choice
            String password = passwordChoice == 1
                    ? scanner.nextLine() // Custom password entered by user
                    : PasswordUtils.generateRandomPassword(12); // Generate a random password

            System.out.println("Generated password: " + password);
            // Create the new database with the given name and password
            dbManager.createDatabase(dbName, password);
            // Initialize the SiteManager for the new database
            SiteManager siteManager = new SiteManager(new File(dbName + ".json"));
            siteManager.manageSites(scanner); // Begin managing sites
        }
        // Handle the case where the user wants to access the help menu
        else if (dbChoice == 3){
            HelpMenu.displayMenuHelp(scanner); // Display the help menu
        }
        // Handle invalid choices
        else {
            System.out.println("Invalid choice."); // Error message for invalid input
        }
    }
}
