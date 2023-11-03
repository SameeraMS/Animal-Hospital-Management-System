package lk.ijse.ahms.dto;

public class PaymentDto {
    private String paymentId;
    private String date;
    private String amount;
    private String appointmentId;

    public PaymentDto() {
    }

    public PaymentDto(String paymentId, String date, String amount, String appointmentId) {
        this.paymentId = paymentId;
        this.date = date;
        this.amount = amount;
        this.appointmentId = appointmentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "paymentId='" + paymentId + '\'' +
                ", date='" + date + '\'' +
                ", amount='" + amount + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                '}';
    }
}
