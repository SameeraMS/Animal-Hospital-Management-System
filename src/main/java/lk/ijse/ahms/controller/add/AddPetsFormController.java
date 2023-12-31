package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lk.ijse.ahms.controller.dashboard.PetsFormController;
import lk.ijse.ahms.dto.PetOwnerDto;
import lk.ijse.ahms.dto.PetsDto;
import lk.ijse.ahms.model.PetModel;
import lk.ijse.ahms.model.PetOwnerModel;
import lk.ijse.ahms.regex.Regex;
import lk.ijse.ahms.util.SystemAlert;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddPetsFormController {
    public JFXTextField petId;
    public JFXTextField petName;
    public JFXTextField petAge;
    public JFXComboBox<String> cmbOwnerId;
    public JFXTextField petType;
    public JFXComboBox<String> cmbGender;

    @Setter
    private PetsFormController petsFormController;

    @Setter
    private AddApointmentFormController addApointmentFormController;

    public void initialize() {
        generatenextId();
        loadAllOwnerstoComboBox();
        loadAllGender();

    }

    private void generatenextId() {
        try {
            String payId = PetModel.generateNextId();
            petId.setText(payId);
            petId.setEditable(false);
        } catch (SQLException e) {
            new SystemAlert(Alert.AlertType.ERROR,"Error", e.getMessage(), ButtonType.OK).show();
        }
    }

    private void loadAllGender() {
        cmbGender.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    private void loadAllOwnerstoComboBox() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<PetOwnerDto> idList = PetOwnerModel.getAllOwners();

            for (PetOwnerDto dto : idList) {
                obList.add(dto.getOwnerId());
            }

            cmbOwnerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void idOnAction(ActionEvent actionEvent) {

    }



    public void saveOnAction(ActionEvent actionEvent) {

        if(Regex.getNamePattern().matcher(petName.getText()).matches()){
            if(Regex.getIntPattern().matcher(petAge.getText()).matches()){

                String id = petId.getText();
                String name = petName.getText();
                String age = petAge.getText();
                String ownerId = cmbOwnerId.getValue();
                String type = petType.getText();
                String gender = cmbGender.getValue();

                var dto = new PetsDto(id, name, age, gender, type, ownerId);

                if(!id.isEmpty() && !name.isEmpty() && !age.isEmpty() && !ownerId.isEmpty() && !type.isEmpty() && !gender.isEmpty()) {
                    try {
                        boolean isSaved = PetModel.savePet(dto);

                        if (isSaved) {
                            //      new Alert(Alert.AlertType.CONFIRMATION, "Pet saved!").show();
                            new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Pet saved Successfully..!", ButtonType.OK).show();
                            clearFields();

                            if (petsFormController != null) {
                                petsFormController.initialize();
                            } else if (addApointmentFormController != null) {
                                addApointmentFormController.initialize();
                            }
                            initialize();
                        }
                    } catch (SQLException e) {
                        new SystemAlert(Alert.AlertType.ERROR,"Error", e.getMessage(), ButtonType.OK).show();
                    }
                } else {
                    new SystemAlert(Alert.AlertType.INFORMATION,"Information","Please Fill All Details..!", ButtonType.OK).show();
                }

            }else {
                new SystemAlert(Alert.AlertType.INFORMATION,"Information","Invalid Age!..!", ButtonType.OK).show();
            }
        }else {
            new SystemAlert(Alert.AlertType.INFORMATION,"Information","Invalid Name!..!", ButtonType.OK).show();
        }
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        petId.clear();
        petName.clear();
        petAge.clear();
        cmbOwnerId.getSelectionModel().clearSelection();
        petType.clear();
        cmbGender.getSelectionModel().clearSelection();
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
