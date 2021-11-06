package Pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class Create {
    private BorderPane classPane = new BorderPane();

    public Create() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...
        TextField tourNameTextField = new TextField();
        tourNameTextField.setPromptText("Enter tournament name here...");

        Label tourNameLabel = new Label("TOURNAMENT NAME (max 35 characters)");
        tourNameLabel.setUnderline(true);
        tourNameLabel.setAlignment(Pos.TOP_LEFT);
        tourNameLabel.setLabelFor(tourNameTextField);

        VBox tourNameVbox = new VBox(2);
        tourNameVbox.getChildren().addAll(tourNameLabel,tourNameTextField);


        VBox classVBox = new VBox(tourNameVbox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    public BorderPane getPane() {
        return this.classPane;
    }
}
