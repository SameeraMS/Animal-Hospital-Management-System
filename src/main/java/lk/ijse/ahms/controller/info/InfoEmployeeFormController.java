package lk.ijse.ahms.controller.info;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.model.EmpModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InfoEmployeeFormController {
    public JFXComboBox cmbEmpId;
    public JFXTextField txtname;
    public JFXTextField txtadress;
    public JFXTextField txttel;
    public JFXTextField txtemail;
    public JFXComboBox cmbEmpType;
    public JFXButton btndelete;
    public JFXButton btnUpdate;
    public JFXButton btnEdit;

    public void initialize(){
        setAllEmpId();
    }

    private void setAllEmpId() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> idList = EmpModel.getAllEmployee();

            for (EmployeeDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbEmpId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbEmpIdOnAction(ActionEvent actionEvent) {

        String id = (String) cmbEmpId.getValue();

        try {
            EmployeeDto dto = EmpModel.getEmployeeDetails(id);

            txtname.setText(dto.getName());
            txtadress.setText(dto.getAddress());
            txttel.setText(dto.getTel());
            txtemail.setText(dto.getEmail());

            ObservableList<String> obList = FXCollections.observableArrayList();

            obList.add(dto.getType());

            cmbEmpType.setItems(obList);
            cmbEmpType.setValue(cmbEmpType.getItems().get(0));

            setEdit(false);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setEdit(boolean b) {
        txtname.setEditable(b);
        txtadress.setEditable(b);
        txttel.setEditable(b);
        txtemail.setEditable(b);
        cmbEmpType.setEditable(b);
    }

    public void editOnAction(ActionEvent actionEvent) {

        String emptype = (String)cmbEmpType.getValue();

        btnEdit.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Edit?", yes, no).showAndWait();

            if(!emptype.equals("Admin")) {
                if (type.orElse(no) == yes) {
                    setEdit(true);

                    ObservableList<String> obList = FXCollections.observableArrayList();

                    obList.add("Helper");
                    obList.add("Cleaner");

                    cmbEmpType.setItems(obList);
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Cannot Edit Admin!").show();
            }
        });

    }

    public void updateOnAction(ActionEvent actionEvent) {

        String id = (String) cmbEmpId.getValue();
        String name = txtname.getText();
        String address = txtadress.getText();
        String tel = txttel.getText();
        String mail = txtemail.getText();
        String type = (String) cmbEmpType.getSelectionModel().getSelectedItem();

        var dto = new EmployeeDto(id, name, address, tel, mail, type);

        btnUpdate.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {
                try {
                    boolean isSaved = EmpModel.updateEmployee(dto);

                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
                        setEdit(false);
                        clearall();
                        initialize();
                    }
                } catch (SQLException a) {
                    new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                }
            }
        });


    }

    private void clearall() {
        txtname.clear();
        txtadress.clear();
        txttel.clear();
        txtemail.clear();
        cmbEmpType.getSelectionModel().clearSelection();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String id = (String) cmbEmpId.getValue();
        String type = (String)cmbEmpType.getValue();

        btndelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type1 = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (type1.orElse(no) == yes) {
                if(!type.equals("Admin")) {
                    try {
                        boolean isDelete = EmpModel.deleteEmployee(id);

                        if (isDelete) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted!").show();
                            setEdit(false);
                            clearall();
                            initialize();
                        }
                    } catch (SQLException a) {
                        new Alert(Alert.AlertType.ERROR, a.getMessage()).show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Cannot Delete Admin!").show();
                }
            }
        });
    }
}
