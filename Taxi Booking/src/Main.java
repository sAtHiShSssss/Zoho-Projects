import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        Booking book = new Booking();
        book.initializeTaxis();
        while(flag){
            System.out.println(" 1.Book taxi \n 2.Print Taxi \n 3.Print Trips \n 4.Exit");
            System.out.println("Enter the choice : ");
            int choice = in.nextInt();

            switch (choice){
                case 1:{
                    book.bookTaxi(in);
                    break;
                }
                case 2:{
                    book.printTaxis();
                    break;
                }
                case 3:{
                    book.printTrips();
                    break;
                }
                default:{
                    flag = false;
                }
            }
        }
    }
}