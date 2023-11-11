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
import lk.ijse.ahms.controller.add.AddEmployeeFormController;
import lk.ijse.ahms.controller.add.AddMedicineFormController;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dto.tm.DoctorTm;
import lk.ijse.ahms.dto.tm.MedicineTm;
import lk.ijse.ahms.model.DocModel;
import lk.ijse.ahms.model.MedModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MedicineFormcontroller {
    public TableView<MedicineTm> tblMed;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colType;
    public TableColumn colPrice;
    public TableColumn colDesc;
    public TableColumn colExp;
    public TableColumn colQty;
    private MedModel medModel = new MedModel();

    public void initialize() {
        setCellValueFactoryMedicine();
        loadAllMedicine();
    }

    private void setCellValueFactoryMedicine() {
        colId.setCellValueFactory(new PropertyValueFactory<>("MedId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colExp.setCellValueFactory(new PropertyValueFactory<>("ExpDate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
    }

    private void loadAllMedicine() {
        System.out.println("Loading all Medicine");
        ObservableList<MedicineTm> obList = FXCollections.observableArrayList();

        try {
            List<MedicineDto> medicineDtos = MedModel.getAllMedicine();

            for (MedicineDto dto : medicineDtos) {

                obList.add(
                        new MedicineTm(
                                dto.getMedId(),
                                dto.getName(),
                                dto.getType(),
                                dto.getPrice(),
                                dto.getDescription(),
                                dto.getExpdate(),
                                dto.getQty()
                        )
                );
            }
            tblMed.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMedOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addMedicine_form.fxml"));
        Parent root = fxmlLoader.load();

        AddMedicineFormController med =  fxmlLoader.getController();
        med.setMedFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void infoMedOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/view/info/infoMedicine_form.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }
}
