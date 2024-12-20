package Classes;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        // Initialize scanner for user input from console
        Scanner scanner = new Scanner(System.in);
        // Create a file reference for the databases configuration
        File databasesFile = new File("databases.json");
        // Initialize the database manager with the configuration file
        DatabasesManager dbManager = new DatabasesManager(databasesFile);
        
        // Main program loop
        while (true) {
            // Display main menu options
            System.out.println("\nWelcome to the Encryption/Decryption Program");
            System.out.println("Choose an option:");
            System.out.println("1. Choose an existing database");
            System.out.println("2. Create a new database");
            System.out.println("3. Help Menu");
            System.out.println("4. Exit");
            
            // Get user's choice and consume newline
            int choice = scanner.nextInt();
            scanner.nextLine(); // Prevent scanner buffer issues

            // Process user's choice using switch expression
            switch (choice) {
                case 1 -> handleExistingDatabase(scanner, dbManager);
                case 2 -> handleNewDatabase(scanner, dbManager);
                case 3 -> HelpMenu.displayMenuHelp(scanner);
                case 4 -> {
                    System.out.println("Exiting program. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Handles the process of connecting to an existing database
     */
    private static void handleExistingDatabase(Scanner scanner, DatabasesManager dbManager) {
        // Get database credentials from user
        System.out.println("Enter the name of the database:");
        String dbName = scanner.nextLine();
        System.out.println("Enter the password:");
        String inputPassword = scanner.nextLine();

        // Verify database credentials and proceed if valid
        if (dbManager.verifyDatabase(dbName, inputPassword)) {
            System.out.println("Successfully connected to the database: " + dbName);
            // Initialize site manager with the selected database
            SiteManager siteManager = new SiteManager(new File(dbName + ".json"));
            // Set the encryption algorithm (can be modified for different algorithms)
            siteManager.setEncryptionAlgorithm("Enigma");
            // Enter the site management menu
            manageSites(scanner, siteManager);
        } else {
            System.out.println("Incorrect database name or password.");
        }
    }

    /**
     * Handles the process of creating a new database
     */
    private static void handleNewDatabase(Scanner scanner, DatabasesManager dbManager) {
        // Get new database name from user
        System.out.println("Enter the name of the new database:");
        String dbName = scanner.nextLine();
        
        // Present password options
        System.out.println("Choose a password option:");
        System.out.println("1. Enter a custom password");
        System.out.println("2. Generate a random password");

        // Handle password choice
        int passwordChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Determine password based on user's choice
        String password = passwordChoice == 1
                ? scanner.nextLine() // User enters custom password
                : PasswordUtils.generateRandomPassword(12); // Generate random password

        System.out.println("Generated password: " + password);
        // Create new database with chosen name and password
        dbManager.createDatabase(dbName, password);
        
        // Initialize site manager for the new database
        SiteManager siteManager = new SiteManager(new File(dbName + ".json"));
        siteManager.setEncryptionAlgorithm("Enigma");
        System.out.println("Database created successfully.");
        // Enter the site management menu
        manageSites(scanner, siteManager);
    }

    /**
     * Manages the site operations menu (add, modify, delete, view)
     */
    private static void manageSites(Scanner scanner, SiteManager siteManager) {
        while (true) {
            // Display site management options
            System.out.println("\nSite Management Menu:");
            System.out.println("1. Add new site");
            System.out.println("2. Modify site");
            System.out.println("3. Delete site");
            System.out.println("4. View all sites");
            System.out.println("5. Return to main menu");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Process user's choice
            switch (choice) {
                case 1 -> addSite(scanner, siteManager);
                case 2 -> modifySite(scanner, siteManager);
                case 3 -> deleteSite(scanner, siteManager);
                case 4 -> viewSites(siteManager);
                case 5 -> {
                    return; // Return to main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Handles adding a new site to the database
     */
    private static void addSite(Scanner scanner, SiteManager siteManager) {
        // Collect site information
        System.out.println("Enter site name:");
        String siteName = scanner.nextLine();
        
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        // Attempt to add the site with error handling
        try {
            siteManager.addSite(siteName, username, password);
            System.out.println("Site added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding site: " + e.getMessage());
        }
    }

    /**
     * Handles modifying an existing site in the database
     */
    private static void modifySite(Scanner scanner, SiteManager siteManager) {
        // Get site to modify
        System.out.println("Enter site name to modify:");
        String siteName = scanner.nextLine();
        
        // Get new credentials (optional)
        System.out.println("Enter new username (or press Enter to skip):");
        String newUsername = scanner.nextLine();
        
        System.out.println("Enter new password (or press Enter to skip):");
        String newPassword = scanner.nextLine();

        // Attempt to modify the site with error handling
        try {
            siteManager.modifySite(siteName, 
                                 newUsername.isEmpty() ? null : newUsername,
                                 newPassword.isEmpty() ? null : newPassword);
            System.out.println("Site modified successfully.");
        } catch (Exception e) {
            System.out.println("Error modifying site: " + e.getMessage());
        }
    }

    /**
     * Handles deleting a site from the database
     */
    private static void deleteSite(Scanner scanner, SiteManager siteManager) {
        // Get site to delete
        System.out.println("Enter site name to delete:");
        String siteName = scanner.nextLine();

        // Attempt to delete the site with error handling
        try {
            siteManager.deleteSite(siteName);
            System.out.println("Site deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting site: " + e.getMessage());
        }
    }

    /**
     * Displays all sites stored in the database
     */
    private static void viewSites(SiteManager siteManager) {
        // Get all sites from the database
        List<Map<String, String>> sites = siteManager.getSites();
        
        // Check if there are any sites to display
        if (sites.isEmpty()) {
            System.out.println("No sites found.");
            return;
        }

        // Display all sites and their information
        System.out.println("\nStored Sites:");
        for (Map<String, String> site : sites) {
            System.out.println("\nSite Name: " + site.get("siteName"));
            System.out.println("Username: " + site.get("username"));
            System.out.println("Password: " + site.get("password"));
        }
    }
}