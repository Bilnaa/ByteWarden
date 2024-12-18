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
    private final Map<String, String> databases;

    public DatabasesManager(File databasesFile) {
        this.databasesFile = databasesFile;
        this.databases = loadDatabases();
    }

    public boolean verifyDatabase(String dbName, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password);
        return databases.containsKey(dbName) && databases.get(dbName).equals(hashedPassword);
    }

    public void createDatabase(String dbName, String password) {
        if (databases.containsKey(dbName)) {
            throw new IllegalArgumentException("Database already exists.");
        }
        String hashedPassword = PasswordUtils.hashPassword(password);
        databases.put(dbName, hashedPassword);
        saveDatabases();
    }

    public Map<String, String> loadDatabases() {
        if (!databasesFile.exists()) return new HashMap<>();
        try (FileReader reader = new FileReader(databasesFile)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
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
}
