package lk.ijse.ahms.model;

import lk.ijse.ahms.db.DbConnection;
import lk.ijse.ahms.dto.AppointmentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentModel {
    public static List<AppointmentDto> getAllAppointments() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appointment";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<AppointmentDto> appointmentDtos = new ArrayList<>();

        while(resultSet.next()){
            appointmentDtos.add(
                    new AppointmentDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9),
                            resultSet.getString(10),
                            resultSet.getString(11)
                    )
            );
        }


        return appointmentDtos;
    }

    public static boolean saveAppointment(AppointmentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO appointment VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getAppointmentId());
        pstm.setString(2, dto.getAmount());
        pstm.setString(3, dto.getDate());
        pstm.setString(4, dto.getTime());
        pstm.setString(5, dto.getDescription());
        pstm.setString(6, dto.getDoctorId());
        pstm.setString(7, dto.getDoctorName());
        pstm.setString(8, dto.getPetOwnerId());
        pstm.setString(9, dto.getPetOwnerName());
        pstm.setString(10, dto.getPetId());
        pstm.setString(11, dto.getPetName());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static List<AppointmentDto> searchAppointments(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appointment WHERE appointment_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<AppointmentDto> appointmentDtos = new ArrayList<>();

        while(resultSet.next()){
            appointmentDtos.add(
                    new AppointmentDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9),
                            resultSet.getString(10),
                            resultSet.getString(11)
                    )
            );
        }
        return appointmentDtos;
    }


    public static boolean deleteAppoint(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM appointment WHERE appointment_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        boolean isDeleted = pstm.executeUpdate() > 0;

        return isDeleted;
    }

    public static String generateNextAppointId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT appointment_id FROM appointment ORDER BY appointment_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitAppId(resultSet.getString(1));
        }
        return splitAppId(null);
    }

    private static String splitAppId(String currentAppId) {
        if(currentAppId != null) {
            String[] split = currentAppId.split("A0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "A00" + id;
        } else {
            return "A001";
        }
    }

    public static AppointmentDto searchOwnerId(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appointment WHERE appointment_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return new AppointmentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11)
            );
        }else {
            return null;
        }
    }
}
