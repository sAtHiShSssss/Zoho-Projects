import java.net.HttpCookie;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        BankManagement bankManagement = new BankManagement();

        Scanner in = new Scanner(System.in);
        List<String> list = new ArrayList<>();

        boolean flag = true;
        //bankManagement.initializeCustomers();
        bankManagement.readDataFromFile();

        while(flag){
            System.out.println(" 1.Open Bank Account \n 2.Withdraw \n 3.Deposit \n 4.Account Transfer \n 5.Transaction History \n 6.Exit");
            System.out.println("Enter the choice : ");
            int choice = in.nextInt();

            switch (choice){
                case 1:{
                    bankManagement.createAccount(in);
                    break;
                }
                case 2:{
                    bankManagement.withdrawal(in);
                    break;
                }
                case 3:{
                    bankManagement.cashDeposit(in);
                    break;
                }
                case 4:{
                    bankManagement.accountTransfer(in);
                    break;
                }
                case 5:{
                    bankManagement.print();
                    break;
                }
                case 6:{
                    bankManagement.writeDataToFile();
                    flag = false;
                    break;
                }
                default:{
                    System.out.println("Invalid choice..Try Again!");
                    break;
                }
            }
        }
    }
}