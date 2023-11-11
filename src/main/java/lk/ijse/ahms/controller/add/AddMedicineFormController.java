package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import lk.ijse.ahms.controller.dashboard.EmployeeFormController;
import lk.ijse.ahms.controller.dashboard.MedicineFormcontroller;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.model.MedModel;
import lombok.Setter;

import java.sql.SQLException;

public class AddMedicineFormController {

    public JFXTextField medqty;
    @FXML
    private JFXTextField medId;

    @FXML
    private JFXTextField medName;

    @FXML
    private JFXTextField medPrice;

    @FXML
    private JFXComboBox<String> cmbType;

    @FXML
    private JFXTextField medDesc;

    @FXML
    private JFXTextField medExpDate;

    @Setter
    private MedicineFormcontroller medFormController;

    public void initialize() {
        loadCmbBox();
    }

    private void loadCmbBox() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Medicine");
        obList.add("Injection");

        cmbType.setItems(obList);
    }

    public void idOnAction(ActionEvent actionEvent) {
        medName.requestFocus();
    }

    public void priceOnAction(ActionEvent actionEvent) {
        medDesc.requestFocus();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = medId.getText();
        String name = medName.getText();
        String type = cmbType.getSelectionModel().getSelectedItem();
        String price = medPrice.getText();
        String desc = medDesc.getText();
        String expDate = medExpDate.getText();
        String qty = medqty.getText();

        var dto = new MedicineDto(id, name, type, qty, price, desc, expDate );

        if(!id.isEmpty() && !name.isEmpty() && !type.isEmpty() && !price.isEmpty() && !desc.isEmpty() && !expDate.isEmpty() && !qty.isEmpty()) {
            try {
                boolean isSaved = MedModel.saveMedicine(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Medicine saved!").show();
                    clearFields();
                    medFormController.initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "All fields are required!").show();
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        medId.clear();
        medName.clear();
        medPrice.clear();
        cmbType.getSelectionModel().clearSelection();
        medDesc.clear();
        medExpDate.clear();
        medqty.clear();
    }

    public void descOnAction(ActionEvent actionEvent) {
        medExpDate.requestFocus();
    }


}
