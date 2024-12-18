package Classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initial menu
        System.out.println("Welcome to the Encryption/Decryption Program");
        System.out.println("Choose an option:");
        System.out.println("1. Choose an existing database");
        System.out.println("2. Create a new database");
        int dbChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline after integer input

        if (dbChoice == 1) {
            System.out.println("You selected: Choose an existing database");
            // Add logic to handle existing databases (e.g., load or list databases)
        } else if (dbChoice == 2) {
            System.out.println("You selected: Create a new database");
            // Add logic to create a new database
        } else {
            System.out.println("Invalid choice.");
            return; // Exit if invalid choice
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Sha256 sha256 = new Sha256(); // For password verification

        System.out.print("Enter the name of the BDD file (without .json extension): ");
        String bddFileName = scanner.nextLine() + ".json";
        File bddFile = new File(bddFileName);

        if (!bddFile.exists() || !bddFile.isFile()) {
            System.out.println("File does not exist or is not a valid JSON file.");
            return;
        }

        try (FileReader reader = new FileReader(bddFile)) {
            Map<String, String> bddData = gson.fromJson(reader, Map.class);

            // Verify the password
            System.out.print("Enter the password for the BDD: ");
            String inputPassword = scanner.nextLine();
            String hashedInputPassword = sha256.calculateHash(inputPassword);

            if (!hashedInputPassword.equals(bddData.get("password"))) {
                System.out.println("Incorrect password!");
                return;
            }

            System.out.println("\nConnected to BDD: " + bddData.get("name"));

            // Display the secondary menu
            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Encrypt a password");
                System.out.println("2. Decrypt a password");
                System.out.println("0. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 0) {
                    System.out.println("Exiting...");
                    break;
                }

                switch (choice) {
                    case 1: // Encrypt
                        System.out.println("Enter the password to encrypt:");
                        String passwordToEncrypt = scanner.nextLine();

                        System.out.println("Choose an encryption algorithm:");
                        System.out.println("1. Enigma");
                        System.out.println("2. RC4");
                        System.out.println("3. ROT(X)");
                        System.out.println("4. Vigenère");
                        int algoChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        String encryptedPassword = "";
                        switch (algoChoice) {
                            case 1:
                                encryptedPassword = encryptEnigma(passwordToEncrypt);
                                break;
                            case 2:
                                encryptedPassword = encryptRC4(passwordToEncrypt);
                                break;
                            case 3:
                                System.out.println("Enter the value of X for ROT(X):");
                                int x = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                encryptedPassword = encryptROT(passwordToEncrypt, x);
                                break;
                            case 4:
                                System.out.println("Enter the key for Vigenère:");
                                String key = scanner.nextLine();
                                encryptedPassword = encryptVigenere(passwordToEncrypt, key);
                                break;
                            default:
                                System.out.println("Invalid algorithm choice.");
                                continue;
                        }

                        System.out.println("Encrypted password: " + encryptedPassword);

                        // Save the encrypted password to the JSON file
                        bddData.put("encrypted_password", encryptedPassword);
                        try (FileWriter writer = new FileWriter(bddFile)) {
                            gson.toJson(bddData, writer);
                            System.out.println("Encrypted password saved to BDD.");
                        } catch (IOException e) {
                            System.out.println("Error saving to BDD file.");
                        }
                        break;

                    case 2: // Decrypt
                        String encryptedMessage = bddData.get("encrypted_password");
                        if (encryptedMessage == null) {
                            System.out.println("No encrypted password found in the BDD.");
                            break;
                        }

                        System.out.println("Choose a decryption algorithm:");
                        System.out.println("1. Enigma");
                        System.out.println("2. RC4");
                        System.out.println("3. ROT(X)");
                        System.out.println("4. Vigenère");
                        int decryptAlgoChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        String decryptedPassword = "";
                        switch (decryptAlgoChoice) {
                            case 1:
                                decryptedPassword = decryptEnigma(encryptedMessage);
                                break;
                            case 2:
                                decryptedPassword = decryptRC4(encryptedMessage);
                                break;
                            case 3:
                                System.out.println("Enter the value of X for ROT(X):");
                                int x = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                decryptedPassword = decryptROT(encryptedMessage, x);
                                break;
                            case 4:
                                System.out.println("Enter the key for Vigenère:");
                                String key = scanner.nextLine();
                                decryptedPassword = decryptVigenere(encryptedMessage, key);
                                break;
                            default:
                                System.out.println("Invalid algorithm choice.");
                                continue;
                        }

                        System.out.println("Decrypted password: " + decryptedPassword);
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while accessing the BDD file.");
            e.printStackTrace();
        }
    }

    // Encryption algorithms (simplified implementations)
    public static String encryptEnigma(String password) {
        return "Enigma Encryption: " + password;  // Replace with actual implementation
    }

    public static String encryptRC4(String password) {
        return "RC4 Encryption: " + password;  // Replace with actual implementation
    }

    public static String encryptROT(String password, int x) {
        return ROTX.encryptROT(password, x);  // Replace with actual implementation
    }

    public static String encryptVigenere(String password, String key) {
        return VigenereAlgo.encrypt(password, key);  // Replace with actual implementation
    }

    // Decryption algorithms (simplified implementations)
    public static String decryptEnigma(String encryptedMessage) {
        return "Enigma Decryption: " + encryptedMessage;  // Replace with actual implementation
    }

    public static String decryptRC4(String encryptedMessage) {
        return "RC4 Decryption: " + encryptedMessage;  // Replace with actual implementation
    }

    public static String decryptROT(String input, int x) {
        return ROTX.decryptROT(input, x);  // Replace with actual implementation
    }

    public static String decryptVigenere(String encryptedMessage, String key) {
        return VigenereAlgo.decrypt(encryptedMessage, key);  // Replace with actual implementation
    }
}
