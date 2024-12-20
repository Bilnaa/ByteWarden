package Classes;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Menu {
    private static final String[] LOGO_LINES = {
        "________      ___    ___ _________  _______   ___       __   ________  ________  ________  _______   ________      ",
        "|\\   __  \\    |\\  \\  /  /|\\___   ___\\\\  ___ \\ |\\  \\     |\\  \\|\\   __  \\|\\   __  \\|\\   ___ \\|\\  ___ \\ |\\   ___  \\    ",
        "\\ \\  \\|\\ /_   \\ \\  \\/  / ||___ \\  \\_\\ \\   __/|\\ \\  \\    \\ \\  \\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\_|\\ \\ \\   __/|\\ \\  \\\\ \\  \\   ",
        " \\ \\   __  \\   \\ \\    / /     \\ \\  \\ \\ \\  \\_|/_\\ \\  \\  __\\ \\  \\ \\   __  \\ \\   _  _\\ \\  \\ \\\\ \\ \\  \\_|/_\\ \\  \\\\ \\  \\  ",
        "  \\ \\  \\|\\  \\   \\/  /  /       \\ \\  \\ \\ \\  \\_|\\ \\ \\  \\|\\__\\_\\  \\ \\  \\ \\  \\ \\  \\\\  \\\\ \\  \\_\\\\ \\ \\  \\_|\\ \\ \\  \\\\ \\  \\ ",
        "   \\ \\_______\\__/  / /          \\ \\__\\ \\ \\_______\\ \\____________\\ \\__\\ \\__\\ \\__\\\\ _\\\\ \\_______\\ \\_______\\ \\__\\\\ \\__\\",
        "    \\|_______|\\___/ /            \\|__|  \\|_______|\\|____________|\\|__|\\|__|\\|__|\\|__|\\|_______|\\|_______|\\|__| \\|__|",
        "             \\|___|/                                                                                                "
    };

    private static void animateLogo() {
        try {
            // Clear console
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Create a buffer to store the current state of the logo
            char[][] buffer = new char[LOGO_LINES.length][];
            for (int i = 0; i < LOGO_LINES.length; i++) {
                buffer[i] = new char[LOGO_LINES[i].length()];
                for (int j = 0; j < LOGO_LINES[i].length(); j++) {
                    buffer[i][j] = ' ';
                }
            }

            // Animate each character
            for (int i = 0; i < LOGO_LINES.length; i++) {
                for (int j = 0; j < LOGO_LINES[i].length(); j++) {
                    buffer[i][j] = LOGO_LINES[i].charAt(j);
                    
                    // Clear screen and redraw the entire buffer
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    
                    // Print current state of buffer
                    for (int k = 0; k < buffer.length; k++) {
                        System.out.println("\033[1;32m" + new String(buffer[k]) + "\033[0m");
                    }
                    
                    TimeUnit.MILLISECONDS.sleep(1);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void matrixRain() {
        try {
            String[] matrix = {"ｱ", "ｲ", "ｳ", "ｴ", "ｵ", "ｶ", "ｷ", "ｸ", "ｹ", "ｺ", "ﾀ", "ﾁ", "ﾂ", "ﾃ", "ﾄ", "ﾅ",
                              "1", "0", "@", "#", "$", "%", "&", "*"};
            Random rand = new Random();
            
            String[] buffer = new String[10];
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = "";
            }

            for (int i = 0; i < 20; i++) { // Reduced iterations for faster startup
                for (int j = buffer.length - 1; j > 0; j--) {
                    buffer[j] = buffer[j-1];
                }
                
                StringBuilder newLine = new StringBuilder();
                for (int j = 0; j < 50; j++) {
                    newLine.append(matrix[rand.nextInt(matrix.length)]).append(" ");
                }
                buffer[0] = newLine.toString();

                System.out.print("\033[H\033[2J");
                System.out.flush();
                
                for (String line : LOGO_LINES) {
                    System.out.println("\033[1;32m" + line + "\033[0m");
                }
                System.out.println();
                
                for (String line : buffer) {
                    System.out.println("\033[0;32m" + line + "\033[0m");
                }
                
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void showSplashScreen() {
        animateLogo();
        matrixRain();
        System.out.print("\033[H\033[2J"); // Clear screen before showing menu
        System.out.flush();
    }

    public static void main(String[] args) {
        // Show splash screen
        showSplashScreen();

        // Initialize the scanner for user input
        Scanner scanner = new Scanner(System.in);
        // Load the database file to manage existing databases
        File databasesFile = new File("databases.json");
        DatabasesManager dbManager = new DatabasesManager(databasesFile);

        // Your existing menu code starts here
        System.out.println("Welcome to the Encryption/Decryption Program");
        System.out.println("Choose an option:");
        System.out.println("1. Choose an existing database");
        System.out.println("2. Create a new database");
        System.out.println("3. Help Menu");

        // Read the user's choice for the menu
        int dbChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt

        if (dbChoice == 1) {
            System.out.println("Enter the name of the database:");
            String dbName = scanner.nextLine();
            System.out.println("Enter the password:");
            String inputPassword = scanner.nextLine();

            if (dbManager.verifyDatabase(dbName, inputPassword)) {
                System.out.println("Successfully connected to the database: " + dbName);
                Map<String, String> encryptionMap = dbManager.getEncryptionMap(dbName);
                SiteManager siteManager = new SiteManager(new File(dbName + ".json"), encryptionMap);
                siteManager.manageSites(scanner);
            } else {
                System.out.println("Incorrect database name or password.");
            }
        }
        else if (dbChoice == 2) {
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

            Map<String, String> encryptionMap = new HashMap<>();
            boolean addMoreEncryptions = true;

            while (addMoreEncryptions) {
                System.out.println("Choose an encryption method:");
                System.out.println("1. RotX");
                System.out.println("2. RC4");
                System.out.println("3. Vigenere");
                System.out.println("4. Polybios");
                System.out.println("5. Done adding encryptions");

                int encryptionChoice = scanner.nextInt();
                scanner.nextLine();

                switch (encryptionChoice) {
                    case 1 -> {
                        System.out.println("Enter the shift value for RotX:");
                        String shiftValue = scanner.nextLine();
                        encryptionMap.put("RotX", shiftValue);
                    }
                    case 2 -> {
                        System.out.println("Enter the key for RC4:");
                        String rc4Key = scanner.nextLine();
                        encryptionMap.put("RC4", rc4Key);
                    }
                    case 3 -> {
                        System.out.println("Enter the key for Vigenere:");
                        String vigenereKey = scanner.nextLine();
                        encryptionMap.put("Vigenere", vigenereKey);
                    }
                    case 4 -> {
                        encryptionMap.put("Polybios", "default");
                    }
                    case 5 -> addMoreEncryptions = false;
                    default -> System.out.println("Invalid choice. Please choose a valid encryption method.");
                }
            }

            dbManager.createDatabase(dbName, password, encryptionMap);
            SiteManager siteManager = new SiteManager(new File(dbName + ".json"), encryptionMap);
            siteManager.manageSites(scanner);
        }
        else if (dbChoice == 3) {
            HelpMenu.displayMenuHelp(scanner);
        }
        else {
            System.out.println("Invalid choice.");
        }
    }
}