package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.dto.PetsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetModel {
    public static List<PetsDto> getAllPets() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM pets";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<PetsDto> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new PetsDto(
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

    public static boolean savePet(PetsDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO pets VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getPetId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAge());
        pstm.setString(4, dto.getGender());
        pstm.setString(5, dto.getType());
        pstm.setString(6, dto.getOwnerId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public static PetsDto getPetsDetails(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM pets WHERE pet_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        PetsDto dto = null;

        if(resultSet.next()) {
            dto = new PetsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }

        return dto;
    }

    public static boolean deletePet(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM pets WHERE pet_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        boolean isDeleted = pstm.executeUpdate() > 0;

        return isDeleted;
    }

    public static boolean updatePet(PetsDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE pets SET name=?, age=?, gender=?, type=?, pet_owner_id=? WHERE pet_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAge());
        pstm.setString(3, dto.getGender());
        pstm.setString(4, dto.getType());
        pstm.setString(5, dto.getOwnerId());
        pstm.setString(6, dto.getPetId());

        boolean isUpdated = pstm.executeUpdate() > 0;

        return isUpdated;
    }

    public static String generateNextId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT pet_id FROM pets ORDER BY pet_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitpetId(resultSet.getString(1));
        }
        return splitpetId(null);
    }

    private static String splitpetId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("PE0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "PE00" + id;
            } else {
                return "PE0" + id;
            }
        } else {
            return "PE001";
        }
    }
}
