package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.model.MedModel;

import java.sql.SQLException;

public class AddMedicineFormController {
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

        var dto = new MedicineDto(id, name, type, price, desc, expDate);

        try {
            boolean isSaved = MedModel.saveMedicine(dto);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
    }

    public void descOnAction(ActionEvent actionEvent) {
        medExpDate.requestFocus();
    }
}
