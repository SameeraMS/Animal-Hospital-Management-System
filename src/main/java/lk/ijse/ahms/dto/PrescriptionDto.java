package lk.ijse.ahms.dto;

public class PrescriptionDto {
    private String prescriptionId;
    private String description;
    private String appointmentId;

    public PrescriptionDto() {
    }

    public PrescriptionDto(String prescriptionId, String description, String appointmentId) {
        this.prescriptionId = prescriptionId;
        this.description = description;
        this.appointmentId = appointmentId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toString() {
        return "PrescriptionDto{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", description='" + description + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                '}';
    }
}
