package lk.ijse.ahms.controller.dashboard;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.ahms.dto.PaymentDto;
import lk.ijse.ahms.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DashboardController {
    public static Label lblToChange;
    public Label lblpet;
    public Label lblpetowner;
    public Label lbldoc;
    public Label lblemp;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane calanderpain;


    public void initialize() throws SQLException, IOException {
        setValues();
        lblDate.setText(String.valueOf(LocalDate.now()));
        settime();

        URL resource = getClass().getResource("/view/Calender.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        calanderpain.getChildren().clear();
        calanderpain.getChildren().add(load);


    }

    private void settime() {
        //running time
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while(true) {
                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    lblTime.setText(timenow);
                });
            }
        });
        thread.start();
    }


    private void setValues() throws SQLException {
        lblpet.setText(String.valueOf(PetModel.getAllPets().size()));
        lblpetowner.setText(String.valueOf(PetOwnerModel.getAllOwners().size()));
        lbldoc.setText(String.valueOf(DocModel.getAllDoctor().size()));
        lblemp.setText(String.valueOf(EmpModel.getAllEmployee().size()));
    }
}
