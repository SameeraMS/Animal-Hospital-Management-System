package lk.ijse.ahms.controller.barcode;

import com.github.eduramiba.webcamcapture.drivers.NativeDriver;
import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.ahms.webCam.WebCamView;
import lk.ijse.ahms.webCam.WebcamService;

import java.io.IOException;
import java.sql.SQLException;

public class BarcodeReadController {

        public Rectangle main;
        public Rectangle second;
        @FXML
        private AnchorPane pane;

        @FXML
        private ImageView imageView;


        @FXML
        private JFXButton btnStart;

        @FXML
        private JFXButton btnStop;
        private WebcamService service;



        @FXML
        void startBtnOnAction(ActionEvent event) {
            service.restart();

            btnStart.setDisable(true);
        }

        @FXML
        void stopBtnOnAction(ActionEvent event) {
            service.cancel();

            btnStart.setDisable(false);
        }

        public void initialize() {

            Webcam.setDriver(new NativeDriver());

            Webcam cam = Webcam.getDefault();


            service = new WebcamService(cam);




            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebCamView view = new WebCamView(service, imageView);
            pane.getChildren().add(view.getView());

            service.progressProperty().addListener((a, b, c) -> {
                String s = String.valueOf(c);
                if (s.equals("1.0")) {
                    System.out.println(c);
                    service.cancel();
                    service.getT1().stop();
                    imageView.getScene().getWindow().hide();

                    String readBarCodeId = service.getReadBarCodeId();







                    }


            second.setWidth(main.getWidth() * c.doubleValue());
            });
            startRead();
        }

        private void startRead() {
            service.restart();
            //progress.setVisible(true);
            btnStart.setDisable(true);
        }

        public WebcamService getService() {
            return service;
        }




}
