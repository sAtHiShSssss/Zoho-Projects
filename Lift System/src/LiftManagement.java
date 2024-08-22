import java.util.*;

public class LiftManagement {
    Map<String, Lift> lifts = new HashMap<>();
    int assignLift = 10;
    public void assignLift(Scanner in) {
        System.out.println("Enter source floor : ");
        int source = in.nextInt();
        System.out.println("Enter destination floor : ");
        int destination = in.nextInt();

        Lift assignLift = assignLiftToUser(source, destination);
        if(assignLift == null){
            System.out.println("No Lifts Available...");
            return;
        }
        System.out.println(assignLift.getLiftId()+" is assigned");
        assignLift.setCurrentFloor(destination);
    }
    public Lift assignLift(int source, int destination){
        int min = Integer.MAX_VALUE;
        Lift assignLift = null;
        for(Map.Entry<String,Lift> map : lifts.entrySet()){
            int temp = Math.abs(map.getValue().getCurrentFloor() - source);
            if(temp < min){
                min = temp;
                assignLift = map.getValue();
            }
            else if(temp == min){
                if(source < destination){
                    if(map.getValue().getCurrentFloor() < source){
                        assignLift = map.getValue();
                    }
                }
                else{
                    if(map.getValue().getCurrentFloor() > source){
                        assignLift = map.getValue();
                    }
                }
            }
        }
        return assignLift;
    }
    public Lift assignLiftToUser(int source, int destination){
        Lift assignLift = null;
        int stops = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        for(Map.Entry<String,Lift> lift : lifts.entrySet()){
            if(isCanServe(lift.getValue(), source, destination) && !lift.getValue().isUnderMaintenance()){
                int totalStops = calculateStops(lift.getValue(),source, destination);
                if(totalStops < stops){
                    stops = totalStops;
                    assignLift = lift.getValue();
                }

            }
        }
        return assignLift;
    }
    public int calculateStops(Lift lift, int source, int destination){
        int totalStops = Math.abs(source - destination) +  Math.abs(lift.getCurrentFloor() - source);
        for(int i : lift.getRestrictedFloors()){
            if((i > source && i < destination) || (i < source && i > destination)){
                totalStops--;
            }
        }
        return totalStops;
    }
    public boolean isCanServe(Lift lift, int source, int destination){
        return !(lift.getRestrictedFloors().contains(source) || lift.getRestrictedFloors().contains(destination));
    }
    public void displayLifts(){
        System.out.println("Lift  :  L1   L2   L3   L4   L5");
        System.out.print("Floor :   ");
        for(Map.Entry<String,Lift> map : lifts.entrySet()){
            System.out.print(map.getValue().getCurrentFloor()+"    ");
        }
        System.out.println("\n");
    }
    public void createLifts(){
        Lift lift = new Lift(Arrays.asList(6,7,8,9));
        lifts.put(lift.getLiftId(), lift);
        lift = new Lift(Arrays.asList(6,7,8,9));
        lifts.put(lift.getLiftId(), lift);
        lift = new Lift(Arrays.asList(1,2,3,4,5));
        lifts.put(lift.getLiftId(), lift);
        lift = new Lift(Arrays.asList(1,2,3,4,5));
        lifts.put(lift.getLiftId(), lift);
        lift = new Lift(new ArrayList<>());
        lifts.put(lift.getLiftId(), lift);
    }
}
