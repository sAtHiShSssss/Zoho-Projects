import java.util.*;
import java.io.*;
public class BankManagement implements Serializable{
    Map<Long, Customer> customers = new HashMap<>();
    Customer customer = null;
    Transaction transaction = null;
    public void createAccount(Scanner in){
        System.out.println("Enter your name : ");
        in.nextLine();
        String name = in.nextLine();
        String encryptedPassword = getPasswordFromUser(in);
        encryptedPassword = encryptPassword(encryptedPassword);

        customer = new Customer(name, 10000, encryptedPassword);
        System.out.println("Account opened successfully...!");
        System.out.println("Customer Id : "+customer.customerId);
        System.out.println("Account No  : "+customer.accountNo);
        transaction = new Transaction("Opening", customer.balance, customer.balance);
        customer.transactions.add(transaction);
        customers.put(customer.accountNo, customer);
    }
    public void withdrawal(Scanner in){
        System.out.println("Enter the account number : ");
        long accountNo = in.nextLong();
        if(!customers.containsKey(accountNo)){
            System.out.println("Invalid account no...");
            return;
        }
        System.out.println("Enter the amount to withdraw : ");
        double amount = in.nextDouble();

        customer = customers.get(accountNo);
        try{
            if(customer.balance < amount){
                throw new CustomException("Entered amount higher then the current balance...");
            }
            customer.balance-=amount;
            System.out.println("Amount Rs."+amount+" debited");
            if(customer.balance < 1000){
                throw new CustomException("You should maintain minimum balance Rs.1000");
            }
        }
        catch(CustomException e){
            System.out.println(e);
        }
        finally{
            System.out.println("Available balance : "+customer.balance);
        }
    }
    public void cashDeposit(Scanner in){
        System.out.println("Enter the account number : ");
        long accountNo = in.nextLong();
        if(!customers.containsKey(accountNo)){
            System.out.println("Invalid account no...");
            return;
        }
        System.out.println("Enter the amount to deposit : ");
        double amount = in.nextDouble();

        customer = customers.get(accountNo);
        customer.balance+=amount;
        System.out.println("Rs."+amount+" successfully deposited...!");
        System.out.println("Current Balance : Rs."+customer.balance);
    }
    public void accountTransfer(Scanner in){

    }
    public void passwordChange(Customer customer, Scanner in){
        while(true){
            String password = getPasswordFromUser(in);
            password = encryptPassword(password);
            if(customer.password.contains(password)){
                System.out.println("New password cannot be same has last 3 passwords...!");
            }
            else{
                System.out.println("Password successfully changed...!");
                break;
            }
        }
    }
    public String encryptPassword(String str){
        String encrypted = "";
        for(char ch : str.toCharArray()){
            if(ch >= 'A' && ch <= 'Z'){
                encrypted = encrypted + ((char)(90 - ch + 65));
            }
            else if(ch >= 'a' && ch <= 'z'){
                encrypted = encrypted + ((char)(122 - ch + 97));
            }
            else{
                encrypted = encrypted + ((char)((57 - ch + 48)));
            }
        }
        return encrypted;
    }
    public String getPasswordFromUser(Scanner in){
        while(true){
            System.out.println("Enter the password : ");
            String password = in.next();
            if(passwordChecker(password)){
                return password;
            }
            else{
                System.out.println("Password should be length 6...");
                System.out.println("Password should contain 2 UpperCase 2 LowerCase 2 Numbers");
                System.out.println("\n\n");
            }
        }
    }
    public boolean passwordChecker(String str){
        if(str.length() < 6){
            return false;
        }
        int small = 0, capital = 0, numbers = 0;
        for(char ch : str.toCharArray()){
            if(ch >= 'A' && ch <= 'Z'){
                capital++;
            }
            else if(ch >= 'a' && ch <= 'z'){
                small++;
            }
            else if(ch >= '0' && ch <= '9'){
                numbers++;
            }
        }
        if(small >= 2 && capital >= 2 && numbers >= 2){
            return true;
        }
        return false;
    }
    public void initializeCustomers() {
        customer = new Customer(11, 11011, "Kumar", 10000, "AppipNbjm");
        customers.put(customer.accountNo, customer);
        customer = new Customer(22, 220222, "Madhu", 20000, "Cboljoh");
        customers.put(customer.accountNo, customer);
        customer = new Customer(33, 33033, "Rahul", 30000, "dbnqvt");
        customers.put(customer.accountNo, customer);
        customer = new Customer(44, 44044, "Robin", 40000, "kbwb22");
        customers.put(customer.accountNo, customer);
    }

    public void readDataFromFile(){
        try{
            FileInputStream file = new FileInputStream("bank_db.txt");
            ObjectInputStream read = new ObjectInputStream(file);
            customers = (Map<Long, Customer>)read.readObject();

            FileInputStream file1 = new FileInputStream("temp_values.txt");
            ObjectInputStream read1 = new ObjectInputStream(file1);
            String str = (String)read1.readObject();
            String []arr = str.split(" ");
            Customer.tempAccountNo = Long.parseLong(arr[0]);
            Customer.tempCustomerID = Integer.parseInt(arr[1]);
        }
        catch(ClassNotFoundException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public void writeDataToFile(){
        try{
            FileOutputStream file = new FileOutputStream("bank_db.txt");
            ObjectOutputStream write = new ObjectOutputStream(file);
            write.writeObject(customers);

            FileOutputStream file1 = new FileOutputStream("temp_values.txt");
            ObjectOutputStream write1 = new ObjectOutputStream(file1);
            write1.writeObject(Customer.tempCustomerID+" "+Customer.tempAccountNo);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    public void print(){
        System.out.println("-----------------------------");
        for(Map.Entry<Long, Customer> map : customers.entrySet()){
            System.out.println(map.getValue().name);
        }
        System.out.println("-----------------------------");
    }
}
