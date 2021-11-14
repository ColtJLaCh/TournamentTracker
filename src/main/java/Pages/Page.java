package Pages;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Page {

    protected BorderPane classPane = new BorderPane();
    protected VBox classVBox = new VBox();

    //Local methods
    public VBox reconstructClassVBox(Node... nodes) {
        this.classVBox.getChildren().clear();
        for (Node node : nodes) {
            this.classVBox.getChildren().add(node);
        }
        classVBox.setStyle("-fx-focus-color: black;" +
                "-fx-background-insets: 1, 1, 1, 1;");
        return classVBox;
    }

    //Use this method to get page
    public BorderPane getPane() {
        return this.classPane;
    }

    //Use this method to call all methods related to class
    public void pageBehavior() {}
}
