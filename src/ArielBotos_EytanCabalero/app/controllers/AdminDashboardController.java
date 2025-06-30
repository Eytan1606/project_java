package ArielBotos_EytanCabalero.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private void addCourse() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArielBotos_EytanCaballero/app/views/add_course.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Course");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void addLecturer() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArielBotos_EytanCaballero/app/views/add_lecturer.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Lecturer");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void viewAllStudents() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArielBotos_EytanCaballero/app/views/view_students.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("View All Students");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void logout() {
        System.out.println("Logging out...");
        // בעתיד תוכל לסגור את החלון או לעבור למסך login
    }


    private void loadScene(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ArielBotos_EytanCaballero/app/views/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

