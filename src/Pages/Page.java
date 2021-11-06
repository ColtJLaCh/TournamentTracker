package Pages;

import javafx.scene.layout.BorderPane;

public class Page {
    protected BorderPane classPane = new BorderPane();

    //Use this method to get page
    public BorderPane getPane() {
        return this.classPane;
    }

    //Use this method to call all methods related to class
    public void pageBehavior() {}
}
