package Pages;

import HelpfulClasses.UsefulConstants;
import com.sun.javafx.css.StyleCache;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    TextField tourNameTextField = new TextField();
    VBox tourNameVbox = new VBox(2);

    ArrayList<HBox> stats = new ArrayList<HBox>();
    Button addStatButton = new Button("+ADD NEW (max 20 chars)");
    Button[] statDeleteButton = new Button[1];
    VBox statVBox = new VBox();

    public Create() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        tourNameTextField.setPromptText("Enter tournament name here...");
        tourNameTextField.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/4);

        Label tourNameLabel = new Label("TOURNAMENT NAME (max 35 characters)");
        tourNameLabel.setUnderline(true);
        tourNameLabel.setAlignment(Pos.TOP_LEFT);
        tourNameLabel.setLabelFor(tourNameTextField);

        tourNameVbox.getChildren().addAll(tourNameLabel,tourNameTextField);

        stats.add(new HBox(new TextField("Wins")));
        stats.get(0).getChildren().get(0).setDisable(true);
        stats.add(new HBox(new TextField("Losses")));
        stats.get(1).getChildren().get(0).setDisable(true);

        statVBox.getChildren().addAll(stats.get(0),stats.get(1),addStatButton);

        classVBox = new VBox(tourNameVbox,statVBox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(30,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here
        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods
    private void reconstructClassVBox() {
        classVBox.getChildren().clear();
        classVBox.getChildren().addAll(tourNameVbox,statVBox);
    }

    private void reconstructStatVBox() {
        statVBox.getChildren().clear();
        for (var i = 0; i < stats.size(); i++) {
            statVBox.getChildren().add(stats.get(i));
        }
        statVBox.getChildren().add(addStatButton);

        reconstructClassVBox();
    }

    private void deleteStats() {
        stats.remove(stats.size()-1);
        reconstructStatVBox();
    }

    //Methods to add to pageBehavior
    private void addStats() {
        addStatButton.setOnMouseClicked(e->{
            HBox statHBox = new HBox();
            TextField statNew = new TextField("NEW STAT");
            Button deleteStat = new Button("X");
            deleteStat.setOnMouseClicked(de -> {
                deleteStats();
            });
            statHBox.getChildren().addAll(statNew,deleteStat);
            stats.add(statHBox);

            reconstructStatVBox();
        });
    }


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {
        addStats();
    }
}
