package lk.ijse.ahms.controller.deletePassword;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import lk.ijse.ahms.controller.dashboard.SettingsFormController;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.model.UserModel;
import lombok.Setter;

import java.sql.SQLException;
import java.util.Optional;

public class DeleteUserFormController {
    public JFXTextField txtPass;
    public JFXButton btnDelete;

    @Setter
    private SettingsFormController settingsFormController;

    public void deleteOnAction(ActionEvent actionEvent) {

        btnDelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {

                String password = txtPass.getText();
                String id = settingsFormController.id;

                try {
                    UserDto dto = UserModel.searchByName(id);

                    UserDto dto1 = UserModel.searchByName("sameerams2002@gmail.com");

                    if(password.equals(dto.getPassword()) | password.equals(dto1.getPassword())) {
                        boolean isDelete = UserModel.deleteUser(id);

                        if (isDelete) {
                            new Alert(Alert.AlertType.CONFIRMATION, "User Deleted!").show();
                            settingsFormController.initialize();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Wrong Password").show();
                            txtPass.setStyle("-fx-border-width: 5px; -fx-background-color: #e74c3c;");
                            clearFields();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Wrong Password").show();
                        txtPass.setStyle("-fx-border-width: 5px; -fx-background-color: #e74c3c;");
                        clearFields();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            });

    }

    private void clearFields() {
        txtPass.clear();
    }
}
