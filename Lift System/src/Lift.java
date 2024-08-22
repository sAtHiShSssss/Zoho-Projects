import java.util.List;
public class Lift {
    private String liftId;
    private int currentFloor;
    private boolean isUnderMaintenance;
    private int capacity;
    private List<Integer> restrictedFloors;
    static int tempId = 1;

    public Lift(List<Integer> restrictedFloors){
        this.liftId = "L"+tempId++;
        this.currentFloor = 0;
        this.isUnderMaintenance = false;
        this.capacity = 0;
        this.restrictedFloors = restrictedFloors;
    }

    public String getLiftId() {
        return liftId;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public boolean isUnderMaintenance() {
        return isUnderMaintenance;
    }

    public void setUnderMaintenance(boolean underMaintenance) {
        isUnderMaintenance = underMaintenance;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Integer> getRestrictedFloors() {
        return restrictedFloors;
    }

    public void setRestrictedFloors(List<Integer> restrictedFloors) {
        this.restrictedFloors = restrictedFloors;
    }

}
