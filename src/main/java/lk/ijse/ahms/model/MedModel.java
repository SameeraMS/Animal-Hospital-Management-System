package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.MedicineDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MedModel {
    public static boolean saveMedicine(MedicineDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO medicine VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getMedId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getType());
        pstm.setString(4, String.valueOf(dto.getPrice()));
        pstm.setString(5, dto.getDescription());
        pstm.setString(6, dto.getExpdate());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
}
