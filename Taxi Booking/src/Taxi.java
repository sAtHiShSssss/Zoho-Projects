import java.util.ArrayList;
import java.util.List;

public class Taxi {
    String taxiName;
    char currentPosition;
    int totalEarnings;
    int freeTime;
    List<Trip> trips = null;
    static int id=1;

    public Taxi(){
        this.taxiName = "T"+id++;
        this.currentPosition = 'A';
        this.freeTime = 0;
        this.totalEarnings = 0;
        this.trips = new ArrayList<>();
    }
}
