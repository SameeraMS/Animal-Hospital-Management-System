package lk.ijse.ahms.controller.dashboard;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.ahms.dto.AppointmentDto;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dto.PlaceOrderDto;
import lk.ijse.ahms.dto.PrescriptionDto;
import lk.ijse.ahms.dto.tm.CartTm;
import lk.ijse.ahms.model.*;
import lk.ijse.ahms.qr.QRScanner;
import lk.ijse.ahms.util.SystemAlert;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PaymentFormController {
    public Label lbldate;
    public static String scan;
    static QRScanner qrScanner;
    public Label lbltime;
    public JFXComboBox<String> cmbAppId;
    public JFXTextField txtPetOwner;
    public JFXTextField txtPet;
    public JFXTextField txtpaymentId;
    public JFXComboBox<String> cmbpresid;
    public JFXComboBox<String> cmbmedid;
    public JFXTextField txtmedname;
    public Label lblqtyOnHand;
    public JFXTextField txtqty;
    public Label lblamount;
    public Label lbltype;
    public TableView tblcart;
    public TableColumn colid;
    public TableColumn colname;
    public TableColumn colunitprice;
    public TableColumn colqty;
    public TableColumn coltotal;
    public Label lbltotal;
    public Label lblappointmentAmount;
    public JFXButton btndelete;
    public JFXButton btnaddtocart;
    public JFXButton btnplaceorder;
    public JFXButton btnqr;

    private PaymentModel paymentModel = new PaymentModel();
    private MedModel medModel = new MedModel();
    private PresDetailsModel presDetailsModel = new PresDetailsModel();
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    private PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    public void initialize() {
        setCellValueFactory();
        generateNextPayId();
        loadAllAppointments();
        loadAllMedicine();
        setDateTime();

    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("medId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
    }

    private void generateNextPayId() {
        try {
            String payId = paymentModel.generateNextPayId();
            txtpaymentId.setText(payId);
            txtpaymentId.setEditable(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setDateTime() {
        lbldate.setText(String.valueOf(LocalDate.now()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        lbltime.setText(LocalTime.now().format(formatter));
    }

    private void loadAllMedicine() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<MedicineDto> idList = MedModel.getAllMedicine();

            for (MedicineDto dto : idList) {
                obList.add(dto.getMedId());
            }

            cmbmedid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllAppointments() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<AppointmentDto> idList = AppointmentModel.getAllAppointments();

            for (AppointmentDto dto : idList) {
                obList.add(dto.getAppointmentId());
            }

            cmbAppId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void appointmentIdOnAction(ActionEvent actionEvent) {
        String AppId = cmbAppId.getValue();

        try {
            if (AppId != null) {
                List<AppointmentDto> dto = AppointmentModel.searchAppointments(AppId);

                txtPetOwner.setText(dto.get(0).getPetOwnerName());
                txtPet.setText(dto.get(0).getPetName());
                lblappointmentAmount.setText(dto.get(0).getAmount());

                PrescriptionDto presDto = PrescriptionModel.searchPrescriptionbyAppId(AppId);

                ObservableList<String> obList = FXCollections.observableArrayList();

                if (presDto != null) {
                    obList.add(presDto.getPrescriptionId());
                    cmbpresid.setItems(obList);
                } else {
                    obList.add("No Prescription");
                    cmbpresid.setItems(obList);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public void medIdOnAction(ActionEvent actionEvent) {
        String id = cmbmedid.getValue();

        try {
            if (id != null) {
                MedicineDto dto = MedModel.getMedicineDetails(id);

                txtmedname.setText(dto.getName());
                lblqtyOnHand.setText(dto.getQty());
                lbltype.setText(dto.getType());
                lblamount.setText(dto.getPrice());
            }

            txtqty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        String medid = cmbmedid.getValue();
        String medname = txtmedname.getText();
        int qty = Integer.parseInt(txtqty.getText());
        double unitPrice = Double.parseDouble(lblamount.getText());
        double tot = unitPrice * qty;

        if (qty <= Integer.parseInt(lblqtyOnHand.getText())) {

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblcart.getItems().size(); i++) {
                    if (colid.getCellData(i).equals(medid)) {
                        int col_qty = Integer.valueOf(String.valueOf(colqty.getCellData(i)));
                        qty += col_qty;
                        tot = unitPrice * qty;

                        obList.get(i).setQty(String.valueOf(qty));
                        obList.get(i).setTotal(String.valueOf(tot));

                        calculateTotal();
                        tblcart.refresh();

                        return;
                    }
                }
            }


            var cartTm = new CartTm(medid, medname, String.valueOf(unitPrice), String.valueOf(qty), String.valueOf(tot));

            obList.add(cartTm);

            tblcart.setItems(obList);
            calculateTotal();
            txtqty.clear();

        } else {
            new SystemAlert(Alert.AlertType.ERROR, "Out of stock", "Out of stock", ButtonType.OK).show();
        }


    }

    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblcart.getItems().size(); i++) {
            total += Double.parseDouble((String) coltotal.getCellData(i));
        }
        total = total + Double.parseDouble(lblappointmentAmount.getText());
        lbltotal.setText(String.valueOf(total));
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        btndelete.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblcart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblcart.refresh();
                calculateTotal();
            }
        });
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException, JRException, InterruptedException {
        String payId = txtpaymentId.getText();
        String date = lbldate.getText();
        String appointId = cmbAppId.getValue();
        String total = lbltotal.getText();


                List<CartTm> cartTmList = new ArrayList<>();
                for (int i = 0; i < tblcart.getItems().size(); i++) {
                    cartTmList.add((CartTm) tblcart.getItems().get(i));
                }
        System.out.println("place order on action -> "+cartTmList);
                var placeOrderDto = new PlaceOrderDto(payId, date, total, appointId, cartTmList);
                boolean isSuccess = false;
                try {
                    isSuccess = placeOrderModel.placeOrder(placeOrderDto);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if (isSuccess) {
                   // new Alert(Alert.AlertType.CONFIRMATION, "Order Successfully Placed!").show();
                    new SystemAlert(Alert.AlertType.CONFIRMATION,"Confirmation","Order Placed Successfully..!",ButtonType.OK).show();

                    printBill(payId,total,appointId);
                    clearall();
                }


    }

    private void printBill(String payId, String total, String appointId) throws JRException {


        HashMap hashMap = new HashMap();
        hashMap.put("parameterPaymentId", payId);
        hashMap.put("appointmentId", appointId);


        InputStream resourceAsStream = getClass().getResourceAsStream("/report/Bill.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        /*
        JRDesignQuery jrDesignQuery = new JRDesignQuery();
        jrDesignQuery.setText("select\n" +
                "    p.payment_id,\n" +
                "    p.med_id,\n" +
                "    p.med_name,\n" +
                "    p.qty,\n" +
                "    p.unit_price,\n" +
                "    p.amount\n" +
                "from print p\n" +
                "         join payment pa\n" +
                "              on p.payment_id = pa.payment_id\n" +
                "where p.payment_id ='"+ payId +"'");
        load.setQuery(jrDesignQuery);

         */

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport, hashMap, new JREmptyDataSource());

        JasperViewer.viewReport(jasperPrint, false);
    }

    private void clearall() {
        txtmedname.clear();
        txtqty.clear();
        txtmedname.requestFocus();
        tblcart.getItems().clear();
        lbltotal.setText("0");
        lblappointmentAmount.setText("0");
        cmbpresid.getItems().clear();
        cmbmedid.getItems().clear();
        cmbAppId.getItems().clear();
        initialize();
    }

    public void qtyOnAction(ActionEvent actionEvent) {
        addToCartOnAction(actionEvent);
    }

    public void qrOnAction(ActionEvent actionEvent) throws IOException {

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(qrScanner.getVideoPanel());
        Stage stage = new Stage();


        stage.setScene(new Scene(stackPane, 800, 600));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                QRScanner.webcam.close();
                qrScanner.thread.stop();
            }
        });

    }

    public void setAppId(String c) {
        cmbAppId.setValue(c);
        appointmentIdOnAction(c);
    }

    private void appointmentIdOnAction(String c) {
        String AppId = c;

        try {
            if (AppId != null) {
                List<AppointmentDto> dto = AppointmentModel.searchAppointments(AppId);

                txtPetOwner.setText(dto.get(0).getPetOwnerName());
                txtPet.setText(dto.get(0).getPetName());
                lblappointmentAmount.setText(dto.get(0).getAmount());

                PrescriptionDto presDto = PrescriptionModel.searchPrescriptionbyAppId(AppId);

                ObservableList<String> obList = FXCollections.observableArrayList();

                if (presDto != null) {
                    obList.add(presDto.getPrescriptionId());
                    cmbpresid.setItems(obList);
                } else {
                    obList.add("No Prescription");
                    cmbpresid.setItems(obList);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void barcodeOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/barcodeReader/BarcodeRead.fxml"));
        Parent root = fxmlLoader.load();

       // AddApointmentFormController appointment =  fxmlLoader.getController();
     //   appointment.setAppointmentFormController(this);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
