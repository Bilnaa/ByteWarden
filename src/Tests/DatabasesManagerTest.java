package Tests;

import Classes.DatabasesManager;
import Classes.Sha256;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatabasesManagerTest {

    private static File testFile;
    private DatabasesManager manager;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException {
        testFile = new File("testDatabases.json");
        if (testFile.exists()) {
            testFile.delete();
        }
        testFile.createNewFile();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Before
    public void setUp() {
        manager = new DatabasesManager(testFile);
    }

    @After
    public void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testCreateDatabase() {
        String dbName = "TestDB";
        String password = "password123";
        Map<String, String> encryptionMap = new HashMap<>();
        encryptionMap.put("key1", "value1");

        manager.createDatabase(dbName, password, encryptionMap);

        assertTrue(manager.verifyDatabase(dbName, password));
        assertEquals(encryptionMap, manager.getEncryptionMap(dbName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateDatabaseDuplicate() {
        String dbName = "TestDB";
        String password = "password123";
        Map<String, String> encryptionMap = new HashMap<>();

        manager.createDatabase(dbName, password, encryptionMap);
        manager.createDatabase(dbName, password, encryptionMap);
    }

    @Test
    public void testVerifyDatabase() {
        String dbName = "TestDB";
        String password = "password123";
        Map<String, String> encryptionMap = new HashMap<>();

        manager.createDatabase(dbName, password, encryptionMap);

        assertTrue(manager.verifyDatabase(dbName, password));
        assertFalse(manager.verifyDatabase(dbName, "wrongPassword"));
        assertFalse(manager.verifyDatabase("NonExistentDB", password));
    }

    @Test
    public void testLoadDatabases() throws IOException {
        // Pre-populate the test file with data
        String dbName = "TestDB";
        Sha256 sha256 = new Sha256();
        String passwordHash = sha256    .calculateHash("password123");
        Map<String, String> encryptionMap = new HashMap<>();
        encryptionMap.put("key1", "value1");

        Map<String, DatabasesManager.Database> preloadedData = new HashMap<>();
        preloadedData.put(dbName, new DatabasesManager.Database(dbName, passwordHash, encryptionMap));

        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(preloadedData));
        }

        manager = new DatabasesManager(testFile);

        assertTrue(manager.verifyDatabase(dbName, "password123"));
        assertEquals(encryptionMap, manager.getEncryptionMap(dbName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEncryptionMapForNonExistentDatabase() {
        manager.getEncryptionMap("NonExistentDB");
    }
}
