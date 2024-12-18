package Classes;

public abstract class Hash {
    public abstract String calculateHash(String input);

    public void printHash(String input) {
        System.out.println("Hash de \"" + input + "\": " + calculateHash(input));
    }
}


