import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {
    int customerId;
    long accountNo;
    String name;
    double balance;
    String password;
    static int tempCustomerID = 55;
    static long tempAccountNo = 55055;
    List<Transaction> transactions = null;
    List<String> passwords = null;

    public Customer(int customerId, long accountNo, String name, double balance, String password){
        this.customerId = customerId;
        this.accountNo = accountNo;
        this.name = name;
        this.balance = balance;
        this.password = password;
        this.transactions = new ArrayList<>();
        this.passwords = new ArrayList<>();
    }
    public Customer(String name, double balance, String password){
        this.customerId = tempCustomerID;
        this.accountNo = tempAccountNo;
        this.name = name;
        this.balance = balance;
        this.password = password;
        this.transactions = new ArrayList<>();
        this.passwords = new ArrayList<>();
        tempCustomerID+=10;
        tempAccountNo+=11011;
    }
}
