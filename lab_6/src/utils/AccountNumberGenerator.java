package utils;

public class AccountNumberGenerator {
    public static String generate() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}
