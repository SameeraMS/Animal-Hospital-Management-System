package lk.ijse.ahms.dto;

public class PrescriptionDetailsDto {
    private String prescriptionId;
    private String medId;

    public PrescriptionDetailsDto() {
    }

    public PrescriptionDetailsDto(String prescriptionId, String medId) {
        this.prescriptionId = prescriptionId;
        this.medId = medId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    @Override
    public String toString() {
        return "PrescriptionDetailsDto{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", medId='" + medId + '\'' +
                '}';
    }
}
