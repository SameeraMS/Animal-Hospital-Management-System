package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.EmployeeDto;
import lk.ijse.ahms.dto.MedicineDto;
import lk.ijse.ahms.dto.tm.CartTm;

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

    public static MedicineDto getMedicineDetails(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM medicine WHERE med_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return new MedicineDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        } else {
            return null;}
    }

    public static boolean updateMedicine(MedicineDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE medicine SET name =?, type =?, qty =?, price =?, description =?, exp_date =? WHERE med_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getType());
        pstm.setString(3, dto.getQty());
        pstm.setString(4, dto.getPrice());
        pstm.setString(5, dto.getDescription());
        pstm.setString(6, dto.getExpdate());
        pstm.setString(7, dto.getMedId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static boolean deleteMedicine(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM medicine WHERE med_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        boolean isDelete = pstm.executeUpdate() > 0;

        return isDelete;
    }

    public static String generateNextMedId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT med_id FROM medicine ORDER BY med_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitmedId(resultSet.getString(1));
        }
        return splitmedId(null);
    }

    private static String splitmedId(String currentMedId) {
        if(currentMedId != null) {
            String[] split = currentMedId.split("M0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "M00" + id;
            } else {
                return "M0" + id;
            }
        } else {
            return "M001";
        }
    }


    public boolean updateMed(List<CartTm> cartTmList) throws SQLException {
        System.out.println("med model -> "+cartTmList);
        for(CartTm tm : cartTmList) {

                if (!updateQty(tm.getMedId(), tm.getQty())) {
                    return false;
                }

        }
        return true;
    }

    public boolean updateQty(String medid, String qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE medicine SET qty = qty - ? WHERE med_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, qty);
        pstm.setString(2, medid);

        return pstm.executeUpdate() > 0;
    }
}
