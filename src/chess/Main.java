package chess;

public class Main {

    public static void main(String[] args) {
        MazeCreator mazeCreator = new MazeCreator(50);
        mazeCreator.startDigging();
        mazeCreator.printMaze();
    }
}
