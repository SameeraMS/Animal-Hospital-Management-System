package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.smtp.Mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

public ResultSet checkCredentials(String username, String password) throws SQLException {
    Connection connection = DbConnection.getInstance().getConnection();

    String sql = "SELECT * FROM user WHERE user_name=?";
    PreparedStatement pstm = connection.prepareStatement(sql);

    pstm.setString(1,username);
    ResultSet resultSet = pstm.executeQuery();
    return resultSet;
}


}
