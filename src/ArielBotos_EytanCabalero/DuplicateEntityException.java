package ArielBotos_EytanCabalero;

public class DuplicateEntityException extends AppException {
  public DuplicateEntityException(String entityType, String entityName) {
    super(String.format("%s '%s' already exists.", entityType, entityName));
  }
}