package ArielBotos_EytanCabalero2;

public class NullInputException extends RuntimeException {
    public NullInputException() {
        super("Input cannot be empty.");
    }

    public NullInputException(String name) {
        super(name + " cannot be empty, please try again.");
    }
}
