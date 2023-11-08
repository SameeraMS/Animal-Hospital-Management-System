package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.DoctorDto;
import lk.ijse.ahms.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocModel {
    public static boolean saveDoctor(DoctorDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO doctor VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getDocId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getEmail());


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public static List<DoctorDto> getAllDoctors() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM doctor";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<DoctorDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new DoctorDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }
}
