package lk.ijse.ahms.controller.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.ahms.controller.dashboard.AppointmentFormController;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dto.PetOwnerDto;
import lk.ijse.ahms.dto.PetsDto;
import lk.ijse.ahms.model.AppointmentModel;
import lk.ijse.ahms.model.DocModel;
import lk.ijse.ahms.model.PetModel;
import lk.ijse.ahms.model.PetOwnerModel;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class AddApointmentFormController {

    public JFXTextField appointmentId;
    public JFXComboBox<String> cmbDocId;
    public Label lblTime;
    public Label lblDate;
    public JFXTextField petOwnerName;
    public JFXComboBox<String> cmbPetId;
    public JFXComboBox<String> cmbPetOwnerId;
    public JFXTextField petName;
    public JFXTextArea desc;
    public JFXTextField docName;
    public Label lblAmount;
    @Setter
    private AppointmentFormController appointmentFormController;

    public void initialize() {
        setDateandTime();
        loadAllDoc();
        loadAllPetOwner();
        loadAllPet();
        setEdit(false);
    }

    private void setEdit(boolean b) {
        petOwnerName.setEditable(b);
        petName.setEditable(b);
        docName.setEditable(b);
    }


    private void loadAllPet() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<PetsDto> idList = PetModel.getAllPets();

            for (PetsDto dto : idList) {
                obList.add(dto.getPetId());
            }

            cmbPetId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllPetOwner() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<PetOwnerDto> idList = PetOwnerModel.getAllOwners();

            for (PetOwnerDto dto : idList) {
                obList.add(dto.getOwnerId());
            }

            cmbPetOwnerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllDoc() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<DoctorDto> idList = DocModel.getAllDoctor();

            for (DoctorDto dto : idList) {
                obList.add(dto.getDocId());
            }

            cmbDocId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDateandTime() {
        lblDate.setText(String .valueOf(LocalDate.now()));
        lblTime.setText(String.valueOf(LocalDate.now()));
    }

    public void DocIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbDocId.getValue();

        try {
            if (id != null) {
                DoctorDto dto = DocModel.getDocDetails(id);

                docName.setText(dto.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbPetIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbPetId.getValue();

        try {
            if (id != null) {
                PetsDto dto = PetModel.getPetsDetails(id);

                petName.setText(dto.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbPetOwnerIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbPetOwnerId.getValue();

        try {
            if (id != null) {
                PetOwnerDto dto = PetOwnerModel.getOwnerDetails(id);

                petOwnerName.setText(dto.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeAppointmentOnAction(ActionEvent actionEvent) {
        String id = appointmentId.getText();
        String docname = docName.getText();
        String petname = petName.getText();
        String petownername = petOwnerName.getText();
        String description = desc.getText();
        String amount = lblAmount.getText();
        String date = lblDate.getText();
        String time = lblTime.getText();
        String docid = cmbDocId.getValue();
        String petid = cmbPetId.getValue();
        String petownerid = cmbPetOwnerId.getValue();

        var dto = new AppointmentDto(id, amount, date, time, description, docid, docname, petownerid, petownername, petid, petname);

        if(!id.isEmpty() && !amount.isEmpty() && !date.isEmpty() && !time.isEmpty() && !description.isEmpty() && !docid.isEmpty() && !petid.isEmpty() && !petownerid.isEmpty()) {
            try {
                boolean isSaved = AppointmentModel.saveAppointment(dto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Appointment saved!").show();
                    appointmentFormController.initialize();
                    clearFields();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields").show();
        }


    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        appointmentId.clear();
        desc.clear();
        docName.clear();
        petName.clear();
        petOwnerName.clear();
        cmbPetId.getSelectionModel().clearSelection();
        cmbPetOwnerId.getSelectionModel().clearSelection();
        cmbDocId.getSelectionModel().clearSelection();
    }


    public void newPetOwnerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPetOwner_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetOwnerFormController ownr =  fxmlLoader.getController();
        ownr.setAddApointmentFormController(this);


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void newPetOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPets_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetsFormController ownr =  fxmlLoader.getController();
        ownr.setAddApointmentFormController(this);


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
