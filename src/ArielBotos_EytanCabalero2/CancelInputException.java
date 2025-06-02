package ArielBotos_EytanCabalero2;

public class CancelInputException extends RuntimeException {
    public CancelInputException() {
        super("Operation cancelled.");
    }
}
