package Pages;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Create {

    BorderPane parentPane = new BorderPane();
    Scene scene = new Scene(parentPane,1024,768);

    BorderPane classPane = new BorderPane();

    public Create(BorderPane parentPane) {
        this.parentPane = parentPane;
    }

    public Scene getScene() {
        return this.scene;
    }

    private static void createScene() {
    }
}
