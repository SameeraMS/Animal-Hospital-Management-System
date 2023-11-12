package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import lk.ijse.ahms.controller.dashboard.EmployeeFormController;
import lk.ijse.ahms.controller.dashboard.PetsFormController;
import lk.ijse.ahms.controller.info.InfoPetsFormController;
import lombok.Setter;

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
    }

    public void nameOnAction(ActionEvent actionEvent) {
    }

    public void mailOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void clearOnAction(ActionEvent actionEvent) {
    }

    public void telOnAction(ActionEvent actionEvent) {
    }
}
