package chess;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    Stage window;
    Scene scene;
    Canvas canvas;
    int sizeOfCanvas = 50;
    ExecutorService threadPool = Executors.newFixedThreadPool(1);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;


        Label label1 = new Label("Push to draw");
        Button button1 = new Button("Create maze");


        HBox hBox = new HBox();
        hBox.getChildren().addAll(button1);

        BorderPane bp = new BorderPane();
        bp.setTop(hBox);


        Group root = new Group();
        canvas = new Canvas(499, 499);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawShapes(gc);
        root.getChildren().add(canvas);

        bp.setCenter(root);

        MazeRunner mazeRunner = new MazeRunner(gc);


        button1.setOnAction(e -> {
            threadPool.submit(mazeRunner);
        });

        scene = new Scene(bp);
        window.setScene(scene);
        window.setTitle("Maze creator");
        window.show();


    }

    private void startMaze(GraphicsContext gc) {
        MazeCreator mazeCreator = new MazeCreator(gc);
        mazeCreator.startDigging();
        mazeCreator.printMaze();
        System.out.println("ready");
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setLineWidth(10);
        gc.fillRect(0, 0, 499, 499);
        gc.setStroke(Color.BLUE);
        gc.strokeLine(0, 0, 0, 499);
        gc.strokeLine(0, 499, 499, 499);
        gc.strokeLine(499, 499, 499, 0);
        gc.strokeLine(499, 0, 0, 0);


    }

}
