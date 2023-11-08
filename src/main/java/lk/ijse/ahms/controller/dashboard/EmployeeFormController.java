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
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.dto.tm.DoctorTm;
import lk.ijse.ahms.dto.tm.EmployeeTm;
import lk.ijse.ahms.model.DocModel;
import lk.ijse.ahms.model.EmpModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    public TableView<EmployeeTm> tblEmployee;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colTel;
    public TableColumn colType;
    public TableView tblDoc;
    public TableColumn colDocId;
    public TableColumn colDocName;
    public TableColumn colDocTel;
    private EmpModel empModel = new EmpModel();

    public void initialize() {
        setCellValueFactoryEmployee();
        loadAllEmployee();

        setCellValueFactoryDoctor();
        loadAllDoctors();
    }

    private void setCellValueFactoryDoctor() {
        colDocId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colDocName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colDocTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
    }

    private void loadAllDoctors() {

        ObservableList<DoctorTm> obList = FXCollections.observableArrayList();

        try {
            List<DoctorDto> doctorDtos = DocModel.getAllDoctors();

            for (DoctorDto dto : doctorDtos) {

                obList.add(
                        new DoctorTm(
                                dto.getDocId(),
                                dto.getName(),
                                dto.getTel()
                        )
                );
            }
            tblDoc.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactoryEmployee() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
    }

    private void loadAllEmployee() {

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> employeeDtos = empModel.getAllEmployee();

            for (EmployeeDto dto : employeeDtos) {

                obList.add(
                        new EmployeeTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getTel(),
                                dto.getType()
                        )
                );
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmpOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/view/add/addEmployee_form.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void infoEmpOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/info/infoEmployee_form.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void addDocOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/add/addDoc_form.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void infoDocOnAction(ActionEvent actionEvent) {

    }
}
