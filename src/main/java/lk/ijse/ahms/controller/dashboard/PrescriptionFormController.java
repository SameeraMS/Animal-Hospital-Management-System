package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.PrescriptionDto;
import lk.ijse.ahms.model.AppointmentModel;
import lk.ijse.ahms.model.PrescriptionModel;

import java.sql.SQLException;
import java.util.List;

public class PrescriptionFormController {


    public JFXTextField txtPresId;
    public JFXComboBox<String> cmbAppointment;
    public JFXTextArea txtDesc;
    public JFXButton btnSave;
    public JFXTextField txtsearch;
    public JFXButton btnupdate;
    public JFXButton btndelete;
    public JFXButton btnClear;


    public  void initialize() {
        generateNextId();
        loadAllAppId();
    }

    private void generateNextId() {
        try {
            String presId = PrescriptionModel.generateNextPresId();
            txtPresId.setText(presId);
            txtPresId.setEditable(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllAppId() {

        try {
            List<AppointmentDto> dto = PrescriptionModel.getAllAppointmentId();

            for (AppointmentDto d : dto) {
                cmbAppointment.getItems().add(d.getAppointmentId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String appointmentId = cmbAppointment.getValue();
        String description = txtDesc.getText();
        String prescriptionId = txtPresId.getText();

        var dto = new PrescriptionDto(prescriptionId, description, appointmentId);

        if (!prescriptionId.isEmpty() && !description.isEmpty() && !appointmentId.isEmpty()) {
            try {
                boolean isSaved = PrescriptionModel.savePrescription(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Prescription Saved Successfully").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Prescription Not Saved").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void clearFields() {
        txtPresId.clear();
        txtDesc.clear();
        cmbAppointment.getSelectionModel().clearSelection();
    }

    public void searchOnAction(ActionEvent actionEvent) {
        searchPrescription();
    }

    private void searchPrescription() {
        String presid = txtsearch.getText();

        try {
            PrescriptionDto dto = PrescriptionModel.searchPrescription(presid);

            if (dto == null) {
                new Alert(Alert.AlertType.ERROR, "Prescription Not Found").show();
            } else {
                txtDesc.setText(dto.getDescription());
                txtPresId.setText(dto.getPrescriptionId());
                cmbAppointment.setValue(dto.getAppointmentId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String presid = txtPresId.getText();
        String description = txtDesc.getText();
        String appointmentId = cmbAppointment.getValue();

        var dto = new PrescriptionDto(presid, description, appointmentId);

        if (!presid.isEmpty() && !description.isEmpty() && !appointmentId.isEmpty()) {
            try {
                boolean isUpdate = PrescriptionModel.updatePrescription(dto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Prescription Updated Successfully").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Prescription Not Updated").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String presid = txtPresId.getText();

        try {
            boolean isDelete = PrescriptionModel.deletePrescription(presid);

            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Prescription Deleted Successfully").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Prescription Not Deleted").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtsearchOnAction(ActionEvent actionEvent) {
        searchPrescription();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
}
