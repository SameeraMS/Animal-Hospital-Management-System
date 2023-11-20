package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.PetOwnerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetOwnerModel {
    public static boolean savePetOwner(PetOwnerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO pet_owner VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getOwnerId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getEmail());
        pstm.setString(4, dto.getTel());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static List<PetOwnerDto> getAllOwners() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM pet_owner";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<PetOwnerDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new PetOwnerDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public static PetOwnerDto getOwnerDetails(String id) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM pet_owner WHERE pet_owner_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new PetOwnerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        } else {
            return null;
        }
    }

    public static boolean deletePetOwner(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM pet_owner WHERE pet_owner_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        boolean isDelete = pstm.executeUpdate() > 0;

        return isDelete;
    }

    public static boolean updatePetOwner(PetOwnerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE pet_owner SET name =?, email =?, contact_no =? WHERE pet_owner_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getEmail());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getOwnerId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public static String generateNextId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT pet_owner_id FROM pet_owner ORDER BY pet_owner_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitId(resultSet.getString(1));
        }
        return splitId(null);
    }

    private static String splitId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "O00" + id;
        } else {
            return "O001";
        }
    }
}
