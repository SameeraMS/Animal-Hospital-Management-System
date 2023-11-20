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

    public static List<DoctorDto> getAllDoctor() throws SQLException {

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

    public static DoctorDto getDocDetails(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM doctor WHERE doc_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new DoctorDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        } else {
            return null;}
    }

    public static boolean updateDoctor(DoctorDto dto) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE doctor SET name =?, email =?, contact_no =? WHERE doc_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getEmail());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getDocId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static boolean deleteDoctor(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM doctor WHERE doc_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        boolean isDelete = pstm.executeUpdate() > 0;

        return isDelete;
    }

    public static String generateNextDocId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT doc_id FROM doctor ORDER BY doc_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitDocId(resultSet.getString(1));
        }
        return splitDocId(null);
    }

    private static String splitDocId(String currentDocId) {
        if(currentDocId != null) {
            String[] split = currentDocId.split("D0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "D00" + id;
        } else {
            return "D001";
        }
    }
}
