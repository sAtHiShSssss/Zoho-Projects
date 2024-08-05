public class Trip {
    int customerId;
    int bookingId;
    char pickupPoint;
    char dropPoint;
    int pickupTime;
    int dropTime;
    int amount;

    public Trip(int customerId, int bookingId, char pickupPoint, char dropPoint, int pickupTime, int dropTime, int amount){
        this.customerId = customerId;
        this.bookingId = bookingId;
        this.pickupPoint = pickupPoint;
        this.dropPoint = dropPoint;
        this.pickupTime = pickupTime;
        this.dropTime = dropTime;
        this.amount = amount;
    }
}
