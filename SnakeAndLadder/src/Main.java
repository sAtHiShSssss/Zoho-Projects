import java.io.PrintStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter No.of players : ");
        int n = in.nextInt();
        List<Player> players = new ArrayList<>();
        for(int i=1;i<=n;i++){
            System.out.println("Enter player "+i+" name : ");
            String name = in.next();
            players.add(new Player(name));
        }
        Board board = new Board();
        board.initializeBoard();
        board.printBoard(players);

        while(true){
            for(Player player : players){
                System.out.println("Player "+player.getName()+" press enter to roll dice : ");
                in.nextLine();

                int dice = board.rollDice();
                System.out.println("Dice Number : "+dice);

                int position = player.getPosition()+dice;
                if(board.snakes.containsValue(position)){
                    System.out.println("Ohh no landed on a snake... ");
                    player.setPosition(board.snakes.get(position));
                }
                else if(board.ladders.containsKey(position)){
                    System.out.println("Yahh climbed a ladder...");
                    player.setPosition(board.ladders.get(position));
                }
                else{
                    player.setPosition(position);
                    if(player.getPosition() >= board.winning_position){
                        System.out.println("Hurrah Player "+player.getName()+" wins...!");
                        return;
                    }
                }
                board.printBoard(players);
            }
        }
    }
}