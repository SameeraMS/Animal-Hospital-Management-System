package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.ahms.controller.dashboard.PetsFormController;
import lombok.Setter;

import java.io.IOException;

public class AddPetsFormController {
    public JFXTextField petId;
    public JFXTextField petName;
    public JFXTextField petAge;
    public JFXComboBox<String> cmbOwnerId;
    public JFXTextField petType;
    public JFXComboBox<String> cmbGender;

    @Setter
    private PetsFormController petsFormController;

    public void idOnAction(ActionEvent actionEvent) {
    }



    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void clearOnAction(ActionEvent actionEvent) {
    }

    public void addOwnerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPetOwner_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetOwnerFormController ownr =  fxmlLoader.getController();
        ownr.setPetFormController(petsFormController);
        ownr.setAddPetFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
