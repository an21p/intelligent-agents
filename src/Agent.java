import java.util.ArrayList;
import java.util.Random;

/**
 * Created by antonis on 18/03/16.
 */
public class Agent {
    private int id;
    private boolean alive;
    int health;
    int successfulMoves;
    ArrayList<Integer> healthHistory;
    ArrayList<Memory> past;
    public Move nextMove;
    Memory currentMemory;
    Square currentSquare;
    Square [] adjSquares;


    public Agent(int id) {
        this.id = id;
        alive = true;
        health = 50;
        successfulMoves = 0;
        healthHistory = new ArrayList<Integer>();
        past = new ArrayList<Memory>();
    }

    public int getName(){
        return id;
    }

    void decideMove(){
        if (shouldMove()) {
            Random r = new Random();

            boolean foundMove = false;
            lookAround();
            while (!foundMove) {
                //move randomly
                int random = r.nextInt(4);

                if (adjSquares[random] != null) {
                    switch (random) {
                        case 0 :
                            nextMove = Move.Up;
                            break;
                        case 1 :
                            nextMove = Move.Down;
                            break;
                        case 2 :
                            nextMove = Move.Left;
                            break;
                        case 3 :
                            nextMove = Move.Right;
                            break;
                    }
                    foundMove = true;
                }
            }
        }
    }

    void lookAround(){
        adjSquares = World.returnAdj(id);
    }

    boolean shouldMove() {
        if (healthHistory.size()>5 && successfulMoves>0) {
            //if it explored compare
            int sum =0;
            for (Integer aHealthHistory : healthHistory) {
                sum += aHealthHistory;
            }
            int average = sum/healthHistory.size();
            return average < healthHistory.get(healthHistory.size() - 1);

        } else {
            //if it did not move yet it should (explore)
            return true;
        }
    }

    public void realityCheck() {
        if (alive) {
            int prevHealth = health;
            currentSquare = World.returnCurrent(id);
            health--;
            health-=currentSquare.getTree();
            if (health<0) die();
            else healthHistory.add(prevHealth-health);
            decideMove();
            System.out.println("Health "+health);
            for (Memory m : past) {
                System.out.print(m.getReverseMove()+" "+m.getTreeFound()+",  ");
            }
            System.out.println();
        }
        else {
            System.out.println("Agent "+id+" is dead");
        }
    }
    public Move getNextMove() {
        return nextMove;
    }

    public Move makeMove() {
        Move currentMove = nextMove;
        currentMemory = new Memory(currentMove, currentSquare.getTree());
        nextMove = null;
        return currentMove;
    }

    public void successfulMove() {

        past.add(currentMemory);
        successfulMoves++;
    }

    void die() {
        alive = false;
    }
}

class Memory {
    Move moveMade;
    Move reverseMove;
    int treeFound;

    public Memory(Move m, int t) {
        moveMade = m;
        reverseMove = returnReverse(m);
        treeFound = t;
    }

    Move returnReverse(Move m) {
        Move reverse = m;
        switch (m) {
            case Up:    reverse= Move.Down;   break;
            case Down:  reverse= Move.Up;     break;
            case Left:  reverse= Move.Right;  break;
            case Right: reverse= Move.Left;   break;
        }
        return reverse;
    }

    public String getMoveMade() {
        return  moveMade.toString();
    }

    public Move getReverseMove() {
        return  reverseMove;
    }

    public int getTreeFound() {
        return  treeFound;
    }
}