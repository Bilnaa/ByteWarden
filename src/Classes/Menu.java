package Classes;

import java.io.File;
import java.util.Scanner;

public class Menu {
    // Main function, starting point of the program
    public static void main(String[] args) {
        // Create a scanner object for user input
        Scanner scanner = new Scanner(System.in);
        // File where the database information is stored
        File databasesFile = new File("databases.json");
        // Initialize the database manager
        DatabasesManager dbManager = new DatabasesManager(databasesFile);

        // Display a welcome message
        System.out.println("Welcome to the Encryption/Decryption Program");
        // Start an infinite loop to show the main menu
        while (true) {
            // Display the options in the main menu
            System.out.println("Choose an option:");
            System.out.println("1. Choose an existing database");
            System.out.println("2. Create a new database");
            System.out.println("3. Exit");
            // Read user choice
            int dbChoice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            // Process the user choice using a switch statement
            switch (dbChoice) {
                case 1 -> handleExistingDatabase(scanner, dbManager); // Handle selecting an existing database
                case 2 -> handleNewDatabase(scanner, dbManager); // Handle creating a new database
                case 3 -> { // Exit the program
                    System.out.println("Exiting program. Goodbye!");
                    return; // Exit the main method and the program
                }
                default -> System.out.println("Invalid choice. Please try again."); // Invalid choice
            }
        }
    }

    // Function to handle the case when the user wants to choose an existing database
    private static void handleExistingDatabase(Scanner scanner, DatabasesManager dbManager) {
        // Ask for the name of the database
        System.out.println("Enter the name of the database:");
        String dbName = scanner.nextLine();
        // Ask for the password of the database
        System.out.println("Enter the password:");
        String inputPassword = scanner.nextLine();

        // Check if the database name and password are correct
        if (dbManager.verifyDatabase(dbName, inputPassword)) {
            System.out.println("Successfully connected to the database: " + dbName);
            // Create a SiteManager for managing the database's sites
            SiteManager siteManager = new SiteManager(new File(dbName + ".json"));
            // Display the main action menu for the site manager
            mainActionMenu(scanner, siteManager);
        } else {
            // Inform the user if the credentials are incorrect
            System.out.println("Incorrect database name or password.");
        }
    }

    // Function to handle the case when the user wants to create a new database
    private static void handleNewDatabase(Scanner scanner, DatabasesManager dbManager) {
        // Ask for the name of the new database
        System.out.println("Enter the name of the new database:");
        String dbName = scanner.nextLine();

        // Ask the user to choose a password option
        System.out.println("Choose a password option:");
        System.out.println("1. Enter a custom password");
        System.out.println("2. Generate a random password");
        // Read user choice for password generation
        int passwordChoice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        // Generate or ask for a custom password based on the user's choice
        String password = switch (passwordChoice) {
            case 1 -> {
                System.out.println("Enter your custom password:");
                yield scanner.nextLine(); // Return the custom password
            }
            case 2 -> {
                // Generate a random password and display it
                String generatedPassword = PasswordUtils.generateRandomPassword(12);
                System.out.println("Generated password: " + generatedPassword);
                yield generatedPassword; // Return the generated password
            }
            default -> {
                System.out.println("Invalid choice. Defaulting to generated password.");
                yield PasswordUtils.generateRandomPassword(12); // Default to random password
            }
        };

        // Create the new database with the chosen name and password
        dbManager.createDatabase(dbName, password);

        // Create a SiteManager for the new database
        SiteManager siteManager = new SiteManager(new File(dbName + ".json"));

        // Display the main action menu for the site manager
        mainActionMenu(scanner, siteManager);
    }

    // Function to display the main action menu for managing sites and performing actions
    private static void mainActionMenu(Scanner scanner, SiteManager siteManager) {
        // Ensure an encryption algorithm is selected (commented out here)
        // siteManager.chooseEncryptionAlgorithm(scanner);

        // Start an infinite loop to show the action menu
        while (true) {
            // Display the options for actions in the site manager
            System.out.println("Choose an action:");
            System.out.println("1. Manage sites");
            System.out.println("2. Decrypt a password");
            System.out.println("3. Back to main menu");
            // Read user choice for the action
            int action = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            // Process the action choice using a switch statement
            switch (action) {
                case 1 -> siteManager.manageSites(scanner, siteManager.encryptionAlgorithm); // Manage sites, passing the encryption algorithm
                case 2 -> { // Decrypt a password
                    siteManager.chooseEncryptionAlgorithm(scanner); // Choose the encryption algorithm
                    siteManager.handleEncryptionDecryption(scanner); // Handle encryption/decryption process
                }
                case 3 -> { // Return to the main menu
                    System.out.println("Returning to the main menu...");
                    return; // Exit the current loop and return to the main menu
                }
                default -> System.out.println("Invalid choice. Please try again."); // Invalid choice
            }
        }
    }
}
