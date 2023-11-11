package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import lk.ijse.ahms.controller.DashboardControlsController;
import lk.ijse.ahms.controller.dashboard.DashboardController;
import lk.ijse.ahms.controller.dashboard.EmployeeFormController;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.model.EmpModel;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddEmployeeFormController {
    @FXML
    private JFXTextField empId;

    @FXML
    private JFXTextField empName;

    @FXML
    private JFXTextField empAddress;

    @FXML
    private JFXTextField empTel;

    @FXML
    private JFXTextField empMail;

    @FXML
    private JFXComboBox<String> cmbEmpType;


    public void initialize() {
        loadCmbBox();
    }

    @Setter
    private EmployeeFormController empFormController;


    private void loadCmbBox() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Helper");
        obList.add("Cleaner");

        cmbEmpType.setItems(obList);
    }

    private void clearfields() {
        empId.clear();
        empName.clear();
        empAddress.clear();
        empTel.clear();
        empMail.clear();
        cmbEmpType.getSelectionModel().clearSelection();
    }


    public void saveOnAction(javafx.event.ActionEvent actionEvent) {
        String id = empId.getText();
        String name = empName.getText();
        String address = empAddress.getText();
        String tel = empTel.getText();
        String mail = empMail.getText();
        String type = cmbEmpType.getSelectionModel().getSelectedItem();

        var dto = new EmployeeDto(id, name, address, tel, mail, type);

        if (!id.isEmpty() && !name.isEmpty() && !address.isEmpty() && !tel.isEmpty() && !mail.isEmpty() && !type.isEmpty()) {
            try {
                boolean isSaved = EmpModel.saveEmployee(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    clearfields();
                    empFormController.initialize();

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "All fields are required!").show();
        }
    }

    public void clearOnAction(javafx.event.ActionEvent actionEvent) {
        clearfields();
    }

    public void idOnAction(ActionEvent actionEvent) {
        empName.requestFocus();
    }

    public void nameOnAction(ActionEvent actionEvent) {
        empAddress.requestFocus();
    }

    public void addressOnAction(ActionEvent actionEvent) {
        empTel.requestFocus();
    }

    public void telOnAction(ActionEvent actionEvent) {
        empMail.requestFocus();
    }

    public void mailOnAction(ActionEvent actionEvent) {
      //  cmbEmpType.requestFocus();
    }
}
