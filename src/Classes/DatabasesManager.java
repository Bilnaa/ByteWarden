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
    private final File databasesFile;
    private final Map<String, Database> databases;
    private final Sha256 sha256 = new Sha256();

    public DatabasesManager(File databasesFile) {
        this.databasesFile = databasesFile;
        this.databases = loadDatabases();
    }

    public boolean verifyDatabase(String dbName, String password) {
        if (!databases.containsKey(dbName)) return false;
        Database database = databases.get(dbName);
        String hashedPassword = sha256.calculateHash(password);
        return database.getHashPassword().equals(hashedPassword);
    }

    public void createDatabase(String dbName, String password, Map<String, String> encryptionMap) {
        if (databases.containsKey(dbName)) {
            throw new IllegalArgumentException("Database already exists.");
        }
        String hashedPassword = sha256.calculateHash(password);
        Database newDatabase = new Database(dbName, hashedPassword, encryptionMap);
        databases.put(dbName, newDatabase);
        saveDatabases();
    }

    private Map<String, Database> loadDatabases() {
        if (!databasesFile.exists()) return new HashMap<>();
        try (FileReader reader = new FileReader(databasesFile)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Database>>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void saveDatabases() {
        try (FileWriter writer = new FileWriter(databasesFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(databases, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Classe interne représentant une base de données
    public static class Database {
        private final String name;
        private final String hashPassword;
        private final Map<String, String> encryptionMap;

        public Database(String name, String hashPassword, Map<String, String> encryptionMap) {
            this.name = name;
            this.hashPassword = hashPassword;
            this.encryptionMap = encryptionMap;
        }

        public String getName() {
            return name;
        }

        public String getHashPassword() {
            return hashPassword;
        }

        public Map<String, String> getEncryptionMap() {
            return encryptionMap;
        }
    }
    public Map<String, String> getEncryptionMap(String dbName) {
        if (!databases.containsKey(dbName)) {
            throw new IllegalArgumentException("Database does not exist: " + dbName);
        }
        try (FileReader reader = new FileReader(databasesFile)) {
            Gson gson = new Gson();
            Map<String, Map<String, Object>> allDatabases = gson.fromJson(reader, Map.class);
            Map<String, Object> dbData = allDatabases.get(dbName);
            return (Map<String, String>) dbData.get("encryptionMap");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read databases file.", e);
        }
    }

}
