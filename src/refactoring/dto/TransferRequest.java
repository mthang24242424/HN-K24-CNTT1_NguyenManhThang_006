package refactoring.dto;

public class TransferRequest {
    private String senderId;
    private String receiverId;
    private double amount;
    private String transferType;

    public TransferRequest() {}

    public TransferRequest(String senderId, String receiverId, double amount, String transferType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.transferType = transferType;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
}
