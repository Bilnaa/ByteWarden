package Classes;

import java.util.Scanner;

public class HelpMenu {

    // Main method to display the help menu and handle user navigation.
    public static void displayMenuHelp(Scanner scanner) {
        int choice;

        // Loop to display the menu until the user chooses to exit.
        do {
            System.out.println("\n========== HELP MENU ==========");
            System.out.println("1. How to create a database?");
            System.out.println("2. How to encrypt my database password?");
            System.out.println("3. How the algorithms work ?");
            System.out.println("4. How to decrypt my password?");
            System.out.println("5. How to a add/ modify/ delete a site in my database?");
            System.out.println("6. Exit help menu");
            System.out.print("Select an option: ");

            // Read user's choice.
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character.

            // Handle the user's choice using a switch statement.
            switch (choice) {
                case 1:
                    navigateCreateDatabaseHelp(scanner); // Navigate to database creation help.
                    break;
                case 2:
                    navigateEncryptPasswordHelp(scanner); // Navigate to password encryption help.
                    break;
                case 3:
                    navigateExplainAlgorithms(scanner); // Navigate to algorithm explanations.
                    break;
                case 4:
                    navigateDecryptPasswordHelp(scanner); // Navigate to password decryption help.
                    break;
                case 5:
                    navigateNewSiteHelp(scanner);
                    break;
                case 6:
                    System.out.println("Exiting help menu..."); // Exit message.
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option."); // Handle invalid input.
            }
        } while (choice != 5);

        // Close the scanner when exiting the menu.
        scanner.close();
    }

    // Method to provide help on creating a database.
    private static void navigateCreateDatabaseHelp(Scanner scanner) {
        boolean back = false;
        do {
            System.out.println("\n--- How to Create a Database ---");
            System.out.println("1. Open the application.");
            System.out.println("2. Choose the 'Create a new database' option in the main menu.");
            System.out.println("3. Enter a name for your database and a master password.");
            System.out.println("4. The application will encrypt and store your database securely.");
            System.out.println("5. Remember your master password; it cannot be recovered if lost.");
            System.out.println("6. Go back");
            System.out.println("7. Exit Help Menu");
            System.out.print("Select an option: ");

            // Read the user's choice.
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase(String.valueOf(6))) {
                back = true; // Go back to the main help menu.
            } else if (choice.equalsIgnoreCase(String.valueOf(7))) {
                System.out.println("Exiting help menu...");
                System.exit(0); // Exit the program.
            } else {
                System.out.println("Invalid input. Please choose 'Go back' or 'Exit'.");
            }
        } while (!back);
    }

    // Method to provide help on encrypting database passwords.
    private static void navigateEncryptPasswordHelp(Scanner scanner) {
        boolean back = false;
        do {
            System.out.println("\n--- How to Encrypt My Database Password ---");
            System.out.println("1. When adding a password to your database, choose an encryption algorithm:");
            System.out.println("   - ROT(X): Shifts characters by X positions.");
            System.out.println("   - RC4: A stream cipher for quick encryption.");
            System.out.println("   - Vigenere: Uses a keyword to shift characters.");
            System.out.println("   - Enigma: Simulates the historical encryption machine.");
            System.out.println("   - Polybe: Encodes text using a 5x5 grid.");
            System.out.println("2. The application will encrypt and save the password using the selected algorithm.");
            System.out.println("3. Go back");
            System.out.println("4. Exit Help Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase(String.valueOf(3))) {
                back = true; // Go back to the main help menu.
            } else if (choice.equalsIgnoreCase(String.valueOf(4))) {
                System.out.println("Exiting help menu...");
                System.exit(0); // Exit the program.
            } else {
                System.out.println("Invalid input. Please choose 'Go back' or 'Exit'.");
            }
        } while (!back);
    }




    // Method to explain encryption algorithms.
    private static void navigateExplainAlgorithms(Scanner scanner) {
        boolean back = false;
        do {
            System.out.println("\n--- Algorithm Explanations ---");
            System.out.println("1. ROT(X): Rotates each character by X positions in the alphabet.");
            System.out.println("   - Example: 'A' with ROT(3) becomes 'D'.");
            System.out.println("2. RC4: A symmetric key stream cipher that encrypts data byte by byte.");
            System.out.println("3. Vigenere: Uses a keyword to shift letters cyclically.");
            System.out.println("   - Example: Keyword 'KEY' applied to text shifts letters by positions derived from 'KEY'.");
            System.out.println("4. Enigma: Mimics the encryption of the WWII Enigma machine.");
            System.out.println("5. Polybe: Encodes text into pairs of numbers based on a 5x5 grid.");
            System.out.println("6. Go back");
            System.out.println("7. Exit Help Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase(String.valueOf(6))) {
                back = true; // Go back to the main help menu.
            } else if (choice.equalsIgnoreCase(String.valueOf(7))) {
                System.out.println("Exiting help menu...");
                System.exit(0); // Exit the program.
            } else {
                System.out.println("Invalid input. Please choose 'Go back' or 'Exit'.");
            }
        } while (!back);
    }

    // Method to provide help on decrypting passwords.
    private static void navigateDecryptPasswordHelp(Scanner scanner) {
        boolean back = false;
        do {
            System.out.println("\n--- How to Decrypt My Password ---");
            System.out.println("1. Select the password you want to decrypt from the database.");
            System.out.println("2. Enter the master password to access the database.");
            System.out.println("3. The application will identify the encryption algorithm used.");
            System.out.println("4. Enter any additional required keys or settings (e.g., keyword for Vigenere).");
            System.out.println("5. The password will be decrypted and displayed securely.");
            System.out.println("6. Go back");
            System.out.println("7. Exit Help Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase(String.valueOf(6))) {
                back = true; // Go back to the main help menu.
            } else if (choice.equalsIgnoreCase(String.valueOf(7))) {
                System.out.println("Exiting help menu...");
                System.exit(0); // Exit the program.
            } else {
                System.out.println("Invalid input. Please choose 'Go back' or 'Exit'.");
            }
        } while (!back);
    }

    // Method to provide help on adding/ modifying/ deleting a site in the database
    private static void navigateNewSiteHelp(Scanner scanner) {
        boolean back = false;
        do {
            System.out.println("\n--- How to Add/ Modify/ Delete a  Site in my database ---");
            System.out.println("1. In the main menu select : 'Choose an existing database'");
            System.out.println("2. Connect to your database where you have your password");
            System.out.println("3. Select 'Manage sites'");
            System.out.println("4. Select 'Add a new site'/ 'Modify a site' or 'Delete a site'");
            System.out.println("5. Follow the instructions");
            System.out.println("6. Go back");
            System.out.println("7. Exit Help Menu");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase(String.valueOf(6))) {
                back = true; // Go back to the main help menu.
            } else if (choice.equalsIgnoreCase(String.valueOf(7))) {
                System.out.println("Exiting help menu...");
                System.exit(0); // Exit the program.
            } else {
                System.out.println("Invalid input. Please choose 'Go back' or 'Exit'.");
            }
        } while (!back);
    }
}
