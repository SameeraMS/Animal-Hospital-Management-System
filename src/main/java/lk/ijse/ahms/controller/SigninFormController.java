package lk.ijse.ahms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.model.EmpModel;
import lk.ijse.ahms.model.UserModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SigninFormController {
    public AnchorPane root;
    public  TextField txtusername;
    public PasswordField txtpassword;
    public ImageView imageview1;

    UserModel usermodel = new UserModel();

    public void signinOnAction(ActionEvent actionEvent) throws IOException {

       String getun = txtusername.getText();
       String getpw = txtpassword.getText();

        /*
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardControl_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("dashboard");
         */


        if(getun.isEmpty()) {
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
                       // AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardControl_form.fxml"));

                            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/dashboardControl_form.fxml"));
                            Parent anchorPane = fxmlLoader.load();

                            DashboardControlsController dash =  fxmlLoader.getController();
                            dash.setSigninFormController(this);

                        Scene scene = new Scene(anchorPane);
                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.setScene(scene);
                        stage.setTitle("dashboard");

                            EmployeeDto dto = EmpModel.getEmployeeDetails(id);
                            dash.setLblname(dto.getName());


                        /*    String email = getun;
                                String subject = "Animal Hospital System";
                                String message = "Hi..! \n\n You have successfully log in to Animal Hospital System. \n\n Thank you..!";

                                Mail mail = new Mail(email,subject,message);
                                Thread thread = new Thread(mail);

                                mail.valueProperty().addListener((a, oldValue, newValue) -> {
                                    if (newValue){
                                        System.out.println("mail sent");
                                    }else {
                                        System.out.println("mail not sent");
                                    }
                                });

                                thread.setDaemon(true);
                                thread.start();

                         */


                         } else {
                            new Alert(Alert.AlertType.INFORMATION, "username or password incorrect..!").show();


                            }
                    }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    public void passOnAction(ActionEvent actionEvent) throws IOException {
        signinOnAction(actionEvent);
    }

    public void usernameOnAction(ActionEvent actionEvent) {
        txtpassword.requestFocus();
    }

    public void forgotPassOnAction(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/forgotPassword/forgotpass1_form.fxml"));
        AnchorPane anchorPane = loader.load();
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
