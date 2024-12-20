package Classes;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        this.databases = loadDatabases() != null ? loadDatabases() : new HashMap<>();
    }
    //Verify the hashed password with the password entered by the user
    public boolean verifyDatabase(String dbName, String password) {
        if (!databases.containsKey(dbName)) return false;
        Database database = databases.get(dbName);
        String pepper = readPepperFromJson("pepper.json");

        String hashedPassword = sha256.calculateHash(database.getSalt() + password + pepper);
        return database.getHashPassword().equals(hashedPassword);
    }
    //create database object with user inputs
    public void createDatabase(String dbName, String password, Map<String, String> encryptionMap) {
        if (databases.containsKey(dbName)) {
            throw new IllegalArgumentException("Database already exists.");
        }
        String pepper = readPepperFromJson("pepper.json");
        String salt = PasswordUtils.generateRandomPassword(12);
        String hashedPassword = sha256.calculateHash(salt + password + pepper);
        Database newDatabase = new Database(dbName, hashedPassword, encryptionMap, salt);
        databases.put(dbName, newDatabase);
        saveDatabases();
    }

    private String readPepperFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> jsonMap = objectMapper.readValue(new File(filePath), Map.class);
            return jsonMap.get("pepper");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
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

    // class database to make the database manager easy to use
    public static class Database {
        private final String name;
        private final String hashPassword;
        private final Map<String, String> encryptionMap;
        private final String salt;
        public Database(String name, String hashPassword, Map<String, String> encryptionMap, String salt) {
            this.name = name;
            this.hashPassword = hashPassword;
            this.encryptionMap = encryptionMap;
            this.salt = salt;
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
        public String getSalt(){
            return salt;
        }


    }
    //method to get the encryption map for the encryption and decryption during the database user selection
    public Map<String, String> getEncryptionMap(String dbName) {
        if (!databases.containsKey(dbName)) { // if the database name doesn't exist
            throw new IllegalArgumentException("Database does not exist: " + dbName);
        }
        try (FileReader reader = new FileReader(databasesFile)) {
            Gson gson = new Gson();
            Map<String, Map<String, Object>> allDatabases = gson.fromJson(reader, Map.class);  //get all the databases info from databases.json
            Map<String, Object> dbData = allDatabases.get(dbName); //get the wanted database
            return (Map<String, String>) dbData.get("encryptionMap"); // get all the encryption methods from the current database
        } catch (IOException e) {
            throw new RuntimeException("Failed to read databases file.", e);
        }
    }

}
