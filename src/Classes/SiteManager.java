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

    public SiteManager(File dbFile) {
        this.dbFile = dbFile;
        this.sites = loadSites();
    }

    public void manageSites(Scanner scanner) {
        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Add a site");
            System.out.println("2. Modify a site");
            System.out.println("3. Delete a site");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addSite(scanner);
                case 2 -> modifySite(scanner);
                case 3 -> deleteSite(scanner);
                case 4 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public void addSite(String siteName, String username, String password) {
        Map<String, String> site = Map.of("siteName", siteName, "username", username, "password", password);
        sites.add(site);
        saveSites();
    }

    public void addSite(Scanner scanner) {
        System.out.println("Enter site name:");
        String siteName = scanner.nextLine();
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        addSite(siteName, username, password);
    }

    public void modifySite(String siteName, String newUsername, String newPassword) {
        for (Map<String, String> site : sites) {
            if (site.get("siteName").equals(siteName)) {
                if (newUsername != null && !newUsername.isEmpty()) {
                    site.put("username", newUsername);
                }
                if (newPassword != null && !newPassword.isEmpty()) {
                    site.put("password", newPassword);
                }
                System.out.println("Modified site: " + site);
                saveSites();
                return;
            }
        }
        System.out.println("Site not found for modification: " + siteName);
        throw new IllegalArgumentException("Site not found: " + siteName);
    }

    public void modifySite(Scanner scanner) {
        System.out.println("Enter the site name to modify:");
        String siteName = scanner.nextLine();
        System.out.println("Enter the new username (leave empty to keep current):");
        String newUsername = scanner.nextLine();
        System.out.println("Enter the new password (leave empty to keep current):");
        String newPassword = scanner.nextLine();
        modifySite(siteName, newUsername, newPassword);
    }


    public void deleteSite(String siteName) {
        sites.removeIf(site -> site.get("siteName").equals(siteName));
        saveSites();
    }

    public void deleteSite(Scanner scanner) {
        System.out.println("Enter the site name to delete:");
        String siteName = scanner.nextLine();
        deleteSite(siteName);
    }


    public List<Map<String, String>> loadSites() {
        if (!dbFile.exists()) return new ArrayList<>();
        try (FileReader reader = new FileReader(dbFile)) {
            Gson gson = new Gson();
            Map<String, Object> data = gson.fromJson(reader, Map.class);
            return (List<Map<String, String>>) data.get("sites");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void saveSites() {
        try (FileWriter writer = new FileWriter(dbFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map<String, Object> data = Map.of("sites", sites);
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
