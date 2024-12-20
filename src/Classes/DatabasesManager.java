package Classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DatabasesManager {
    private final File databasesFile; // The file where the databases and their passwords are stored
    private final Map<String, DatabaseInfo> databases; // A map holding the database names as keys and their info as values
    private final Sha256 sha256 = new Sha256();

    public DatabasesManager(File databasesFile) {
        this.databasesFile = databasesFile;
        this.databases = new HashMap<>(); // Initialize the map before the try-catch block
        if (!databasesFile.exists()) {
            try {
                databasesFile.createNewFile();
                saveDatabases(); // Sauvegarder une base de données vide
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.databases.putAll(loadDatabases());
        }
    }

    public boolean verifyDatabase(String dbName, String password) {
        DatabaseInfo dbInfo = databases.get(dbName);
        if (dbInfo == null) {
            return false;
        }
        String hashedPassword = sha256.calculateHash(password);
        return hashedPassword.equals(dbInfo.getHashedPassword());
    }

    public void createDatabase(String dbName, String password, String algorithm) {
        String hashedPassword = sha256.calculateHash(password);
        DatabaseInfo dbInfo = new DatabaseInfo(hashedPassword, algorithm);
        databases.put(dbName, dbInfo);
        saveDatabases();
    }

    public Map<String, DatabaseInfo> loadDatabases() {
        if (!databasesFile.exists()) {
            return new HashMap<>();
        }
        try (FileReader reader = new FileReader(databasesFile)) {
            Type type = new TypeToken<Map<String, DatabaseInfo>>() {}.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void saveDatabases() {
        try (FileWriter writer = new FileWriter(databasesFile)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(databases, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void encryptAllSites(EncryptionStack encryptionStack) {
        for (Map.Entry<String, DatabaseInfo> entry : databases.entrySet()) {
            String dbName = entry.getKey();
            DatabaseInfo dbInfo = entry.getValue();
            File siteFile = new File(dbName + ".json");
            if (siteFile.exists()) {
                try {
                    String content = new String(java.nio.file.Files.readAllBytes(siteFile.toPath()));
                    String encryptedContent = encryptionStack.encrypt(content);
                    try (FileWriter writer = new FileWriter(siteFile)) {
                        writer.write(encryptedContent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Inner class to hold database information
    public static class DatabaseInfo {
        private final String hashedPassword;
        private final String algorithm;

        public DatabaseInfo(String hashedPassword, String algorithm) {
            this.hashedPassword = hashedPassword;
            this.algorithm = algorithm;
        }

        public String getHashedPassword() {
            return hashedPassword;
        }

        public String getAlgorithm() {
            return algorithm;
        }
    }
}