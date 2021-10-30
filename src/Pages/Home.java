package Pages;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class Home {

    BorderPane parentPane = new BorderPane();
    Scene scene = new Scene(parentPane,1024,768);

    BorderPane classPane = new BorderPane();

    public Home(BorderPane parentPane) {
        this.parentPane = parentPane;
    }

    public Scene getScene() {
        return this.scene;
    }

    private static void createScene() {
    }
}
