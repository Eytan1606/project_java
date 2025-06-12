package ArielBotos_EytanCabalero;

public class NullInputException extends RuntimeException {
    public NullInputException() {
        super("Input cannot be empty.");
    }
}
