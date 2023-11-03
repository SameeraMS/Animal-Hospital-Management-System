package lk.ijse.ahms.dto;

public class PetsDto {
    private String petId;
    private String name;
    private String age;
    private String gender;
    private String type;
    private String ownerId;

    public PetsDto() {
    }

    public PetsDto(String petId, String name, String age, String gender, String type, String ownerId) {
        this.petId = petId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.ownerId = ownerId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "PetsDto{" +
                "petId='" + petId + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }
}
