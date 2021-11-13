package Pages;

import Database.Database;
import HelpfulClasses.UsefulConstants;
import com.sun.javafx.css.StyleCache;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    //TOUR NAME
    TextField tourNameTextField = new TextField();
    VBox tourNameVbox = new VBox(2);

    //STATS
    Label statsLabel = new Label("STATS (wins/losses/time/ect)");
    ArrayList<HBox> stats = new ArrayList<HBox>();
    Button addStatButton = new Button("+ ADD NEW");
    double statPrefWidth = UsefulConstants.DEFAULT_SCREEN_WIDTH/10;
    VBox statsAddVBox = new VBox();

    //TEAMS AND PLAYERS
    ToggleGroup tourStyleRadToggleGroup = new ToggleGroup();
    RadioButton[] tourStyleRadButton = new RadioButton[2];

    //For the two column layout next to stats
    HBox doubleColumnHBox = new HBox(48);


    //Database dbc = Database.getInstance();

    public Create() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        //---------------------------TOURNAMENT NAME---------------------------
        tourNameTextField.setPromptText("Enter tournament name here...");
        tourNameTextField.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/4);

        Label tourNameLabel = new Label("TOURNAMENT NAME (max 35 characters)");
        tourNameLabel.setUnderline(true);
        tourNameLabel.setAlignment(Pos.TOP_LEFT);
        tourNameLabel.setLabelFor(tourNameTextField);

        tourNameVbox.getChildren().addAll(tourNameLabel,tourNameTextField);

        //---------------------------STATS---------------------------
        VBox statVBox = new VBox(2);
        statsLabel.setUnderline(true);
        statsLabel.setAlignment(Pos.TOP_LEFT);
        statsLabel.setLabelFor(statVBox);

        TextField statWins = new TextField("Wins");
        statWins.setDisable(true);
        statWins.setPrefWidth(statPrefWidth);
        Button deleteWins = new Button("X");
        deleteWins.setDisable(true);
        stats.add(new HBox(statWins,deleteWins));

        TextField statLosses = new TextField("Losses");
        statLosses.setDisable(true);
        statLosses.setPrefWidth(statPrefWidth);
        Button deleteLosses = new Button("X");
        deleteLosses.setDisable(true);
        stats.add(new HBox(statLosses,deleteLosses));

        addStatButton.setPrefWidth(statPrefWidth);
        statsAddVBox.getChildren().addAll(stats.get(0),stats.get(1),addStatButton);
        statVBox.getChildren().addAll(statsLabel,statsAddVBox);

        //---------------------------STYLE, TEAMS AND PLAYERS---------------------------

        //TOUR STYLE---------------------------
        VBox tourStyleVBox = new VBox(2);
        Label tourStyleLabel = new Label("Tournament Style");
        HBox tourStyleRadButtonsHBox = new HBox(10);
        tourStyleLabel.setUnderline(true);
        tourNameLabel.setAlignment(Pos.TOP_LEFT);
        tourNameLabel.setLabelFor(tourStyleRadButtonsHBox);
        tourStyleRadButton[0] = new RadioButton("Singles");
        tourStyleRadButton[0].setToggleGroup(tourStyleRadToggleGroup);
        tourStyleRadButton[1] = new RadioButton("Teams");
        tourStyleRadButton[1].setToggleGroup(tourStyleRadToggleGroup);
        tourStyleRadButtonsHBox.getChildren().addAll(tourStyleRadButton[0],tourStyleRadButton[1]);
        tourStyleVBox.getChildren().addAll(tourStyleLabel,tourStyleRadButtonsHBox);



        //---------------------------FINAL LAYOUT---------------------------
        //This is to set up the two columns next to the stats
        VBox column1VBox = new VBox(48);
        VBox column2VBox = new VBox(48);
        column1VBox.getChildren().addAll(tourStyleVBox);
        //column2VBox.getChildren().addAll();
        doubleColumnHBox.getChildren().addAll(statVBox,column1VBox,column2VBox);

        //---------------------------CLASS STUFF---------------------------
        classVBox = new VBox(tourNameVbox,doubleColumnHBox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(50); //Set spacing here
        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods
    private void reconstructClassVBox() {
        classVBox.getChildren().clear();
        classVBox.getChildren().addAll(tourNameVbox,doubleColumnHBox);
    }

    private void reconstructStatVBox() {
        statsAddVBox.getChildren().clear();
        for (var i = 0; i < stats.size(); i++) {
            statsAddVBox.getChildren().add(stats.get(i));
        }
        statsAddVBox.getChildren().add(addStatButton);

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
            statNew.setPrefWidth(statPrefWidth);
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
