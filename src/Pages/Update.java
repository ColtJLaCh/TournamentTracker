package Pages;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class Update {
    private BorderPane classPane = new BorderPane();

    public Update() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...
        Text exampleText = new Text("Some example text!");

        VBox classVBox = new VBox(exampleText); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    public BorderPane getPane() {
        return this.classPane;
    }
}
