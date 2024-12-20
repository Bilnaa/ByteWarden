package Classes;

import Classes.Enigma.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class SiteManager {
    private final File dbFile;
    private final List<Map<String, String>> sites;
    private String encryptionAlgorithm;
    private final Map<String, Object> testParameters;
    private boolean isTestMode;

    public SiteManager(File dbFile) {
        this.dbFile = dbFile;
        this.testParameters = new HashMap<>();
        this.isTestMode = false;
        initializeDatabase();
        this.sites = loadSites();
    }

    private void initializeDatabase() {
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
                saveSites();
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize database", e);
            }
        }
    }

    public void configureTestMode(boolean enabled, String algorithm, Object... params) {
        this.isTestMode = enabled;
        this.encryptionAlgorithm = algorithm;
        if (params != null && params.length > 0) {
            configureTestParameters(algorithm, params);
        }
    }

    private void configureTestParameters(String algorithm, Object... params) {
        testParameters.clear();
        if ("Enigma".equals(algorithm)) {
            if (params.length != 3) {
                throw new IllegalArgumentException("Enigma requires rotors, reflector, and plugboard parameters");
            }
            Rotors rotors = new Rotors();
            if (params[0] instanceof char[]) {
                rotors.setPositions((char[]) params[0]);
            }
            testParameters.put("rotors", rotors);
            testParameters.put("reflector", new Reflector());
            Plugboard plugboard = new Plugboard();
            if (params[2] instanceof String[][]) {
                for (String[] pair : (String[][]) params[2]) {
                    if (pair.length == 2) {
                        plugboard.connect(pair[0].charAt(0), pair[1].charAt(0));
                    }
                }
            }
            testParameters.put("plugboard", plugboard);
        }
    }

    public void addSite(String siteName, String username, String password) {
        validateEncryptionAlgorithm();
        Map<String, String> site = new HashMap<>();
        site.put("siteName", siteName);
        site.put("username", username);
        site.put("password", encryptPassword(password, null));
        sites.add(site);
        saveSites();
    }

    public void modifySite(String siteName, String newUsername, String newPassword) {
        sites.stream()
            .filter(site -> site.get("siteName").equals(siteName))
            .findFirst()
            .ifPresent(site -> {
                if (newUsername != null && !newUsername.isEmpty()) {
                    site.put("username", newUsername);
                }
                if (newPassword != null && !newPassword.isEmpty()) {
                    site.put("password", encryptPassword(newPassword, null));
                }
                saveSites();
            });
    }

    public void deleteSite(String siteName) {
        sites.removeIf(site -> site.get("siteName").equals(siteName));
        saveSites();
    }

    private String encryptPassword(String password, Scanner scanner) {
        validateEncryptionAlgorithm();
        if (isTestMode) {
            return performTestModeEncryption(password);
        }
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return performNormalEncryption(password, scanner);
    }

    private String performTestModeEncryption(String password) {
        if ("Enigma".equals(encryptionAlgorithm)) {
            Rotors rotors = (Rotors) testParameters.get("rotors");
            Reflector reflector = (Reflector) testParameters.get("reflector");
            Plugboard plugboard = (Plugboard) testParameters.get("plugboard");
            
            if (rotors == null || reflector == null || plugboard == null) {
                throw new IllegalStateException("Enigma components not properly configured for test mode");
            }
            
            Enigma enigmaMachine = new Enigma(rotors, reflector, plugboard);
            return enigmaMachine.encrypt(password);
        }
        throw new IllegalStateException("Unknown encryption algorithm: " + encryptionAlgorithm);
    }

    private String performNormalEncryption(String password, Scanner scanner) {
        if ("Enigma".equals(encryptionAlgorithm)) {
            Rotors rotors = new Rotors();
            Reflector reflector = new Reflector();
            Plugboard plugboard = new Plugboard();

            System.out.print("Enter the initial positions for rotors (3 letters, e.g., ABC): ");
            String rotorPositions = scanner.nextLine().trim().toUpperCase();
            if (rotorPositions.length() != 3) {
                throw new IllegalArgumentException("Must provide exactly 3 positions for rotors");
            }
            rotors.setPositions(rotorPositions.toCharArray());

            System.out.print("Enter plugboard connections (pairs of letters separated by space, e.g., AB CD EF): ");
            String plugboardConfig = scanner.nextLine().trim().toUpperCase();
            if (!plugboardConfig.isEmpty()) {
                String[] pairs = plugboardConfig.split(" ");
                for (String pair : pairs) {
                    if (pair.length() == 2) {
                        plugboard.connect(pair.charAt(0), pair.charAt(1));
                    }
                }
            }

            Enigma enigmaMachine = new Enigma(rotors, reflector, plugboard);
            return enigmaMachine.encrypt(password);
        }
        throw new IllegalStateException("Unknown encryption algorithm: " + encryptionAlgorithm);
    }

    private List<Map<String, String>> loadSites() {
        if (!dbFile.exists()) return new ArrayList<>();

        try (FileReader fileReader = new FileReader(dbFile)) {
            Type mapType = new TypeToken<List<Map<String, String>>>(){}.getType();
            List<Map<String, String>> loadedSites = new Gson().fromJson(fileReader, mapType);
            return loadedSites != null ? loadedSites : new ArrayList<>();
        } catch (IOException | JsonSyntaxException e) {
            throw new RuntimeException("Error reading sites from file", e);
        }
    }

    public void saveSites() {
        try (FileWriter fileWriter = new FileWriter(dbFile)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(sites, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException("Error saving sites to file", e);
        }
    }

    private void validateEncryptionAlgorithm() {
        if (encryptionAlgorithm == null) {
            throw new IllegalStateException("Encryption algorithm is not set");
        }
    }

    public List<Map<String, String>> getSites() {
        return new ArrayList<>(sites);
    }

    public void setEncryptionAlgorithm(String algorithm) {
        this.encryptionAlgorithm = algorithm;
    }
    public void setTestMode(boolean enabled) {
        this.isTestMode = enabled;
    }
    
    public void setTestParameters(String algorithm) {
        this.encryptionAlgorithm = algorithm;
    }

    public void encryptSites(String algorithm) {
        if ("Enigma".equals(algorithm)) {
            List<Map<String, String>> encryptedSites = new ArrayList<>();
            for (Map<String, String> site : sites) {
                Map<String, String> encryptedSite = new HashMap<>(site);
                String password = site.get("password");
                if (password != null && !password.isEmpty()) {
                    encryptedSite.put("password", encryptPassword(password, null));
                }
                encryptedSites.add(encryptedSite);
            }
            sites.clear();
            sites.addAll(encryptedSites);
            saveSites();
        } else {
            throw new IllegalArgumentException("Unsupported encryption algorithm: " + algorithm);
        }
    }
}