package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.ahms.controller.dashboard.EmployeeFormController;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.model.DocModel;
import lk.ijse.ahms.model.EmpModel;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.sql.SQLException;

public class AddDocFormcontroller {
    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField tel;

    @FXML
    private JFXTextField email;

    @Setter
    private EmployeeFormController empFormController;

    public void initialize() {
        generateNextId();

    }

    private void generateNextId() {
        try {
            String payId = DocModel.generateNextDocId();
            id.setText(payId);
            id.setEditable(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void nameOnAction(ActionEvent actionEvent) {
        tel.requestFocus();
    }

    public void telOnAction(ActionEvent actionEvent) {
        email.requestFocus();
    }

    public void emailOnAction(ActionEvent actionEvent) {
      //  saveOnAction(actionEvent);
    }

    public void idOnAction(ActionEvent actionEvent) {
        name.requestFocus();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        claerFields();
    }

    private void claerFields() {
        id.clear();
        name.clear();
        tel.clear();
        email.clear();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String nid = id.getText();
        String nname = name.getText();
        String ntel = tel.getText();
        String mail = email.getText();

        var dto = new DoctorDto(nid, nname, ntel, mail);

        if (!nid.isEmpty() && !nname.isEmpty() && !ntel.isEmpty() && !mail.isEmpty()) {
            {
                try {
                    boolean isSaved = DocModel.saveDoctor(dto);

                    if (isSaved) {
                       // new Alert(Alert.AlertType.CONFIRMATION, "Doctor saved!").show();
                        new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Doctor saved Successfully..!", ButtonType.OK).show();
                        claerFields();
                        empFormController.initialize();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        } else {
            new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Fill All Details..!", ButtonType.OK).show();
        }
    }
}
