package lk.ijse.ahms.dto;

public class AppointmentDto {
    private String appointmentId;
    private String amount;
    private String date;
    private String time;
    private String description;
    private String userId;
    private String doctorId;
    private String petOwnerId;

    public AppointmentDto() {
    }

    public AppointmentDto(String appointmentId, String amount, String date, String time, String description, String userId, String doctorId, String petOwnerId) {
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.description = description;
        this.userId = userId;
        this.doctorId = doctorId;
        this.petOwnerId = petOwnerId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPetOwnerId() {
        return petOwnerId;
    }

    public void setPetOwnerId(String petOwnerId) {
        this.petOwnerId = petOwnerId;
    }

    @Override
    public String toString() {
        return "AppointmentDto{" +
                "appointmentId='" + appointmentId + '\'' +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", userId='" + userId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", petOwnerId='" + petOwnerId + '\'' +
                '}';
    }
}
