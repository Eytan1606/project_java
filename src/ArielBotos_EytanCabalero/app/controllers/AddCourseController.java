package ArielBotos_EytanCabalero.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AddCourseController {

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField creditsField;

    @FXML
    private void handleSaveCourse() {
        String name = courseNameField.getText();
        String code = courseCodeField.getText();
        String credits = creditsField.getText();

        if (name.isEmpty() || code.isEmpty() || credits.isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        try {
            int creditValue = Integer.parseInt(credits);
            // כאן אפשר לשמור את הקורס למערכת Persistence או הדפסה לצורך בדיקה
            System.out.println("Course saved: " + name + " (" + code + ") - " + creditValue + " credits");
            showAlert("Success", "Course saved successfully!");
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Credits must be a number.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

