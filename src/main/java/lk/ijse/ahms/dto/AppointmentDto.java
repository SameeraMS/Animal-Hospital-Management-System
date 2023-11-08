package lk.ijse.ahms.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AppointmentDto {

    private String appointmentId;
    private String amount;
    private String date;
    private String time;
    private String description;
    private String userId;
    private String doctorId;
    private String petOwnerId;
}
