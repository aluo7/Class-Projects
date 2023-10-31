import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
// these imports are used for the First JavaFX Activity
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class JavaFXActivity extends Application {
    @Override
    public void start(final Stage stage) {
        // update this method definition to complete the First JavaFX Activity
        stage.setTitle("CS400: The Key"); // step 1
        Label label = new Label("    The key to making programs fast\n" + // step 2
        "    is to make them do practically nothing.\n" +
        "    -- Mike Haertel");
        Circle circle = new Circle(160, 120, 30); // step 3
        Polygon triangle = new Polygon(160, 120, 200, 220, 120, 220); // step 4
        Group group = new Group(label, circle, triangle);
        Scene scene = new Scene(group,320,240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}