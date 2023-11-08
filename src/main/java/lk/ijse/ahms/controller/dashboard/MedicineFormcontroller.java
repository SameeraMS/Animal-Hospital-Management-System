package lk.ijse.ahms.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MedicineFormcontroller {
    public void addMedOnAction(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(this.getClass().getResource("/view/add/addMedicine_form.fxml"));
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
