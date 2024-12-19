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
    private final Map<String, String> databases; // A map holding the database names as keys and their hashed passwords as values
    private final Sha256 sha256 = new Sha256(); // Instance of the Sha256 class for password hashing

    // Constructor initializing the file and loading the existing databases
    public DatabasesManager(File databasesFile) {
        this.databasesFile = databasesFile;
        this.databases = loadDatabases(); // Load existing databases from file
    }

    // Function to verify if the provided password matches the stored password for a database
    public boolean verifyDatabase(String dbName, String password) {
        String hashedPassword = sha256.calculateHash(password); // Hash the provided password
        // Check if the database exists and the hashed password matches the stored one
        return databases.containsKey(dbName) && databases.get(dbName).equals(hashedPassword);
    }

    // Function to create a new database with a hashed password
    public void createDatabase(String dbName, String password) {
        // If the database already exists, throw an exception
        if (databases.containsKey(dbName)) {
            throw new IllegalArgumentException("Database already exists.");
        }
        // Hash the password and add the new database to the map
        String hashedPassword = sha256.calculateHash(password);
        databases.put(dbName, hashedPassword);
        saveDatabases(); // Save the updated databases list to the file
    }

    // Function to load the databases from the JSON file
    public Map<String, String> loadDatabases() {
        // If the file doesn't exist, return an empty map
        if (!databasesFile.exists()) return new HashMap<>();
        try (FileReader reader = new FileReader(databasesFile)) {
            // Use Gson to parse the JSON file into a map of database names and passwords
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {}.getType(); // Define the type for the map
            return gson.fromJson(reader, type); // Return the map of databases
        } catch (IOException e) {
            return new HashMap<>(); // Return an empty map if there was an error reading the file
        }
    }

    // Function to save the databases and their hashed passwords to the JSON file
    private void saveDatabases() {
        try (FileWriter writer = new FileWriter(databasesFile)) {
            // Use Gson to convert the map of databases to a JSON format and write it to the file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(databases, writer);
        } catch (IOException e) {
            e.printStackTrace(); // Print the error if there is an issue writing to the file
        }
    }
}
