import java.util.*;
public class Coach {
    int coachNumber;
    List<String> lowerBerths = null;
    List<String> middleBerths = null;
    List<String> upperBerths = null;
    List<String> racBerths = null;
    static int t = 1;

    public Coach(){
        this.coachNumber = t++;
        this.lowerBerths = new ArrayList<>();
        this.middleBerths = new ArrayList<>();
        this.upperBerths = new ArrayList<>();
        this.racBerths = new ArrayList<>();
    }
}
