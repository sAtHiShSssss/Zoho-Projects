import java.util.*;

public class LiftManagement {

    List<Lift> lifts = new ArrayList<>();
    Lift lift = null;

    public void assignLift(Scanner in){
        System.out.println("Enter the source floor : ");
        int sourceFloor = in.nextInt();
        System.out.println("Enter the destination floor : ");
        int destinationFloor = in.nextInt();

        Lift assignLift = null;
        int minDistance = Integer.MAX_VALUE;
        int minStops = Integer.MAX_VALUE;

        for(Lift lift : lifts){
            if(isCanServe(lift, sourceFloor, destinationFloor)){
                System.out.println(lift.getName()+" is assigned");
                break;
            }
        }
        
    }
    public boolean isCanServe(Lift lift, int source, int destination){
        if( (source >= lift.getRestrictedMinFloor() && source <= lift.getRestrictedMaxFloor()) || (destination >= lift.getRestrictedMinFloor() && destination <= lift.getRestrictedMaxFloor()) ){
            return false;
        }
        return true;
    }
    public int minStops(Lift lift, int source, int destination){
        return 0;
    }
    public void printLifts(){
        System.out.println("---------------------------------");
        System.out.print(" Lift  :  ");
        for(Lift lift : lifts){
            System.out.print(lift.getName()+"    ");
        }
        System.out.print("\n Floor :  ");
        for(Lift lift : lifts){
            if(!lift.isUnderMaintenance()){
                System.out.print(lift.getCurrentFloor()+"     ");
            }
            else{
                System.out.print("-1"+"     ");
            }
        }
        System.out.println("\n---------------------------------");
    }
    public void initializeLifts(){
        lifts.add(new Lift(0, 5, 6, 9, 10));
        lifts.add(new Lift(0, 5, 6, 9, 10));
        lifts.add(new Lift(6, 10, 1, 5, 10));
        lifts.add(new Lift(6, 10, 1, 5, 10));
        lifts.add(new Lift(0, 10, 0, 0, 10));
    }
}