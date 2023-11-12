package lk.ijse.ahms.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lk.ijse.ahms.controller.add.AddPetOwnerFormController;
import lk.ijse.ahms.controller.add.AddPetsFormController;
import lk.ijse.ahms.controller.info.InfoPetsFormController;

import java.io.IOException;

public class PetsFormController {
    public TableColumn colName;
    public TableView tblPets;
    public TableColumn colId;
    public TableColumn colAge;
    public TableColumn colGender;
    public TableColumn colType;

    public void addPetOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPets_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetsFormController ownr =  fxmlLoader.getController();
        ownr.setPetsFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void infoPetOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/info/infoPets_form.fxml"));
        Parent root = fxmlLoader.load();

        InfoPetsFormController ownr =  fxmlLoader.getController();
        ownr.setPetsFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    public void addPetOwnerOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/add/addPetOwner_form.fxml"));
        Parent root = fxmlLoader.load();

        AddPetOwnerFormController ownr =  fxmlLoader.getController();
        ownr.setPetFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
