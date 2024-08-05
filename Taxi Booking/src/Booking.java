import java.util.*;

public class Booking {
    List<Taxi> taxis = new ArrayList<>();

    public void bookTaxi(Scanner in){
        System.out.println("Enter customer id : ");
        int customerId = in.nextInt();
        System.out.println("Enter pickup point (A B C D E): ");
        char pickupPoint = in.next().charAt(0);
        System.out.println("Enter drop point (A B C D E): ");
        char dropPoint = in.next().charAt(0);
        System.out.println("Enter pickup time : ");
        int pickupTime = in.nextInt();

        List<Taxi> freeTaxis = getFreeTaxis(pickupPoint, pickupTime);
        System.out.println(freeTaxis.size());
        freeTaxis.sort(Comparator.comparingInt(a -> a.totalEarnings));

        int minDistance = Integer.MAX_VALUE;
        Taxi taxi = null;
        for(Taxi t : freeTaxis){
            int distance = Math.abs(pickupPoint - t.currentPosition);
            if(distance < minDistance){
                minDistance = distance;
                taxi = t;
            }
        }
        if(taxi == null){
            System.out.println("No taxis available...");
            return;
        }
        System.out.println(taxi.taxiName+" is allocated");
        int fare = calculateFare(pickupPoint, dropPoint);
        System.out.println("Total amount Rs."+fare);
        taxi.totalEarnings+=fare;
        taxi.currentPosition = dropPoint;
        taxi.freeTime = pickupTime + Math.abs(pickupPoint-dropPoint);
        taxi.trips.add(new Trip(customerId, customerId, pickupPoint, dropPoint, pickupTime, taxi.freeTime, fare));
    }
    public List<Taxi> getFreeTaxis(char pickupPoint, int pickupTime){
        List<Taxi> freeTaxis = new ArrayList<>();

        for(Taxi t : taxis){
            int distance = Math.abs(t.currentPosition - pickupPoint);
            if((pickupTime+distance) >= t.freeTime){
                freeTaxis.add(t);
            }
        }
        return freeTaxis;
    }
    public int calculateFare(char pickupPoint, char dropPoint){
        int distance = Math.abs(pickupPoint - dropPoint) * 15;
        int fare = 100 + ((distance-5)*10);
        return fare;
    }
    public void printTaxis(){
        System.out.println("================================================");
        for(Taxi t : taxis){
            System.out.println(t.taxiName+"     "+t.currentPosition+"     "+t.freeTime+"     "+t.totalEarnings);
        }
        System.out.println("================================================");
    }
    public void printTrips(){
        System.out.println("================================================");
        for(Taxi t : taxis){
            System.out.println(t.taxiName+"      Total Earnings : Rs."+t.totalEarnings);
            System.out.println("-----------------------------------------------");
            System.out.println("B.ID     C.ID    From     To     PickupTime      DropTime     Fare");
            for(Trip trip : t.trips){
                System.out.println(trip.bookingId+"     "+trip.customerId+"     "+trip.pickupPoint+"     "+trip.dropPoint+"     "+trip.pickupTime+"     "+trip.dropTime+"     "+trip.amount);
            }
            System.out.println("-----------------------------------------------");
        }
        System.out.println("================================================");
    }
    public void initializeTaxis(){
        Taxi taxi = new Taxi();
        taxis.add(taxi);
        taxi = new Taxi();
        taxis.add(taxi);
        taxi = new Taxi();
        taxis.add(taxi);
        taxi = new Taxi();
        taxis.add(taxi);
    }
}
