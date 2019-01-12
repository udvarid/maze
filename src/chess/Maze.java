package chess;

public class Maze {

    private int size;
    private Coordinate[][] maze;

    public Maze(int size) {
        this.size = size;
        if (this.size < 3) {
            this.size = 3;
        }
        maze = new Coordinate[this.size][this.size];
        initMaze();
    }

    private void initMaze() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                maze[i][j] = new Coordinate(i, j);
                maze[i][j].setWall(true);
                maze[i][j].setSign(false);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Coordinate[][] getMaze() {
        return maze;
    }
}
