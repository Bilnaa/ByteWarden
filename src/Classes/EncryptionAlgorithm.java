package Classes;

public interface EncryptionAlgorithm {
    void init(String key);
    String encrypt(String data);
    String decrypt(String data);
}