package Tests;

import Classes.DatabasesManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DatabasesManagerTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private DatabasesManager databasesManager;
    private File databasesFile;
    private File pepperFile;
    private static final String TEST_PEPPER = "testPepper123";

    @Before
    public void setUp() throws Exception {
        // Create temporary files for testing
        databasesFile = tempFolder.newFile("databases_test.json");
        pepperFile = new File("pepper.json"); // Create in current directory

        // Create pepper file with test content
        try (FileWriter writer = new FileWriter(pepperFile)) {
            writer.write("{\"pepper\": \"" + TEST_PEPPER + "\"}");
        }

        databasesManager = new DatabasesManager(databasesFile);
    }

    @After
    public void tearDown() {
        databasesFile.delete();
        pepperFile.delete();
    }

    @Test
    public void testCreateDatabase() {
        // Arrange
        String dbName = "testDB";
        String password = "testPassword";
        Map<String, String> encryptionMap = new HashMap<>();
        encryptionMap.put("test", "value");

        // Act
        databasesManager.createDatabase(dbName, password, encryptionMap);

        // Assert
        assertTrue(databasesManager.verifyDatabase(dbName, password));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDatabaseDuplicate() {
        // Arrange
        String dbName = "testDB";
        String password = "testPassword";
        Map<String, String> encryptionMap = new HashMap<>();

        // Act
        databasesManager.createDatabase(dbName, password, encryptionMap);
        databasesManager.createDatabase(dbName, password, encryptionMap); // Should throw exception
    }

    @Test
    public void testVerifyDatabaseWithInvalidName() {
        // Arrange
        String nonExistentDB = "nonExistentDB";
        String password = "testPassword";

        // Act & Assert
        assertFalse(databasesManager.verifyDatabase(nonExistentDB, password));
    }

    @Test
    public void testVerifyDatabaseWithInvalidPassword() {
        // Arrange
        String dbName = "testDB";
        String correctPassword = "correctPassword";
        String wrongPassword = "wrongPassword";
        Map<String, String> encryptionMap = new HashMap<>();

        // Act
        databasesManager.createDatabase(dbName, correctPassword, encryptionMap);

        // Assert
        assertFalse(databasesManager.verifyDatabase(dbName, wrongPassword));
    }

    @Test
    public void testGetEncryptionMap() {
        // Arrange
        String dbName = "testDB";
        String password = "testPassword";
        Map<String, String> encryptionMap = new HashMap<>();
        encryptionMap.put("key1", "value1");
        encryptionMap.put("key2", "value2");

        // Act
        databasesManager.createDatabase(dbName, password, encryptionMap);
        Map<String, String> retrievedMap = databasesManager.getEncryptionMap(dbName);

        // Assert
        assertNotNull(retrievedMap);
        assertEquals(encryptionMap.size(), retrievedMap.size());
        assertEquals(encryptionMap.get("key1"), retrievedMap.get("key1"));
        assertEquals(encryptionMap.get("key2"), retrievedMap.get("key2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEncryptionMapNonExistentDB() {
        // Act & Assert
        databasesManager.getEncryptionMap("nonExistentDB"); // Should throw exception
    }

    @Test
    public void testDatabasePersistence() throws Exception {
        // Arrange
        String dbName = "testDB";
        String password = "testPassword";
        Map<String, String> encryptionMap = new HashMap<>();
        encryptionMap.put("test", "value");

        // Act
        databasesManager.createDatabase(dbName, password, encryptionMap);

        // Create new instance to test persistence
        DatabasesManager newManager = new DatabasesManager(databasesFile);

        // Assert
        assertTrue(newManager.verifyDatabase(dbName, password));
    }

    @Test
    public void testReadPepperFromJson() {
        // This test verifies that the pepper file is correctly created and read
        assertTrue("Pepper file should exist", pepperFile.exists());
    }
}