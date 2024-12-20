package Classes;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File databasesFile = new File("databases.json");
        
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Choose an existing database");
            System.out.println("2. Create a new database");
            System.out.println("3. Help Menu");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> handleExistingDatabase(scanner);
                case 2 -> handleNewDatabase(scanner);
                case 3 -> HelpMenu.displayMenuHelp(scanner); // Display the help menu
                case 4 -> {
                    System.out.println("Exiting program. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleExistingDatabase(Scanner scanner) {
        System.out.println("Enter database filename:");
        String filename = scanner.nextLine();
        File dbFile = new File(filename + ".json");
        
        if (!dbFile.exists()) {
            System.out.println("Database does not exist.");
            return;
        }

        SiteManager siteManager = new SiteManager(dbFile);
        siteManager.setEncryptionAlgorithm("Enigma");
        
        while (true) {
            System.out.println("\nSite Management Menu:");
            System.out.println("1. Add new site");
            System.out.println("2. Modify site");
            System.out.println("3. Delete site");
            System.out.println("4. View all sites");
            System.out.println("5. Return to main menu");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addSite(scanner, siteManager);
                case 2 -> modifySite(scanner, siteManager);
                case 3 -> deleteSite(scanner, siteManager);
                case 4 -> viewSites(siteManager);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleNewDatabase(Scanner scanner) {
        System.out.println("Enter new database filename:");
        String filename = scanner.nextLine();
        File dbFile = new File(filename + ".json");
        
        if (dbFile.exists()) {
            System.out.println("Database already exists.");
            return;
        }

        try {
            SiteManager siteManager = new SiteManager(dbFile);
            siteManager.setEncryptionAlgorithm("Enigma");
            System.out.println("Database created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating database: " + e.getMessage());
        }
    }

    private static void addSite(Scanner scanner, SiteManager siteManager) {
        System.out.println("Enter site name:");
        String siteName = scanner.nextLine();
        
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try {
            siteManager.addSite(siteName, username, password);
            System.out.println("Site added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding site: " + e.getMessage());
        }
    }

    private static void modifySite(Scanner scanner, SiteManager siteManager) {
        System.out.println("Enter site name to modify:");
        String siteName = scanner.nextLine();
        
        System.out.println("Enter new username (or press Enter to skip):");
        String newUsername = scanner.nextLine();
        
        System.out.println("Enter new password (or press Enter to skip):");
        String newPassword = scanner.nextLine();

        try {
            siteManager.modifySite(siteName, 
                                 newUsername.isEmpty() ? null : newUsername,
                                 newPassword.isEmpty() ? null : newPassword);
            System.out.println("Site modified successfully.");
        } catch (Exception e) {
            System.out.println("Error modifying site: " + e.getMessage());
        }
    }

    private static void deleteSite(Scanner scanner, SiteManager siteManager) {
        System.out.println("Enter site name to delete:");
        String siteName = scanner.nextLine();

        try {
            siteManager.deleteSite(siteName);
            System.out.println("Site deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting site: " + e.getMessage());
        }
    }

    private static void viewSites(SiteManager siteManager) {
        List<Map<String, String>> sites = siteManager.getSites();
        if (sites.isEmpty()) {
            System.out.println("No sites found.");
            return;
        }

        System.out.println("\nStored Sites:");
        for (Map<String, String> site : sites) {
            System.out.println("\nSite Name: " + site.get("siteName"));
            System.out.println("Username: " + site.get("username"));
            System.out.println("Password: " + site.get("password"));
        }
    }
}