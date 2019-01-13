package chess;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class MazeCreator {

    private Maze maze;
    private Random randomNumber = new Random();
    private int size;
    private Coordinate antre;
    private Coordinate exit;
    private int doorIn;
    private int doorOut;
    private GraphicsContext gc;

    public MazeCreator(GraphicsContext gc) {
        this.maze = new Maze(50);
        this.gc = gc;
        this.size = maze.getSize();
        doorIn = randomNumber.nextInt(this.size);
        doorOut = randomNumber.nextInt(this.size);
        exitCreator();
    }

    private void exitCreator() {
        antre = new Coordinate(doorIn, 0);
        antre.setWall(false);
        exit = new Coordinate(doorOut, this.size - 1);
        exit.setWall(false);
        maze.getMaze()[doorIn][0] = antre;
        maze.getMaze()[doorOut][this.size - 1] = exit;
    }

    public Maze getMaze() {
        return maze;
    }

    public void printMaze() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (antre.getX() == i && antre.getY() == j ||
                        exit.getX() == i && exit.getY() == j) {
                    gc.setStroke(Color.BLACK);
                } else if (i == 0 || j == 0 || i == this.size - 1 || j == this.size - 1) {
                    gc.setStroke(Color.BLUE);
                } else if (maze.getMaze()[i][j].isWall()) {
                    gc.setStroke(Color.GREEN);
                } else if (maze.getMaze()[i][j].isSign()) {
                    gc.setStroke(Color.CYAN);
                } else {
                    gc.setStroke(Color.WHITE);
                }
                gc.strokeLine(i * 10, j * 10, i * 10, j * 10);


            }
        }
    }

    public void startDigging() {
        boolean exitWasFound = false;
        while (!exitWasFound) {
            printMaze();
            exitWasFound = dig(antre);
            if (!exitWasFound) {
                maze = new Maze(this.size);
                exitCreator();
            }
        }
    }

    private boolean dig(Coordinate spot) {

        spot.setWall(false);
        spot.setSign(true);
        changeOutfit(Color.CYAN, spot);


        if (new Coordinate(spot.getX(), spot.getY() + 1).equals(this.exit)) {
            exit.setSign(true);
            return true;
        }

        List<Coordinate> ways = new ArrayList<>();
        if (spot.getX() + 1 < this.size - 1 && !spot.equals(antre)) {
            Coordinate way1 = maze.getMaze()[spot.getX() + 1][spot.getY()];
            ways.add(way1);
        }
        if (spot.getX() > 1 && !spot.equals(antre)) {
            Coordinate way2 = maze.getMaze()[spot.getX() - 1][spot.getY()];
            ways.add(way2);
        }
        if (spot.getY() + 1 < this.size - 1) {
            Coordinate way3 = maze.getMaze()[spot.getX()][spot.getY() + 1];
            ways.add(way3);
        }
        if (spot.getY() > 1 && !spot.equals(antre)) {
            Coordinate way4 = maze.getMaze()[spot.getX()][spot.getY() - 1];
            ways.add(way4);
        }

        Collections.shuffle(ways);
        Iterator<Coordinate> iterator = ways.iterator();
        while (iterator.hasNext()) {
            Coordinate coordinateToCheck = iterator.next();
            if (wayIsOk(coordinateToCheck)) {
                if (dig(coordinateToCheck)) {
                    return true;
                } else {
                    coordinateToCheck.setSign(false);
                    changeOutfit(Color.WHITE,coordinateToCheck);
                }
            } else {
                iterator.remove();
            }
        }


        return false;
    }

    private void changeOutfit(Color color, Coordinate spot) {
        gc.setStroke(color);
        gc.strokeLine(spot.getX() * 10, spot.getY() * 10, spot.getX() * 10, spot.getY() * 10);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean wayIsOk(Coordinate way) {

        boolean result = true;
        if (!way.isWall() || moreThanOneExit(way)) {
            result = false;
        }

        return result;

    }

    private boolean moreThanOneExit(Coordinate wayToCheck) {

        int numberOfWays = 0;

        if (wayToCheck.getX() + 1 < this.size) {
            Coordinate way1 = maze.getMaze()[wayToCheck.getX() + 1][wayToCheck.getY()];
            if (!way1.isWall() && !way1.equals(this.exit)) {
                numberOfWays++;
            }
        }
        if (wayToCheck.getX() > 0) {
            Coordinate way2 = maze.getMaze()[wayToCheck.getX() - 1][wayToCheck.getY()];
            if (!way2.isWall() && !way2.equals(this.exit)) {
                numberOfWays++;
            }
        }
        if (wayToCheck.getY() + 1 < this.size) {
            Coordinate way3 = maze.getMaze()[wayToCheck.getX()][wayToCheck.getY() + 1];
            if (!way3.isWall() && !way3.equals(this.exit)) {
                numberOfWays++;
            }
        }
        if (wayToCheck.getY() > 0) {
            Coordinate way4 = maze.getMaze()[wayToCheck.getX()][wayToCheck.getY() - 1];
            if (!way4.isWall() && !way4.equals(this.exit)) {
                numberOfWays++;
            }
        }
        return numberOfWays > 1;
    }


}
