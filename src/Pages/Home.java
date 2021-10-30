package Pages;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class Home {

    Pane parentPane = new Pane();
    Scene scene = new Scene(parentPane,1024,768);

    public Home(Pane parentPane) {
        this.parentPane = parentPane;
    }

    public Scene getScene() {
        return this.scene;
    }
}
