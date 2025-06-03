package ArielBotos_EytanCabalero2;

public class DuplicateEntityException extends AppException {
  public DuplicateEntityException(String entityType, String entityName) {
    super(String.format("%s '%s' already exists.", entityType, entityName));
  }
}