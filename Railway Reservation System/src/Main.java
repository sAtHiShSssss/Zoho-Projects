import java.util.*;
public class Main {
    public static void main(String[] args) {
        Booking book = new Booking();
        book.initializeCoach();


        Scanner in = new Scanner(System.in);
        boolean flag = true;
        while(flag){
            System.out.println(" 1.Book Ticket \n 2.Cancel Ticket \n 3.Print Booked Tickets \n 4.Print Available Tickets \n 5.Exit");
            System.out.println(" Enter the choice : ");
            int choice = in.nextInt();

            switch (choice){
                case 1:{
                    book.bookTicket(in);
                    break;
                }
                case 2:{

                    break;
                }
                case 3:{
                    book.printPassengers();
                    break;
                }
                case 4:{
                    book.printAvailableTicket();
                    break;
                }
                case 5:{
                    flag = false;
                    break;
                }
                default:{
                    System.out.println("Invalid choice Try again...");
                }
            }
        }
    }
}