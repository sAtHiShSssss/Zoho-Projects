import java.util.*;

public class Booking {

    List<Ticket> confirmTickets = new ArrayList<>();
    Stack<Ticket> racTickets = new Stack<>();
    Stack<Ticket> waitingListTickets = new Stack<>();
    Coach coach = null;
    Ticket ticket = null;
    public void bookTicket(Scanner in){
        List<Passenger> passengers = getPassengersDetails(in);
        int total = coach.lowerBerths.size()+coach.upperBerths.size()+coach.middleBerths.size();
        if(total < passengers.size()){
            bookRacTickets(passengers);
            return;
        }
        List<Passenger> bookedPassengers = new ArrayList<>();

        for(Passenger passenger : passengers){
            if(passenger.berthPreference == 'L'){
                if(coach.lowerBerths.size() > 0){
                    bookedPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.lowerBerths.get(0)));
                    coach.lowerBerths.remove(0);
                }
                else if(coach.middleBerths.size() > 0){
                    bookedPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.middleBerths.get(0)));
                    coach.middleBerths.remove(0);
                }
                else{
                    bookedPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.upperBerths.get(0)));
                    coach.upperBerths.remove(0);
                }
            }
            else if(passenger.berthPreference == 'M'){
                if(coach.middleBerths.size() > 0){
                    bookedPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.middleBerths.get(0)));
                    coach.middleBerths.remove(0);
                }
                if(coach.lowerBerths.size() > 0){
                    bookedPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.lowerBerths.get(0)));
                    coach.lowerBerths.remove(0);
                }
                else{
                    bookedPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.upperBerths.get(0)));
                    coach.upperBerths.remove(0);
                }
            }
            else if(passenger.berthPreference == 'U'){
                if(coach.upperBerths.size() > 0){
                    bookedPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.upperBerths.get(0)));
                    coach.upperBerths.remove(0);
                }
                else if(coach.lowerBerths.size() > 0){
                    bookedPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.lowerBerths.get(0)));
                    coach.lowerBerths.remove(0);
                }
                else{
                    bookedPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.middleBerths.get(0)));
                    coach.middleBerths.remove(0);
                }
            }
        }
        ticket = new Ticket(bookedPassengers, true);
        confirmTickets.add(ticket);
        System.out.println("Ticket booked successfully");
        System.out.println("PNR number : "+ticket.pnr);
    }
    public void bookRacTickets(List<Passenger> passengers){
        if(coach.racBerths.size() < passengers.size()){
            bookWaitingListTickets(passengers);
            return;
        }
        List<Passenger> racPassengers = new ArrayList<>();
        for(Passenger passenger : passengers){
            racPassengers.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.racBerths.get(0)));
            coach.racBerths.remove(0);
        }
        ticket = new Ticket(racPassengers, true);
        racTickets.add(ticket);
        System.out.println("RAC tickets booked");
        System.out.println("PNR number : "+ticket.pnr);
    }
    public void bookWaitingListTickets(List<Passenger> passengers){
        if(waitingListTickets.size()+passengers.size() > 10){
            System.out.println("No tickets available");
            return;
        }
        List<Passenger> waitingList = new ArrayList<>();
        for(Passenger passenger : passengers){
            waitingList.add(new Passenger(passenger.name, passenger.age, passenger.gender, coach.racBerths.get(0)));
            coach.racBerths.remove(0);
        }
        ticket = new Ticket(waitingList, true);
        waitingListTickets.add(ticket);
        System.out.println("Tickets are in waiting list");
        System.out.println("PNR number : "+ticket.pnr);
    }
    public List<Passenger> getPassengersDetails(Scanner in){
        System.out.println("Enter No.of Tickets : ");
        int n = in.nextInt();
        List<Passenger> passengers = new ArrayList<>();
        for(int i=1;i<=n;i++){
            System.out.println("Enter the passenger name : ");
            in.nextLine();
            String name = in.nextLine();
            System.out.println("Enter the passenger age : ");
            int age = in.nextInt();
            System.out.println("Enter the gender (M or F): ");
            char gender = in.next().charAt(0);
            System.out.println("Enter birth preference(L or M or U) : ");
            char berthPreference = in.next().charAt(0);
            passengers.add(new Passenger(name, age, gender, berthPreference));
        }
        return passengers;
    }
    public void printPassengers(){
        System.out.println("==================================================================================");
        System.out.println("Confirm Tickets");
        for(Ticket ticket : confirmTickets){
            System.out.println("------------------------------------------------------------------");
            System.out.println("PNR : "+ticket.pnr);
            System.out.println("------------------------------------------------------------------");
            for(Passenger p : ticket.passengers){
                System.out.println("     "+p.name+"     "+p.age+"     "+p.age+"     "+p.seatNumber);
            }
            System.out.println("------------------------------------------------------------------");
        }
        System.out.println("==================================================================================");
        System.out.println("RAC Tickets");
        for(Ticket ticket : racTickets){
            System.out.println("------------------------------------------------------------------");
            System.out.println("PNR : "+ticket.pnr);
            System.out.println("------------------------------------------------------------------");
            for(Passenger p : ticket.passengers){
                System.out.println("     "+p.name+"     "+p.age+"     "+p.age+"     "+p.seatNumber);
            }
            System.out.println("------------------------------------------------------------------");
        }
        System.out.println("==================================================================================");
        System.out.println("Waiting List Tickets");
        for(Ticket ticket : racTickets){
            System.out.println("------------------------------------------------------------------");
            System.out.println("PNR : "+ticket.pnr);
            System.out.println("------------------------------------------------------------------");
            for(Passenger p : ticket.passengers){
                System.out.println("     "+p.name+"     "+p.age+"     "+p.age+"     "+p.seatNumber);
            }
            System.out.println("------------------------------------------------------------------");
        }
        System.out.println("==================================================================================");
    }
    public void printAvailableTicket(){
        System.out.println("==================================================================================");
        System.out.println(coach.lowerBerths);
        System.out.println(coach.middleBerths);
        System.out.println(coach.upperBerths);
        System.out.println(coach.racBerths);
        System.out.println("==================================================================================");
    }
    public void initializeCoach(){
        coach = new Coach();
        int num = 1;
        for(int i=1;i<=1;i++){
            for(int j=1;j<=6;j++){
                if(j%3 == 1){
                    coach.lowerBerths.add("LB"+num);
                    num++;
                }
                else if(j%3 == 2){
                    coach.middleBerths.add("MB"+num);
                    num++;
                }
                else if(j%3 == 0){
                    coach.upperBerths.add("UB"+num);
                    num++;
                }
            }
            coach.racBerths.add("RAC"+num);
            coach.racBerths.add("RAC"+num);
            num++;
        }
    }
}
