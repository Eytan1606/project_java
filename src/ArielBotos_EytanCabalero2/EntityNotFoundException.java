package ArielBotos_EytanCabalero2;

public class EntityNotFoundException extends AppException {
    public EntityNotFoundException(String entityType, String entityName) {
        super(String.format("%s '%s' not found.", entityType, entityName));
    }
}
