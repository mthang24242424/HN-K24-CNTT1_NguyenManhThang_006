package refactoring.entity;

public class Account {
    private String id;
    private double balance;
    private String phone;

    public Account(String id, double balance, String phone) {
        this.id = id;
        this.balance = balance;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
