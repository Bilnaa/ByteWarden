package Classes;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File databasesFile = new File("databases.json");
        DatabasesManager dbManager = new DatabasesManager(databasesFile);

        System.out.println("Welcome to the Encryption/Decryption Program");
        System.out.println("Choose an option:");
        System.out.println("1. Choose an existing database");
        System.out.println("2. Create a new database");
        int dbChoice = scanner.nextInt();
        scanner.nextLine();

        if (dbChoice == 1) {
            System.out.println("Enter the name of the database:");
            String dbName = scanner.nextLine();
            System.out.println("Enter the password:");
            String inputPassword = scanner.nextLine();

            if (dbManager.verifyDatabase(dbName, inputPassword)) {
                System.out.println("Successfully connected to the database: " + dbName);
                SiteManager siteManager = new SiteManager(new File(dbName + ".json"));
                siteManager.manageSites(scanner);
            } else {
                System.out.println("Incorrect database name or password.");
            }
        } else if (dbChoice == 2) {
            System.out.println("Enter the name of the new database:");
            String dbName = scanner.nextLine();
            System.out.println("Choose a password option:");
            System.out.println("1. Enter a custom password");
            System.out.println("2. Generate a random password");
            int passwordChoice = scanner.nextInt();
            scanner.nextLine();

            String password = passwordChoice == 1
                    ? scanner.nextLine()
                    : PasswordUtils.generateRandomPassword(12);

            System.out.println("Generated password: " + password);
            dbManager.createDatabase(dbName, password);
            SiteManager siteManager = new SiteManager(new File(dbName + ".json"));
            siteManager.manageSites(scanner);
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
