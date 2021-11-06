package Pages;

import HelpfulClasses.UsefulConstants;
import com.sun.javafx.css.StyleCache;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.util.ArrayList;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class Create extends Page {

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals

    public Create() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        TextField tourNameTextField = new TextField();
        tourNameTextField.setPromptText("Enter tournament name here...");
        tourNameTextField.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/4);

        Label tourNameLabel = new Label("TOURNAMENT NAME (max 35 characters)");
        tourNameLabel.setUnderline(true);
        tourNameLabel.setAlignment(Pos.TOP_LEFT);
        tourNameLabel.setLabelFor(tourNameTextField);

        VBox tourNameVbox = new VBox(2);
        tourNameVbox.getChildren().addAll(tourNameLabel,tourNameTextField);


        VBox classVBox = new VBox(tourNameVbox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(30,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }


    //Use this inherited method to call all methods related to class
    @Override
    public void pageBehavior() {

    }
}
