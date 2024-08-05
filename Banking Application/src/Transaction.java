import java.io.Serializable;

public class Transaction implements Serializable {
    static int id = 1;
    int transactionId;
    String transactionType;
    double amount;
    double balance;

    public Transaction(String transactionType, double amount, double balance){
        this.transactionId = id++;
        this.amount = amount;
        this.balance = balance;
    }
}
