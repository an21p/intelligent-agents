import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by antonis on 18/03/16.
 */
public class World {
    static int SIZE = 6;
    static int AGENTS = 4;
    static Random R = new Random();
    static Square[][] grid;
    ArrayList<Agent> agents;
    static ArrayList<Point> agentPositions;
    public World() {
        initialiseGrid();
        initialiseWorld();
    }
    void initialiseGrid() {
        grid = new Square[SIZE][SIZE];
        for (int r=0; r<SIZE; r++) {
            for (int c=0; c<SIZE; c++) {
                grid[r][c]= new Square();
            }
        }
    }

    void initialiseWorld() {
        agents = new ArrayList<Agent>(AGENTS);
        agentPositions = new ArrayList<Point>(AGENTS);
        Point position = new Point(0,0);
        for (int i=0; i<AGENTS; i++) {
            agents.add(i,new Agent(i));

            boolean unique = false;
            while (!unique) {
                boolean exists = false;
                position = new Point(R.nextInt(SIZE),R.nextInt(SIZE));
                for (Point other : agentPositions) {
                    if (other.getR() == position.getR() && other.getC() == position.getC()) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    unique = true;
                }
            }

            agentPositions.add(i,position);
            grid[position.getR()][position.getC()].setAgent(i);
        }
        /*
        for (Point p : agentPositions) {
            p.print();
        }
        */
        System.out.println();
    }

    void moveAgent(Point a, Move direction) {
        int agent1 = grid[a.getR()][a.getC()].getAgent();
        Square destination;// = grid[a.getR()][a.getC()];
        Point b = new Point(a);
        int other;// = agent1;
        //System.out.println(direction);
        switch (direction) {
            case Up :
                b = new Point(a.getR(),a.getC()-1);
                break;
            case Down :
                b = new Point(a.getR(),a.getC()+1);
                break;
            case Left :
                b = new Point(a.getR()-1,a.getC());
                break;
            case Right :
                b = new Point(a.getR()+1,a.getC());
                break;
        }

        destination = grid[b.getR()][b.getC()];
        other = destination.getAgent();

        if (other==-1) {
            //can move
            destination.setAgent(agent1);
            grid[a.getR()][a.getC()].removeAgent();
            agentPositions.set(agent1,b);
            agents.get(agent1).successfulMove();
            System.out.println("Agent " + agent1 + " moved from point " + a.getR() + "," + a.getC() + " to point " + b.getR() + "," + b.getC());
        }else {
            //cannot move
            System.out.println("Agent "+agent1+" could not move from point "+a.getR()+","+a.getC()+" to point "+b.getR()+","+b.getC()+" because agent "+other+" was there");
        }
    }

    void update() {
        for (Agent a:agents) {
            System.out.print(a.getName() +" ");
            a.realityCheck();
        }

        System.out.println();
        for (Agent a:agents) {
            if (a.getNextMove()!=null) {
                moveAgent(agentPositions.get(a.getName()), a.makeMove());

            }
        }
        System.out.println();
        printWorld();

    }

    void printWorld(){
        for (int r=0; r<SIZE; r++) {
            System.out.print(" | ");
            for (int c=0; c<SIZE; c++) {

                grid[c][r].printSquare();
                System.out.print(" | ");
            }
            System.out.println();
        }

        System.out.println();
/*
        for (Agent a:agents) {
            System.out.print(a.getName() + " ");
        }
        System.out.println();
        for (Point p:agentPositions) {
            System.out.println(p.getX()+" "+p.getC());
    }
*/
    }

    public static Square returnCurrent(int agentID) {
        Point pos = agentPositions.get(agentID);
        return grid[pos.getR()][pos.getC()];
    }

    public static Square[] returnAdj(int agentID) {
        Point pos = agentPositions.get(agentID);
        Square[] adjSquares = new Square[4];
        //UP
        if (pos.getC()>0) {
            //can move up
            adjSquares[0] = grid[pos.getR()][pos.getC()-1];
        } else {
            //cannot move up
            adjSquares[0] = null;
        }
        //DOWN
        if (pos.getC()<SIZE-1) {
            //can move down
            adjSquares[1] = grid[pos.getR()][pos.getC()+1];
        } else {
            //cannot move down
            adjSquares[1] = null;
        }
        //LEFT
        if (pos.getR()>0) {
            //can move left
            adjSquares[2] = grid[pos.getR()-1][pos.getC()];
        } else {
            //cannot move left
            adjSquares[2] = null;
        }
        //DOWN
        if (pos.getR()<SIZE-1) {
            //can move right
            adjSquares[3] = grid[pos.getR()+1][pos.getC()];
        } else {
            //cannot move right
            adjSquares[3] = null;
        }
        System.out.print("---- Agent :"+agentID+" sees U | D | L | U ----     ");
        for (Square s:adjSquares) {
            if (s != null) {
                s.printSquare();
                System.out.print("  |  ");
            } else {
                System.out.print("_:null_  |  ");
            }
        }
        System.out.println();
        return adjSquares;
    }

    public static void main(String[] args) {
        boolean nextTick = true;
        //System.out.println("Wellow horld");
        World w = new World();
        w.printWorld();
        while (nextTick) {
            w.update();
            Scanner reader = new Scanner(System.in);
            System.out.print("Move On? (Y/N)");
            String input = reader.next();
            if (!(input.equals("Y") || input.equals("y"))){
                nextTick=false;
            }
        }
    }
}

class Square {
    static Random R = new Random();
    static int TREES = 4;
    int tree;
    int agent;

    public Square() {
        tree = R.nextInt(TREES);
        this.agent = -1;
    }
    public Square(int agent) {
        tree = R.nextInt(TREES);
        this.agent = agent;
    }
    public int getTree() {
        return tree;
    }
    public void setAgent (int agent) {
        this.agent = agent;
    }
    public void removeAgent () {
        this.agent = -1;
    }
    public int getAgent () {
        return  this.agent;
    }
    public void printSquare() {
        if (agent==-1) {
            System.out.print("T:" + tree+" A:_");
        } else {
            System.out.print("T:" + tree+" A:"+agent);
        }
    }
}

class Point {
    int r;
    int c;
    public Point() {
        r=0; c=0;
    }
    public Point(Point a) {
        r=a.getR(); c=a.getC();
    }
    public Point(int r, int c) {
        setR(r);
        setC(c);
    }
    public void setR(int r) {
        this.r=r;
    }
    public void setC(int c) {
        this.c=c;
    }
    public int getR() {
        return r;
    }
    public int getC() {
        return c;
    }
    public void print() {
        System.out.print("(" + r + "," + c + ")");
    }
}