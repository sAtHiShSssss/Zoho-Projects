public class Lift {
    private String name;
    private int currentFloor;
    private int minFloor;
    private int maxFloor;
    private int restrictedMinFloor;
    private int restrictedMaxFloor;
    private char direction;
    private int totalCapacity;
    private boolean isUnderMaintenance;
    static int temp = 1;

    public Lift(int minFloor, int maxFloor, int restrictedMinFloor, int restrictedMaxFloor, int totalCapacity){
        this.name = "L"+temp;
        this.currentFloor = 0;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.restrictedMinFloor = restrictedMinFloor;
        this.restrictedMaxFloor = restrictedMaxFloor;
        this.direction = 'u';
        this.totalCapacity = totalCapacity;
        this.isUnderMaintenance = false;
        temp++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public void setMinFloor(int minFloor) {
        this.minFloor = minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getRestrictedMinFloor() {
        return restrictedMinFloor;
    }

    public void setRestrictedMinFloor(int restrictedMinFloor) {
        this.restrictedMinFloor = restrictedMinFloor;
    }

    public int getRestrictedMaxFloor() {
        return restrictedMaxFloor;
    }

    public void setRestrictedMaxFloor(int restrictedMaxFloor) {
        this.restrictedMaxFloor = restrictedMaxFloor;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public boolean isUnderMaintenance() {
        return isUnderMaintenance;
    }

    public void setUnderMaintenance(boolean underMaintenance) {
        isUnderMaintenance = underMaintenance;
    }
}