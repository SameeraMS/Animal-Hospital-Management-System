package lk.ijse.ahms.dto;

public class MedSupDto {
    private String supId;
    private String medId;

    public MedSupDto() {
    }

    public MedSupDto(String supId, String medId) {
        this.supId = supId;
        this.medId = medId;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    @Override
    public String toString() {
        return "MedSupDto{" +
                "supId='" + supId + '\'' +
                ", medId='" + medId + '\'' +
                '}';
    }
}
