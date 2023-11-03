package lk.ijse.ahms.dto;

public class PetOwnerDto {
    private String ownerId;
    private String name;
    private String email;
    private String tel;

    public PetOwnerDto() {
    }

    public PetOwnerDto(String ownerId, String name, String email, String tel) {
        this.ownerId = ownerId;
        this.name = name;
        this.email = email;
        this.tel = tel;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "PetOwnerDto{" +
                "ownerId='" + ownerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
