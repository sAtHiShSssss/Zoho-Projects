import java.util.List;

public class Ticket {
    int pnr;
    List<Passenger> passengers = null;
    boolean isConfirmed;
    static int temp = 100;

    public Ticket(List<Passenger> passengers, boolean isConfirmed){
        this.pnr = temp;
        this.passengers = passengers;
        this.isConfirmed = isConfirmed;
    }
}
