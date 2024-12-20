package Classes;

import java.util.ArrayList;
import java.util.List;

public class EncryptionStack {
    private final List<EncryptionAlgorithm> algorithms = new ArrayList<>();

    public void addAlgorithm(EncryptionAlgorithm algorithm) {
        algorithms.add(algorithm);
    }

    public String encrypt(String data) {
        String result = data;
        for (EncryptionAlgorithm algorithm : algorithms) {
            result = algorithm.encrypt(result);
        }
        return result;
    }

    public String decrypt(String data) {
        String result = data;
        for (int i = algorithms.size() - 1; i >= 0; i--) {
            result = algorithms.get(i).decrypt(result);
        }
        return result;
    }
}