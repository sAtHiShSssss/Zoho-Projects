public class Passenger {
    String name;
    int age;
    char gender;
    char berthPreference;
    String seatNumber;
    public Passenger(String name, int age, char gender, char berthPreference){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.berthPreference = berthPreference;
    }
    public Passenger(String name, int age, char gender, String seatNumber){
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
    }
}
