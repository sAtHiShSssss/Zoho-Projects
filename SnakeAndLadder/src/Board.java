import java.util.*;

public class Board {
     int winning_position = 100;
    int [][]board = new int[10][10];
    Map<String, List<Integer>> snakes = new HashMap<>();
    Map<String, List<Integer>> ladders = new HashMap<>();

    public void initializeBoard(){
        int num = 1, i=9;
        while(i >= 0){
            for(int j=0;j<10;j++){
                board[i][j] = num++;
            }
            i--;
            for(int j=9;j>=0;j--){
                board[i][j] = num++;
            }
            i--;
        }

        snakes.put("S1", new ArrayList<>(Arrays.asList(32, 10)));
        snakes.put("S2", new ArrayList<>(Arrays.asList(34, 6)));
        snakes.put("S3", new ArrayList<>(Arrays.asList(48, 26)));
        snakes.put("S4", new ArrayList<>(Arrays.asList(62, 18)));
        snakes.put("S5", new ArrayList<>(Arrays.asList(88, 24)));
        snakes.put("S6", new ArrayList<>(Arrays.asList(95, 56)));
        snakes.put("S7", new ArrayList<>(Arrays.asList(97, 78)));

        ladders.put("L1", new ArrayList<>(Arrays.asList(1, 38)));
        ladders.put("L2", new ArrayList<>(Arrays.asList(4, 14)));
        ladders.put("L3", new ArrayList<>(Arrays.asList(8, 30)));
        ladders.put("L4", new ArrayList<>(Arrays.asList(21, 42)));
        ladders.put("L5", new ArrayList<>(Arrays.asList(28, 76)));
        ladders.put("L6", new ArrayList<>(Arrays.asList(50, 67)));
        ladders.put("L7", new ArrayList<>(Arrays.asList(71, 92)));
        ladders.put("L8", new ArrayList<>(Arrays.asList(80, 99)));

    }

    public void printBoard(List<Player> players){
        List<Integer> playersPositions=  new ArrayList<>();
        for(Player player : players){
            playersPositions.add(player.getPosition());
        }
        System.out.println("======================================");
        for(int []b : board){
            for(int n : b){
                if(playersPositions.contains(n)){
                    System.out.print("P"+playersPositions.indexOf(n)+"  ");
                }
                else if(snakes.containsKey(n)){
                    System.out.print("SH  ");
                }
                else if(snakes.containsValue(n)){
                    System.out.print("ST  ");
                }
                else if(ladders.containsKey(n)){
                    System.out.print("LB  ");
                }
                else if(ladders.containsValue(n)){
                    System.out.print("LT  ");
                }
                else{
                    System.out.print(n+"  ");
                }
            }
            System.out.println();
        }
        System.out.println("======================================");
    }
    public int rollDice(){
        return new Random().nextInt(6)+1;
    }
}
