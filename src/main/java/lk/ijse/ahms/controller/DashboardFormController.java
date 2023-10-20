package lk.ijse.ahms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {
    public AnchorPane mainroot;
    public AnchorPane subroot;

    private void setform(String form) throws IOException {
        URL resource = getClass().getResource(form);
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        subroot.getChildren().clear();
        subroot.getChildren().add(load);
    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        setform("/view/dashboardcontrols/dashboardform_form.fxml");
    }

    public void signoutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/signin_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) mainroot.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("dashboard");

    }
}
