package lk.ijse.ahms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.model.UserModel;
import lk.ijse.ahms.smtp.Mail;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class SigninFormController {
    public AnchorPane root;
    public TextField txtusername;
    public PasswordField txtpassword;

    UserModel usermodel = new UserModel();

    public void signinOnAction(ActionEvent actionEvent) throws IOException {

        String getun = txtusername.getText();
        String getpw = txtpassword.getText();

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardControl_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("dashboard");


     /*    if(getun.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "username required..!!").show();
        }else if(getpw.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "password required..!!").show();
        } else {
            try {
                ResultSet resultSet = usermodel.checkCredentials(getun, getpw);

                if (resultSet.next()) {
                    String name = resultSet.getString(1);
                    String password = resultSet.getString(2);
                    String id = resultSet.getString(3);

                        if (password.equals(getpw) & name.equals(getun)) {
                        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardControl_form.fxml"));
                        Scene scene = new Scene(anchorPane);
                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("dashboard");

                            Mail mail = new Mail();
                            mail.setMsg("Welcome..! \n\n\tYou are successfully logged in to the Animal Hospital Management System \n\nThank you..!");
                            mail.setTo(getun);
                            mail.setSubject("Animal Hospital Management System Login");

                            Thread thread = new Thread(mail);
                            thread.start();



                         } else {
                            new Alert(Alert.AlertType.INFORMATION, "username or password incorrect..!").show();
                            }
                    }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } */
    }

    public void passOnAction(ActionEvent actionEvent) throws IOException {
        signinOnAction(actionEvent);
    }

    public void usernameOnAction(ActionEvent actionEvent) {
        txtpassword.requestFocus();
    }

    public void forgotPassOnAction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/forgotpass1_form.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
