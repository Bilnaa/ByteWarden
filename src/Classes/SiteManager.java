package Classes;

import Classes.Enigma.Enigma;
import Classes.Enigma.Plugboard;
import Classes.Enigma.Reflector;
import Classes.Enigma.Rotors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SiteManager {
    private final File dbFile; // The file where the site data is stored
    private final List<Map<String, String>> sites; // List of sites, each represented as a map of properties
    public String encryptionAlgorithm; // Selected encryption algorithm

    // Constructor initializing the database file and loading sites from the file
    public SiteManager(File dbFile) {
        this.dbFile = dbFile;
        this.sites = loadSites();
    }

    // Function to manage the sites (add, modify, delete, or exit)
    public void manageSites(Scanner scanner, String encryptionAlgo) {
        chooseEncryptionAlgorithm(scanner); // Let the user choose an encryption algorithm

        while (true) {
            // Display menu options
            System.out.println("Choose an action:");
            System.out.println("1. Add a site");
            System.out.println("2. Modify a site");
            System.out.println("3. Delete a site");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            // Handle user's choice
            switch (choice) {
                case 1 -> addSite(scanner); // Add a new site
                case 2 -> modifySite(scanner); // Modify an existing site
                case 3 -> deleteSite(scanner); // Delete a site
                case 4 -> {
                    return; // Exit the site management menu
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Function to handle encryption/decryption of passwords
    public void handleEncryptionDecryption(Scanner scanner) {
        System.out.println("Choose an action:");

        // Offer the user to decrypt a password
        System.out.println("1. Decrypt a password");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        if (choice == 1) {
            // Ask for the site name to decrypt the password for
            System.out.println("Enter site name:");
            String siteName = scanner.nextLine();
            Optional<Map<String, String>> siteOpt = sites.stream()
                    .filter(site -> site.get("siteName").equals(siteName))
                    .findFirst();

            // Check if the site exists
            if (siteOpt.isPresent()) {
                Map<String, String> site = siteOpt.get();
                String encryptedPassword = site.get("password");

                // Decrypt the password and display it
                String decryptedPassword = decryptPassword(encryptedPassword, scanner);
                System.out.println("Decrypted password: " + decryptedPassword);
            } else {
                System.out.println("Site not found.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Function to encrypt a password based on the selected algorithm
    private String encryptPassword(String password, Scanner scanner) {
        return switch (encryptionAlgorithm) {
            case "Enigma" -> {
                // Configuration for the Enigma machine
                System.out.print("Enter the configuration for Enigma - Rotors (example: I II III): ");
                String rotorsConfig = scanner.nextLine().trim();
                Rotors rotors = new Rotors();

                System.out.print("Enter the configuration for the Reflector (example: B): ");
                String reflectorConfig = scanner.nextLine().trim();
                Reflector reflector = new Reflector();

                System.out.print("Enter the plugboard configuration (example: AB CD EF): ");
                String plugboardConfig = scanner.nextLine().trim();
                Plugboard plugboard = new Plugboard();

                // Create the Enigma machine instance and encrypt the password
                Enigma enigmaMachine = new Enigma(rotors, reflector, plugboard);
                yield enigmaMachine.encrypt(password);
            }
            case "RC4" -> AlgorithmChoice.encryptRC4(password); // RC4 encryption
            case "ROT" -> {
                System.out.println("Enter the value of X for ROT(X):");
                int x = scanner.nextInt();
                scanner.nextLine();
                yield AlgorithmChoice.encryptROT(password, x); // ROT encryption
            }
            case "Vigenere" -> {
                System.out.println("Enter the key for Vigenère:");
                String key = scanner.nextLine();
                yield AlgorithmChoice.encryptVigenere(password, key); // Vigenère encryption
            }
            default -> throw new IllegalStateException("Unknown encryption algorithm: " + encryptionAlgorithm); // Error for unknown algorithm
        };
    }

    // Function to decrypt a password based on the selected algorithm
    private String decryptPassword(String encryptedPassword, Scanner scanner) {
        return switch (encryptionAlgorithm) {
            case "Enigma" -> AlgorithmChoice.decryptEnigma(encryptedPassword); // Enigma decryption
            case "RC4" -> AlgorithmChoice.decryptRC4(encryptedPassword); // RC4 decryption
            case "ROT" -> {
                System.out.println("Enter the value of X for ROT(X):");
                int x = scanner.nextInt();
                scanner.nextLine();
                yield AlgorithmChoice.decryptROT(encryptedPassword, x); // ROT decryption
            }
            case "Vigenere" -> {
                System.out.println("Enter the key for Vigenère:");
                String key = scanner.nextLine();
                if (key == null || key.isEmpty()) {
                    throw new IllegalArgumentException("Key for Vigenère cannot be null or empty");
                }
                yield AlgorithmChoice.decryptVigenere(encryptedPassword, key); // Vigenère decryption
            }
            default -> throw new IllegalStateException("Unknown encryption algorithm: " + encryptionAlgorithm); // Error for unknown algorithm
        };
    }

    // Function to choose an encryption algorithm
    public void chooseEncryptionAlgorithm(Scanner scanner) {
        System.out.println("Choose an encryption algorithm:");
        System.out.println("1. Enigma");
        System.out.println("2. RC4");
        System.out.println("3. ROT(X)");
        System.out.println("4. Vigenère");

        // Read the user's choice
        int algoChoice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        // Set the encryption algorithm based on user's choice
        encryptionAlgorithm = switch (algoChoice) {
            case 1 -> "Enigma";
            case 2 -> "RC4";
            case 3 -> "ROT";
            case 4 -> "Vigenere";
            default -> {
                System.out.println("Invalid choice, defaulting to ROT.");
                yield "ROT"; // Default to ROT
            }
        };
        System.out.println("Selected encryption algorithm: " + encryptionAlgorithm);
    }

    // Function to add a new site with encrypted password
    public void addSite(String siteName, String username, String password) {
        Map<String, String> site = new HashMap<>();
        site.put("siteName", siteName);
        site.put("username", username);
        site.put("password", password);
        sites.add(site); // Add site to the list
        saveSites(); // Save the updated sites list to file
    }

    // Function to add a new site using user input
    public void addSite(Scanner scanner) {
        System.out.println("Enter site name:");
        String siteName = scanner.nextLine();
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        // Encrypt the password and add the site
        String encryptedPassword = encryptPassword(password, scanner);
        addSite(siteName, username, encryptedPassword);
    }

    // Function to modify an existing site's username or password
    public void modifySite(String siteName, String newUsername, String newPassword) {
        for (Map<String, String> site : sites) {
            if (site.get("siteName").equals(siteName)) {
                // Update the username and/or password if provided
                if (newUsername != null && !newUsername.isEmpty()) {
                    site.put("username", newUsername);
                }
                if (newPassword != null && !newPassword.isEmpty()) {
                    site.put("password", newPassword);
                }
                saveSites(); // Save the updated list of sites
                return;
            }
        }
        throw new IllegalArgumentException("Site not found: " + siteName); // Site not found
    }

    // Function to modify a site using user input
    public void modifySite(Scanner scanner) {
        System.out.println("Enter the site name to modify:");
        String siteName = scanner.nextLine();
        System.out.println("Enter the new username (leave empty to keep current):");
        String newUsername = scanner.nextLine();
        System.out.println("Enter the new password (leave empty to keep current):");
        String newPassword = scanner.nextLine();
        modifySite(siteName, newUsername, newPassword); // Modify the site
    }

    // Function to delete a site by name
    public void deleteSite(String siteName) {
        sites.removeIf(site -> site.get("siteName").equals(siteName)); // Remove the site from the list
        saveSites(); // Save the updated list of sites
    }

    // Function to delete a site using user input
    public void deleteSite(Scanner scanner) {
        System.out.println("Enter the site name to delete:");
        String siteName = scanner.nextLine();
        deleteSite(siteName); // Delete the site
    }

    // Function to load sites from the JSON file
    public List<Map<String, String>> loadSites() {
        if (!dbFile.exists()) return new ArrayList<>(); // Return an empty list if the file doesn't exist

        try (FileReader fileReader = new FileReader(dbFile)) {
            // Parse the file content using Gson
            Gson gson = new Gson();
            List<Map<String, String>> sitesList = gson.fromJson(fileReader, ArrayList.class);
            return sitesList != null ? sitesList : new ArrayList<>(); // Return the loaded sites or an empty list if parsing fails
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }

    // Function to save sites to the JSON file
    public void saveSites() {
        try (FileWriter fileWriter = new FileWriter(dbFile)) {
            // Write the sites list to the file in JSON format
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(sites, fileWriter);
        } catch (IOException e) {
            System.out.println("Error saving sites: " + e.getMessage());
        }
    }
}
