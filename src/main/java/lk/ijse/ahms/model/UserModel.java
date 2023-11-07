package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.UserDto;
import lk.ijse.ahms.smtp.Mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public static UserDto searchByName(String name) throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user WHERE user_name = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }

    public static List<String> getUserName() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT user_name FROM user";

        List<String> username = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            username.add(resultSet.getString(1));
        }
        return username;
    }

    public ResultSet checkCredentials(String username, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM user WHERE user_name=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,username);
        ResultSet resultSet = pstm.executeQuery();
        return resultSet;
    }


}
