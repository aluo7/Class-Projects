import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.geometry.Pos;
import java.util.Random;

public class DessertGame extends Application {

    private int score = 0;

    @Override
    public void start(final Stage stage) {
        // Step 3 & 4
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 640, 480);
        stage.setTitle("Dessert in the Desert JavaFX Game");

        // Step 5
        Label scoreLabel = new Label("Score: " + score);
        borderPane.setTop(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.TOP_LEFT);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            Platform.exit();
        });
        borderPane.setBottom(exitButton);
        BorderPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);

        // Step 6
        Pane pane = new Pane();
        borderPane.setCenter(pane);
        BorderPane.setAlignment(pane, Pos.CENTER);

        // Step 7
        Button d1 = new Button("Dessert");
        Button d2 = new Button("Desert");
        Button d3 = new Button("Desert");
        Button d4 = new Button("Desert");
        Button d5 = new Button("Desert");
        Button d6 = new Button("Desert");
        Button d7 = new Button("Desert");
        Button d8 = new Button("Desert");

        // Step 8
        Random r = new Random();
        Button[] b = new Button[] { d1, d2, d3, d4, d5, d6, d7, d8 };

        randomizeButtonPositions(r, b);

        for (int i = 0; i < b.length; i++) {
            pane.getChildren().add(b[i]);

            if (b[i].getText().equals("Dessert")) {
                b[i].setOnAction(event -> {
                    // Step 9
                    score++;
                    scoreLabel.setText("Score: " + score);
                    // Step 10
                    exitButton.requestFocus();
                    randomizeButtonPositions(r, b);
                });
            } else if (b[i].getText().equals("Desert")) {
                b[i].setOnAction(event -> {
                    // Step 9
                    score--;
                    scoreLabel.setText("Score: " + score);
                    // Step 10
                    exitButton.requestFocus();
                    randomizeButtonPositions(r, b);
                });
            }
        }

        // Step 10
        exitButton.requestFocus();

        // drawing the things
        stage.setScene(scene);
        stage.show();
    }

    private void randomizeButtonPositions(Random r, Button[] b) {
        for (int i = 0; i < b.length; i++) {
            b[i].setLayoutX(r.nextInt(600));
            b[i].setLayoutY(r.nextInt(400));
        }
    }

    public static void main(String[] args) {
        Application.launch();
    }
}