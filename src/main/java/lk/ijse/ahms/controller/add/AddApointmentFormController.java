package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import lk.ijse.ahms.controller.dashboard.AppointmentFormController;
import lombok.Setter;


public class AddApointmentFormController {

    public JFXTextField appointmentId;
    public JFXComboBox cmbDocId;
    public Label lblTime;
    public Label lblDate;
    public JFXTextField petOwnerName;
    public JFXComboBox cmbPetId;
    public JFXComboBox cmbPetOwnerId;
    public JFXTextField petName;
    public JFXTextArea desc;
    public JFXTextField docName;
    public Label lblAmount;
    @Setter
    private AppointmentFormController appointmentFormController;

    public void DocIdOnAction(ActionEvent actionEvent) {
    }

    public void cmbPetIdOnAction(ActionEvent actionEvent) {
    }

    public void cmbPetOwnerIdOnAction(ActionEvent actionEvent) {
    }

    public void makeAppointmentOnAction(ActionEvent actionEvent) {
    }

    public void clearOnAction(ActionEvent actionEvent) {
    }
}
