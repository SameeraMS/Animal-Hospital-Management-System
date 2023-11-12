package lk.ijse.ahms.controller.info;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.ahms.controller.add.AddEmployeeFormController;
import lk.ijse.ahms.controller.add.AddPetOwnerFormController;
import lk.ijse.ahms.controller.dashboard.PetsFormController;
import lombok.Setter;

import java.io.IOException;

public class InfoPetsFormController {
    public JFXButton btndelete;
    public JFXButton btnUpdate;
    public JFXButton btnEdit;

    public JFXTextField petName;
    public JFXTextField petAge;
    public JFXComboBox cmbOwnerId;
    public JFXTextField petType;
    public JFXButton btnAdd;
    public JFXComboBox cmbPetGender;
    public JFXTextField ownerName;
    public JFXTextField ownerMail;
    public JFXTextField ownerTel;
    public JFXComboBox cmbPetId;

    @Setter
    private PetsFormController petsFormController;

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void upDateOnAction(ActionEvent actionEvent) {
    }

    public void editOnAction(ActionEvent actionEvent) {
    }

    public void addOwnerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPetOwner_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetOwnerFormController ownr =  fxmlLoader.getController();
        ownr.setInfoPetFormController(this);
        ownr.setPetFormController(petsFormController);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void cmbPetIdOnAction(ActionEvent actionEvent) {
    }
}
