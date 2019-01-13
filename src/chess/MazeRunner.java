package chess;

import javafx.scene.canvas.GraphicsContext;

public class MazeRunner implements Runnable {

    private GraphicsContext gc;

    public MazeRunner(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void run() {
        MazeCreator mazeCreator = new MazeCreator(gc);
        mazeCreator.startDigging();
    }
}
