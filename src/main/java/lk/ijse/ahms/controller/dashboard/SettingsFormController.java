package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ahms.controller.SigninFormController;
import lk.ijse.ahms.controller.add.AddApointmentFormController;
import lk.ijse.ahms.controller.deletePassword.DeleteUserFormController;
import lk.ijse.ahms.controller.forgotPassword.Forgotpass1FormController;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.dto.PetsDto;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.dto.tm.AppointmentTm;
import lk.ijse.ahms.dto.tm.PetsTm;
import lk.ijse.ahms.dto.tm.UserTm;
import lk.ijse.ahms.model.AppointmentModel;
import lk.ijse.ahms.model.EmpModel;
import lk.ijse.ahms.model.PetModel;
import lk.ijse.ahms.model.UserModel;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.awt.Color.red;

public class SettingsFormController {
    public JFXTextField txtUsername;
    public JFXTextField txtPassword1;
    public JFXComboBox<String> cmbEmpId;
    public JFXTextField txtCurPassword;
    public JFXTextField txtnewPass1;
    public JFXTextField txtnewPass2;
    public JFXButton btnSaveNewUser;
    public JFXButton btnchangePass;
    public TableView<UserTm> tblUser;
    public TableColumn colUsername;
    public TableColumn colEmpId;
    public JFXButton btnDelete;
    public JFXTextField txtPassword2;

    public String id;

    public int focusedIndex;

    @Setter
    private SigninFormController signinFormController;

    public ObservableList<UserTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        setCellValueFactoryUser();
        loadAllUsers();
        loadEmployee();

    }

    private void loadEmployee() {
        System.out.println("Loading all Employee");

        ObservableList<String> obList1 = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> idList = EmpModel.getAllEmployee();
            for (EmployeeDto dto : idList) {
                obList1.add(dto.getId());
            }
            cmbEmpId.setItems(obList1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactoryUser() {
        colUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("EmpId"));

    }

    private void loadAllUsers() {
        System.out.println("Loading all Users");

        try {
            List<UserDto> userDtos = UserModel.getAllUsers();

            for (UserDto dto : userDtos) {

                obList.add(
                        new UserTm(
                                dto.getUsername(),
                                dto.getEmpId()
                        )
                );
            }
            tblUser.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void forgotpassOnAction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/forgotPassword/forgotpass1_form.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void changePassOnAction(ActionEvent actionEvent) {
        String username = signinFormController.txtusername.getText();
        String password = txtCurPassword.getText();
        String newpassword = txtnewPass1.getText();
        String newpassword2 = txtnewPass2.getText();

        if (!password.isEmpty() && !newpassword.isEmpty() && !newpassword2.isEmpty()) {
            try {
                UserDto dto = UserModel.searchByName(username);
                if (dto.getPassword().equals(password)) {
                    if (newpassword.equals(newpassword2)) {
                        boolean isChanged = UserModel.changePassword(dto.getUsername(), newpassword);
                        if (isChanged) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Password changed!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Current password is incorrect!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {

                focusedIndex = tblUser.getSelectionModel().getSelectedIndex();
                UserTm selectedItem = (UserTm) tblUser.getSelectionModel().getSelectedItem();

                    id = selectedItem.getUsername();

                try {
                    UserDto dto = UserModel.searchByName(id);

                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/deleteUser/deleteUser_form.fxml"));
                    Parent root = fxmlLoader.load();

                    DeleteUserFormController deleteform =  fxmlLoader.getController();
                    deleteform.setSettingsFormController(this);

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();

                } catch (SQLException | IOException ex) {
                    throw new RuntimeException(ex);
                }


    }

    public void saveUserOnAction(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword1.getText();
        String password2 = txtPassword2.getText();
        String empId = cmbEmpId.getValue();

        if (!username.isEmpty() && !password.isEmpty() && !password2.isEmpty() && !empId.isEmpty()) {
            try {
                UserDto dto = UserModel.searchByName(username);

                if (dto == null) {
                    if (password.equals(password2)) {
                        UserDto dto2 = new UserDto(username, password, empId);
                        boolean isSaved = UserModel.saveUser(dto2);
                        if (isSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "User Saved!").show();
                            initialize();
                            clearFields();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void clearFields() {
        txtUsername.clear();
        txtPassword1.clear();
        txtPassword2.clear();
        cmbEmpId.getSelectionModel().clearSelection();
    }
}
