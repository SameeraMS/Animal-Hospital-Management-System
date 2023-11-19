package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dto.PrescriptionDto;
import lk.ijse.ahms.model.AppointmentModel;
import lk.ijse.ahms.model.MedModel;
import lk.ijse.ahms.model.PrescriptionModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PaymentFormController {
    public Label lbldate;
    public Label lbltime;
    public JFXComboBox<String> cmbAppId;
    public JFXTextField txtPetOwner;
    public JFXTextField txtPet;
    public JFXTextField txtpaymentId;
    public JFXComboBox<String> cmbpresid;
    public JFXComboBox<String> cmbmedid;
    public JFXTextField txtmedname;
    public Label lblqtyOnHand;
    public JFXTextField txtqty;
    public Label lblamount;
    public Label lbltype;
    public TableView tblcart;
    public TableColumn colid;
    public TableColumn colname;
    public TableColumn colunitprice;
    public TableColumn colqty;
    public TableColumn coltotal;
    public Label lbltotal;
    public Label lblappointmentAmount;

    public void initialize() {
        loadAllAppointments();
        loadAllMedicine();
        setDateTime();

    }

    private void setDateTime() {
        lbldate.setText(String.valueOf(LocalDate.now()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        lbltime.setText(LocalTime.now().format(formatter));
    }

    private void loadAllMedicine() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<MedicineDto> idList = MedModel.getAllMedicine();

            for (MedicineDto dto : idList) {
                obList.add(dto.getMedId());
            }

            cmbmedid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllAppointments() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<AppointmentDto> idList = AppointmentModel.getAllAppointments();

            for (AppointmentDto dto : idList) {
                obList.add(dto.getAppointmentId());
            }

            cmbAppId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void appointmentIdOnAction(ActionEvent actionEvent) {
        String AppId = cmbAppId.getValue();

        try {
            if (AppId != null) {
                List<AppointmentDto> dto = AppointmentModel.searchAppointments(AppId);

                txtPetOwner.setText(dto.get(0).getPetOwnerId());
                txtPet.setText(dto.get(0).getPetId());
                lblappointmentAmount.setText(dto.get(0).getAmount());

                PrescriptionDto presDto = PrescriptionModel.searchPrescriptionbyAppId(AppId);

                ObservableList<String> obList = FXCollections.observableArrayList();

                if (presDto != null) {
                    obList.add(presDto.getPrescriptionId());
                    cmbpresid.setItems(obList);
                } else {
                    obList.add("No Prescription");
                    cmbpresid.setItems(obList);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public void medIdOnAction(ActionEvent actionEvent) {
        String id = cmbmedid.getValue();

        try {
            if (id != null) {
                MedicineDto dto = MedModel.getMedicineDetails(id);

                txtmedname.setText(dto.getMedId());
                lblqtyOnHand.setText(dto.getQty());
                lbltype.setText(dto.getType());
                lblamount.setText(dto.getPrice());
            }

            txtqty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public void addToCartOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
    }
}
