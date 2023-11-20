package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PresDetailsModel {
    public boolean saveOrderDetails(String payId, List<CartTm> cartTmList) throws SQLException {
        for(CartTm tm : cartTmList) {
            if(!saveOrderDetails(payId, tm)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(String payId, CartTm tm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO prescription_details VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, payId);
            pstm.setString(2, tm.getMedId());
            pstm.setString(3, tm.getQty());
            pstm.setString(4, tm.getUnitPrice());


        return pstm.executeUpdate() > 0;
    }
}
