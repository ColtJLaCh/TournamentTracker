package Pages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Update {

    BorderPane parentPane = new BorderPane();
    Scene scene = new Scene(parentPane,1024,768);

    BorderPane classPane = new BorderPane();

    public Update(BorderPane parentPane) {
        this.parentPane = parentPane;
    }

    public Scene getScene() {
        return this.scene;
    }

    private static void createScene() {
    }
}
