import java.util.Random;

/**
 * Created by antonis on 18/03/16.
 */
public class Agent {
    private int id;
    private boolean alive;
    public Move nextMove;
    Square currentSquare;
    Square [] adjSquares;
    public Agent(int id) {
        this.id = id;
        alive = true;
    }

    public int getName(){
      return id;
    }

    void decideMove(){
        Random r = new Random();

        boolean foundMove = false;
        if (currentSquare.getTree()!=3) {
            lookAround();
            if (adjSquares[0] != null && adjSquares[0].getTree()==3){
                nextMove = Move.Up;
                foundMove = true;
            }
            if (adjSquares[1] != null && adjSquares[1].getTree()==3){
                nextMove = Move.Down;
                foundMove = true;
            }
            if (adjSquares[2] != null && adjSquares[2].getTree()==3){
                nextMove = Move.Left;
                foundMove = true;
            }
            if (adjSquares[3] != null && adjSquares[3].getTree()==3){
                nextMove = Move.Right;
                foundMove = true;
            }
            if (!foundMove) {
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
                }
            }
        }
        else {
            System.out.println("Already Here");
        }
    }

    void lookAround(){
        adjSquares = World.returnAdj(id);
    }

    public void realityCheck() {
        currentSquare = World.returnCurrent(id);
        decideMove();
    }
    public Move getNextMove() {
        return nextMove;
    }

    public Move makeMove() {
        Move currentMove = nextMove;
        nextMove = null;
        return currentMove;
    }

    void die() {
        alive = false;
    }
}
