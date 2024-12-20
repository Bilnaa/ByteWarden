package Classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SiteManager {
    private final File dbFile;
    private final List<Map<String, String>> sites;
    private final Map<String, String> encryptionMap; // Méthodes de chiffrement associées

    public SiteManager(File dbFile, Map<String, String> encryptionMap) {
        this.dbFile = dbFile; //file name
        this.encryptionMap = encryptionMap;// encryption method with parameter needed
        this.sites = loadSites();
    }

    public void manageSites(Scanner scanner) { //methods to ask user what method he will call
        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Add a site");
            System.out.println("2. Modify a site");
            System.out.println("3. Delete a site");
            System.out.println("4. Display all sites");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) { //calls method based on the user input
                case 1 -> addSite(scanner);
                case 2 -> modifySite(scanner);
                case 3 -> deleteSite(scanner);
                case 4 -> displaySites();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public void addSite(String siteName, String username, String password) {//add method use for applying value from scanner method and for the test file
        Map<String, String> site = new HashMap<>();
        site.put("siteName", siteName);
        site.put("username", username);
        site.put("password", password);
        sites.add(site);
        saveSites();
    }

    public void addSite(Scanner scanner) { //add site in the selected json
        System.out.println("Enter site name:");
        String siteName = scanner.nextLine();
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        addSite(siteName, username, password);
    }

    public void modifySite(String siteName, String newUsername, String newPassword) { //modify method use for applying value from scanner method and for the test file
        for (Map<String, String> site : sites) {
            if (site.get("siteName").equals(siteName)) {
                if (newUsername != null && !newUsername.isEmpty()) {
                    site.put("username", newUsername);
                }
                if (newPassword != null && !newPassword.isEmpty()) {
                    site.put("password", newPassword);
                }
                saveSites();
                return;
            }
        }
        throw new IllegalArgumentException("Site not found: " + siteName);
    }

    public void modifySite(Scanner scanner) { //modify the selected site from the selected json
        System.out.println("Enter the site name to modify:");
        String siteName = scanner.nextLine();
        System.out.println("Enter the new username (leave empty to keep current):");
        String newUsername = scanner.nextLine();
        System.out.println("Enter the new password (leave empty to keep current):");
        String newPassword = scanner.nextLine();
        modifySite(siteName, newUsername, newPassword);
    }

    public void deleteSite(String siteName) {   //delete method use for the test file
        sites.removeIf(site -> site.get("siteName").equals(siteName));
        saveSites();
    }

    public void deleteSite(Scanner scanner) { //delete the selected site from the selected json
        System.out.println("Enter the site name to delete:");
        String siteName = scanner.nextLine();
        deleteSite(siteName);
    }

    public void displaySites() { // displays all sites from selected json
        if (sites.isEmpty()) {
            System.out.println("No sites available.");
        } else {
            for (Map<String, String> site : sites) {
                String siteName = site.get("siteName");
                String username = site.get("username");
                String password = site.get("password");
                System.out.println("Site Name: " + siteName);
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                System.out.println("-----------------------------");
            }
        }
    }

    public List<Map<String, String>> loadSites() {
        if (!dbFile.exists()) return new ArrayList<>();
        try (FileReader reader = new FileReader(dbFile)) {
            // read file and decrypt
            StringBuilder encryptedContent = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                encryptedContent.append((char) c);
            }

            // decrypt the file content
            String decryptedContent = decrypt(encryptedContent.toString());

            Gson gson = new Gson();
            Map<String, Object> data = gson.fromJson(decryptedContent, Map.class);
            return (List<Map<String, String>>) data.get("sites");
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveSites() {
        try (FileWriter writer = new FileWriter(dbFile)) {
            // convert sites into java json file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map<String, Object> data = Map.of("sites", sites);
            String jsonContent = gson.toJson(data);

            // crypt json content
            String encryptedContent = encrypt(jsonContent);

            writer.write(encryptedContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String encrypt(String input) {
        String result = input;
        //all case for all encryption methods
        for (String method : encryptionMap.keySet()) {
            switch (method) {
                case "RotX" -> {
                    //rotx encryption
                    int shift = Integer.parseInt(encryptionMap.get("RotX")); //get encryption value from the encryption map from databases.json
                    result = ROTX.encryptROT(result, shift);
                }
                case "RC4" -> {
                    //rc4 encryption

                    RC4 rc4 = new RC4();
                    String key = encryptionMap.get("RC4");//get encryption value from the encryption map from databases.json
                    rc4.init(key);
                    result = rc4.encrypt(result);
                }
                case "Vigenere" -> {
                    //vigenere encryption

                    String key = encryptionMap.get("Vigenere");//get encryption value from the encryption map from databases.json
                    result = VigenereAlgo.encrypt(result, key);
                }
                case "Polybios" -> {
                }
                case "AES" -> {
                }
            }
        }
        return result;
    }

    private String decrypt(String input) {
        String result = input;
        // invert encryption order to handle the multiple encryption
        List<String> methods = new ArrayList<>(encryptionMap.keySet());
        Collections.reverse(methods);

        for (String method : methods) {
            switch (method) {
                case "RotX" -> {
                    //decrypt json content from databases RotX encryptionmethod
                    int shift = Integer.parseInt(encryptionMap.get("RotX"));
                    result = ROTX.decryptROT(result, shift);
                }
                case "RC4" -> {
                    //decrypt json content from databases RC4 encryptionmethod

                    RC4 rc4 = new RC4();
                    String key = encryptionMap.get("RC4");
                    rc4.init(key);
                    result = rc4.decrypt(result);
                }
                case "Vigenere" -> {
                    //decrypt json content from databases Vigenere encryptionmethod

                    String key = encryptionMap.get("Vigenere");
                    result = VigenereAlgo.decrypt(result, key);
                }
                case "Polybios" -> {
                }
                case "AES" -> {
                }
            }
        }
        return result;
    }

}
