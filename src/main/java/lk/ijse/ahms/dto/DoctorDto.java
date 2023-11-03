package lk.ijse.ahms.dto;

public class DoctorDto {
    private String docId;
    private String name;
    private String email;
    private String tel;

    public DoctorDto() {
    }

    public DoctorDto(String docId, String name, String email, String tel) {
        this.docId = docId;
        this.name = name;
        this.email = email;
        this.tel = tel;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
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
        return "DoctorDto{" +
                "docId='" + docId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
