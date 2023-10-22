package lk.ijse.ahms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.ahms.db.DbConnection;

import java.io.IOException;
import java.sql.*;

public class SigninFormController {
    public AnchorPane root;
    public TextField txtusername;
    public PasswordField txtpassword;

    public void signinOnAction(ActionEvent actionEvent) throws IOException {

        String getun = txtusername.getText();
        String getpw = txtpassword.getText();

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM user WHERE user_name=?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,getun);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String name = resultSet.getString(1);
                String password = resultSet.getString(2);
                String id = resultSet.getString(3);

                if (password.equals(getpw) & name.equals(getun)){
                    AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                    Scene scene = new Scene(anchorPane);
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("dashboard");

                    DashboardFormController.setlabelname(name);

                } else {
                    new Alert(Alert.AlertType.INFORMATION, "username or password incorrect..!").show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Password required..!!").show();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }
}
