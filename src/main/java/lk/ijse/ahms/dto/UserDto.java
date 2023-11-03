package lk.ijse.ahms.dto;

public class UserDto {
    private String username;
    private String password;
    private String empId;

    public UserDto() {
    }

    public UserDto(String username, String password, String empId) {
        this.username = username;
        this.password = password;
        this.empId = empId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", empId='" + empId + '\'' +
                '}';
    }
}
