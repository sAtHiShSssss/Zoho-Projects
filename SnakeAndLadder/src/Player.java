public class Player {
    private String name;
    private int id;
    private int position;
    static int num = 1;

    public Player(String name){
        this.id = num++;
        this.name = name;
        this.position = 0;
    }

    public String getName(){
        return name;
    }
    public void setPosition(int position){
        this.position = position;
    }
    public int getPosition(){
        return position;
    }

}
