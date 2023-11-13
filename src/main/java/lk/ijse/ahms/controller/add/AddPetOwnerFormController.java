package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import lk.ijse.ahms.controller.dashboard.PetsFormController;
import lk.ijse.ahms.controller.info.InfoPetsFormController;
import lk.ijse.ahms.dto.PetOwnerDto;
import lk.ijse.ahms.model.PetOwnerModel;
import lombok.Setter;

import java.sql.SQLException;

public class AddPetOwnerFormController {
    public JFXTextField ownerId;
    public JFXTextField ownerName;
    public JFXTextField ownerEmail;
    public JFXTextField ownerTel;

    @Setter
    private InfoPetsFormController infoPetFormController;

    @Setter
    private PetsFormController PetFormController;

    @Setter
    private AddPetsFormController addPetFormController;

    public void idOnAction(ActionEvent actionEvent) {
        ownerName.requestFocus();
    }

    public void nameOnAction(ActionEvent actionEvent) {
        ownerEmail.requestFocus();
    }

    public void mailOnAction(ActionEvent actionEvent) {
        ownerTel.requestFocus();
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        ownerId.clear();
        ownerName.clear();
        ownerEmail.clear();
        ownerTel.clear();
    }

    public void telOnAction(ActionEvent actionEvent) {

    }

    public void saveOnAction(ActionEvent actionEvent) {

        String id = ownerId.getText();
        String name = ownerName.getText();
        String email = ownerEmail.getText();
        String tel = ownerTel.getText();

        var dto = new PetOwnerDto(id, name, email, tel);

        if(!id.isEmpty() && !name.isEmpty() && !email.isEmpty() && !tel.isEmpty()) {
            try {
                boolean isSaved = PetOwnerModel.savePetOwner(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Pet Owner saved!").show();
                    clearFields();

                    if(infoPetFormController != null) {
                        infoPetFormController.initialize();
                    } else if(addPetFormController != null) {
                        addPetFormController.initialize();
                    }

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
}
