package ArielBotos_EytanCabalero.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AddLecturerController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField idField;

    @FXML
    private TextField departmentField;

    @FXML
    private void handleSaveLecturer() {
        String name = nameField.getText();
        String id = idField.getText();
        String department = departmentField.getText();

        if (name.isEmpty() || id.isEmpty() || department.isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        // כאן תוכל להוסיף את המרצה למסד הנתונים או לרשימה
        System.out.println("Lecturer saved: " + name + " (" + id + ") - " + department);
        showAlert("Success", "Lecturer saved successfully!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
