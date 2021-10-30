package Pages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Delete {

    BorderPane parentPane = new BorderPane();
    Scene scene = new Scene(parentPane,1024,768);

    BorderPane classPane = new BorderPane();

    public Delete(BorderPane parentPane) {
        this.parentPane = parentPane;
    }

    public Scene getScene() {
        return this.scene;
    }

    private static void createScene() {
    }
}
