package sk.kuchta.eshop.api.exception;

public class ResourceExistInDatabase extends RuntimeException {
    public ResourceExistInDatabase(String message) {
        super(message);
    }
}
