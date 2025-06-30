package ArielBotos_EytanCabalero.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class ViewStudentsController {

    @FXML
    private TableView<?> studentsTable;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> degreeColumn;

    @FXML
    private void initialize() {
        // כאן תוכל להגדיר את העמודות ולהזין נתונים אם תשתמש במודל סטודנט
    }
}
