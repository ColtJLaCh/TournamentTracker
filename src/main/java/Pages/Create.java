package Pages;

import Database.Database;
import HelpfulClasses.UsefulConstants;
import Nodes.TournamentList;
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
    TournamentList statList = new TournamentList("STATS (wins/losses/time/ect)",2,UsefulConstants.DEFAULT_SCREEN_WIDTH/10,false);

    //TEAMS AND PLAYERS
    ToggleGroup tourStyleRadToggleGroup = new ToggleGroup();
    RadioButton[] tourStyleRadButton = new RadioButton[2];

    TournamentList playerList = new TournamentList("Players",2,UsefulConstants.DEFAULT_SCREEN_WIDTH/10,false);
    TournamentList teamList = new TournamentList("Teams",10,UsefulConstants.DEFAULT_SCREEN_WIDTH/10,true);
    /*
    Label teamsLabel = new Label("Teams (First cell is Team name)");
    VBox teamsVBox = new VBox(10);
    ArrayList<TournamentList> teamList = new ArrayList<TournamentList>();

    Button addTeam = new Button("+ ADD TEAM");
    */


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
        statList.addToList("Wins",true,true);
        statList.addToList("Losses",true,true);


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
        tourStyleRadButton[0].setSelected(true);
        tourStyleRadButton[1] = new RadioButton("Teams");
        tourStyleRadButton[1].setToggleGroup(tourStyleRadToggleGroup);
        tourStyleRadButtonsHBox.getChildren().addAll(tourStyleRadButton[0],tourStyleRadButton[1]);
        tourStyleVBox.getChildren().addAll(tourStyleLabel,tourStyleRadButtonsHBox);

        //TEAMS AND PLAYERS---------------------------

        //Players---
        playerList.addToList("NEW PLAYER",false,true);

        //Teams---
        teamList.addToList("NEW TEAM",false,true);
        /*
        teamsLabel.setAlignment(Pos.TOP_LEFT);
        teamsLabel.setLabelFor(teamsVBox);
        teamsVBox.getChildren().add(teamsLabel);
        for (TournamentList team : teamList) {
            teamsVBox.getChildren().add(team);
        }
        teamsVBox.getChildren().add(addTeam);
        */

        //---------------------------FINAL LAYOUT---------------------------
        //This is to set up the two columns next to the stats
        VBox column1VBox = new VBox(48);
        VBox column2VBox = new VBox(48);
        column1VBox.getChildren().addAll(tourStyleVBox,teamList);
        //column2VBox.getChildren().addAll();
        doubleColumnHBox.getChildren().addAll(statList,column1VBox,column2VBox);

        //---------------------------CLASS STUFF---------------------------
        classVBox = new VBox(tourNameVbox,doubleColumnHBox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(50); //Set spacing here
        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods
    /*
    private void createTeam() {
        addTeam.setOnMouseClicked(e-> {
            TournamentList newTourList = new TournamentList("",2,UsefulConstants.DEFAULT_SCREEN_WIDTH/10,true,false);
            newTourList.addToList("NEW TEAM",false,true);
            teamList.add(newTourList);
            teamsVBox.getChildren().remove(teamsVBox.getChildren().size()-1);
            teamsVBox.getChildren().add(newTourList);
            teamsVBox.getChildren().add(addTeam);
           // System.out.println(teamsVBox.getChildren().size());

            classVBox = reconstructClassVBox(tourNameVbox,doubleColumnHBox);
        });

        for (TournamentList team : teamList) {
            addToTourList(team,team.getArrList().isEmpty() ? "NEW TEAM" : "NEW PLAYER");
        }
    }
    */

    //Methods to add to pageBehavior

    /** addToTourList(tourList, initialCellString)
     * This method is used to reconstruct the classVBox after editing any Tournament List, needed to update the values of the list properly on the UI
     * @param tourList
     * @param initialCellString
     * @author Colton LaChance
     */
    public void addToTourList(TournamentList tourList, String initialCellString) {
        tourList.addToList(initialCellString,false, false);
    }

    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {
        addToTourList(statList,"NEW STAT");
        addToTourList(playerList,"NEW PLAYER");
        addToTourList(teamList,"NEW TEAM");
        for (var i = 0; i < teamList.getArrList().size(); i++) {
            VBox teamVBox = (VBox) teamList.getArrList().get(i);
            TournamentList team = (TournamentList) teamVBox.getChildren().get(1);
            addToTourList(team,"NEW PLAYER");
        }

        //createTeam();
    }
}
