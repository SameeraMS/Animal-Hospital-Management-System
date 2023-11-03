package lk.ijse.ahms.dto;

public class MedicineDto {
    private String medId;
    private String name;
    private String type;
    private double price;
    private String description;
    private String expdate;

    public MedicineDto() {
    }

    public MedicineDto(String medId, String name, String type, double price, String description, String expdate) {
        this.medId = medId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.expdate = expdate;
    }

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    @Override
    public String toString() {
        return "MedicineDto{" +
                "medId='" + medId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", expdate='" + expdate + '\'' +
                '}';
    }
}
