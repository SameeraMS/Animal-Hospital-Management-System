package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.Optional;

public class AppointmentFormController {

    public TableView tblAppointments;
    public TableColumn colId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colDesc;
    public TableColumn colDocId;
    public TableColumn colPetOwnerId;
    public JFXTextField txtAppointId;
    public JFXButton btnDelete;

    private ObservableList<AppointmentTm> obList = FXCollections.observableArrayList();

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

    public void searchOnAction(ActionEvent actionEvent) {
        String id = txtAppointId.getText();

        try {
            List<AppointmentDto> appointmentDtos = AppointmentModel.searchAppointments(id);

            ObservableList<AppointmentTm> obList1 = FXCollections.observableArrayList();

            for (AppointmentDto dto : appointmentDtos) {
                obList1.add(
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
            tblAppointments.setItems(obList1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void idOnAction(ActionEvent actionEvent) {
        searchOnAction(actionEvent);
    }

    public void deleteOnAction(ActionEvent actionEvent) {

        btnDelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to delete?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblAppointments.getSelectionModel().getSelectedIndex();
                AppointmentTm selectedItem = (AppointmentTm) tblAppointments.getSelectionModel().getSelectedItem();

                String id = selectedItem.getId();

                try {
                    boolean isDelete = AppointmentModel.deleteAppoint(id);

                    if (isDelete) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Appoint Deleted!").show();
                        obList.remove(focusedIndex);
                        tblAppointments.refresh();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

    }
}
