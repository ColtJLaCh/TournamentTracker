package Pages;

import Nodes.TourTab;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**Page
 * Parent class for all visual layouts in app
 * @author Colton LaChance
 */
public class Page {

    protected BorderPane classPane = new BorderPane();
    protected VBox classVBox = new VBox();

    //Local methods

    /**VBox reconstructClassVBox(Node...)
     * Reconstructs main layout Vbox with parameters listed
     * @param nodes
     * @return
     * @author Colton LaChance
     */
    public VBox reconstructClassVBox(Node... nodes) {
        this.classVBox.getChildren().clear();
        for (Node node : nodes) {
            this.classVBox.getChildren().add(node);
        }
        return classVBox;
    }

    //Use this method to get page
    public BorderPane getPane() {
        return this.classPane;
    }

    public void pageSetStyle() {
         /*
          This is just here to add the Black outline to focused nodes,
          while retaining no focus on other nodes like the BG scroll pane for the create page.
         */
        classVBox.setStyle("-fx-focus-color: black;" +
                "-fx-background-insets: 1, 1, 1, 1;");
    }

    //Use this method to call all methods related to class
    public void pageBehavior() {}
}
