package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpModel {

    public static boolean saveEmployee(EmployeeDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getTel());
        pstm.setString(5, dto.getEmail());
        pstm.setString(6, dto.getType());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<EmployeeDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new EmployeeDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    )
            );
        }
        return dtoList;


    }
}
