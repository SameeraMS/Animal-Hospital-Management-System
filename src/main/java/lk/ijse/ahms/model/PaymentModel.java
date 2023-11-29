package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.PaymentDto;
import lk.ijse.ahms.dto.PlaceOrderDto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    public static List<PaymentDto> getPayment() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM payment order by amount asc limit 5";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<PaymentDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new PaymentDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }

        return dtoList;

    }

    public String generateNextPayId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT payment_id FROM payment ORDER BY payment_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitPayId(resultSet.getString(1));
        }
        return splitPayId(null);
    }

    private String splitPayId(String currentPayId) {
        if(currentPayId != null) {
            String[] split = currentPayId.split("P0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "P00" + id;
            } else {
                return "P0" + id;
            }
        } else {
            return "P001";
        }
    }


    public boolean savePayment(String payId, String date, String amount, String appointId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO payment VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, payId);
        pstm.setString(2, date);
        pstm.setString(3, amount);
        pstm.setString(4, appointId);

        return pstm.executeUpdate() > 0;
    }
}
