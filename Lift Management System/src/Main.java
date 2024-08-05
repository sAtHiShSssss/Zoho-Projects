import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        LiftManagement liftManagement = new LiftManagement();
        liftManagement.initializeLifts();
        while(flag){
            System.out.println(" 1.Assign Lift \n 2.Display Lift Position \n 3.Lift Maintenance \n 4.Exit");
            int choice = in.nextInt();

            switch (choice){
                case 1:{
                    liftManagement.assignLift(in);
                    break;
                }
                case 2:{
                    liftManagement.printLifts();
                    break;
                }
                case 3:{

                    break;
                }
                case 4:{
                    flag = false;
                    break;
                }
                default:{
                    System.out.println("Invalid choice Try again...!");
                }
            }
        }
    }
}