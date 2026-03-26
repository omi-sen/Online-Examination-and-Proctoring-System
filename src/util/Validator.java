package util;

public class Validator {
    public boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
