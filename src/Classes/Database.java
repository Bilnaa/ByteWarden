package Classes;

public class Database{
    private final String hashedPassword;
    private final String databaseName;
    private final String encryptionMode;


    public Database (String databaseName, String hashedPassword, String encryptionMode) {
        this.databaseName = databaseName;
        this.hashedPassword = hashedPassword;
        this.encryptionMode = encryptionMode;
    }




}
