package lk.ijse.ahms.controller.dashboard;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
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

    public Label lblpet;
    public Label lblpetowner;
    public Label lbldoc;
    public Label lblemp;
    public Label lblDate;
    public Label lblTime;
    public AnchorPane calanderpain;
    public PieChart piechart;
    public LineChart linechart;


    public void initialize() throws SQLException, IOException {
        setValues();
        lblDate.setText(String.valueOf(LocalDate.now()));
        settime();
        setchart();

        URL resource = getClass().getResource("/view/Calender.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        calanderpain.getChildren().clear();
        calanderpain.getChildren().add(load);


    }

    private void setchart() throws SQLException {

        //pie chart

        PieChart.Data slice1 = new PieChart.Data("Pets", PetModel.getAllPets().size());
        PieChart.Data slice2 = new PieChart.Data("Pet Owners", PetOwnerModel.getAllOwners().size());
        PieChart.Data slice3 = new PieChart.Data("Doctors", DocModel.getAllDoctor().size());
        PieChart.Data slice4 = new PieChart.Data("Employees", EmpModel.getAllEmployee().size());

        piechart.getData().addAll(slice1, slice2, slice3, slice4);



        //line chart

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Details");

        series.getData().add(new XYChart.Data<>("Pets", PetModel.getAllPets().size()));
        series.getData().add(new XYChart.Data<>("Pet Owners", PetOwnerModel.getAllOwners().size()));
        series.getData().add(new XYChart.Data<>("Doctors", DocModel.getAllDoctor().size()));
        series.getData().add(new XYChart.Data<>("Employees", EmpModel.getAllEmployee().size()));

        linechart.getData().add(series);
        
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
