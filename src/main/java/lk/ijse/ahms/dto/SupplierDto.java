package lk.ijse.ahms.dto;

public class SupplierDto {
    private String supId;
    private String name;
    private String tel;

    public SupplierDto() {
    }

    public SupplierDto(String supId, String name, String tel) {
        this.supId = supId;
        this.name = name;
        this.tel = tel;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "SupplierDto{" +
                "supId='" + supId + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
