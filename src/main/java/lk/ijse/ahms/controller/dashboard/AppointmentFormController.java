package lk.ijse.ahms.controller.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.ahms.controller.add.AddApointmentFormController;
import lk.ijse.ahms.controller.add.AddMedicineFormController;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dto.tm.AppointmentTm;
import lk.ijse.ahms.dto.tm.MedicineTm;
import lk.ijse.ahms.model.AppointmentModel;
import lk.ijse.ahms.model.MedModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AppointmentFormController {

    public TableView tblAppointments;
    public TableColumn colId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colDesc;
    public TableColumn colDocId;
    public TableColumn colPetOwnerId;

    public void initialize() {
        setCellValueFactoryAppointments();
        loadAllAppointments();
    }

    private void setCellValueFactoryAppointments() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("Time"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colDocId.setCellValueFactory(new PropertyValueFactory<>("DocId"));
        colPetOwnerId.setCellValueFactory(new PropertyValueFactory<>("PetOwnerId"));

    }

    private void loadAllAppointments() {
        System.out.println("Loading all Appointments");
        ObservableList<AppointmentTm> obList = FXCollections.observableArrayList();

        try {
            List<AppointmentDto> AppointmentDtos = AppointmentModel.getAllAppointments();

            for (AppointmentDto dto : AppointmentDtos) {

                obList.add(
                        new AppointmentTm(
                                dto.getAppointmentId(),
                                dto.getDate(),
                                dto.getTime(),
                                dto.getDescription(),
                                dto.getDoctorId(),
                                dto.getPetOwnerId()
                        )
                );
            }
            tblAppointments.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeApointmentOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addApointment_form.fxml"));
        Parent root = fxmlLoader.load();

        AddApointmentFormController appointment =  fxmlLoader.getController();
        appointment.setAppointmentFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }
}
