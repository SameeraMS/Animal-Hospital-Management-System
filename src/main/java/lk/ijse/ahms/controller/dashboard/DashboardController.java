package lk.ijse.ahms.controller.dashboard;

import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import lk.ijse.ahms.dto.PaymentDto;
import lk.ijse.ahms.model.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DashboardController {
    public static Label lblToChange;
    public Label lblpet;
    public Label lblpetowner;
    public Label lbldoc;
    public Label lblemp;


    public void initialize() throws SQLException {
        setValues();


    }



    private void setValues() throws SQLException {
        lblpet.setText(String.valueOf(PetModel.getAllPets().size()));
        lblpetowner.setText(String.valueOf(PetOwnerModel.getAllOwners().size()));
        lbldoc.setText(String.valueOf(DocModel.getAllDoctor().size()));
        lblemp.setText(String.valueOf(EmpModel.getAllEmployee().size()));
    }
}
