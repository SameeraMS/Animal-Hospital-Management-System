package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaceOrderModel {
    private PaymentModel paymentModel = new PaymentModel();
    private MedModel medModel = new MedModel();
    private PresDetailsModel presDetailModel = new PresDetailsModel();

    public boolean placeOrder(PlaceOrderDto placeOrderDto) throws SQLException {
        System.out.println("place order model -> "+placeOrderDto.getCartTmList());
        String payId = placeOrderDto.getPayId();
        String date = placeOrderDto.getDate();
        String amount = placeOrderDto.getAmoount();
        String appointId = placeOrderDto.getAppointId();

        boolean isOk = false;

        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = paymentModel.savePayment(payId, date, amount, appointId);
            if (isOrderSaved) {
                boolean isUpdated = medModel.updateMed(placeOrderDto.getCartTmList());
                if(isUpdated) {
                    boolean isOrderDetailSaved = presDetailModel.saveOrderDetails(placeOrderDto.getPayId(), placeOrderDto.getCartTmList());
                    if (isOrderDetailSaved) {
                        connection.commit();
                        isOk = true;
                    }
                }
            }
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        return isOk;
    }
}
