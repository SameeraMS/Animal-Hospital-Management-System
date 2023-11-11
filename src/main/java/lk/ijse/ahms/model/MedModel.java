package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.MedicineDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedModel {
    public static boolean saveMedicine(MedicineDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO medicine VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getMedId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getType());
        pstm.setString(4, dto.getQty());
        pstm.setString(5, String.valueOf(dto.getPrice()));
        pstm.setString(6, dto.getDescription());
        pstm.setString(7, dto.getExpdate());


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static List<MedicineDto> getAllMedicine() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM medicine";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<MedicineDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new MedicineDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    )
            );
        }
        return dtoList;

    }
}
