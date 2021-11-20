package Pages;

import HelpfulClasses.UsefulConstants;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class Stats extends Page {

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals
    ScrollPane classScrollPane = new ScrollPane();

    //Layout
    HBox statsHBox = new HBox();

    VBox statsBoxVBox = new VBox(4);
    ChoiceBox<String> statsBox = new ChoiceBox<>();
    VBox statsPlayersVBox = new VBox(32);
    VBox statsVBoxLeft = new VBox();

    //Data
    String dataTourName = "";
    String[] dataTeamArr; //The ordered array of teams to be matched up to players
    String[] dataPlayerArr; //The ordered array of players to matched up to teams
    String[] dataStats; //if teams stats 0 and 1 (Wins/Losses) will be applied to team, all others will be applied to players
    String[][] dataStatsArr; // A 2d array for tracking the stats of each player

    public Stats() {
        dummyData();

        //Stats choice box
        statsBox.setMinWidth(UsefulConstants.DEFAULT_SCREEN_HEIGHT/6);
        statsBox.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_HEIGHT/6);
        statsBox.getItems().addAll(dataStats);
        statsBox.setValue(dataStats[0]);
        statsBoxVBox.getChildren().addAll(statsBox);
        statsBoxVBox.setPadding(new Insets(4,4,4,4));
        statsBoxVBox.setStyle("-fx-border-color: gray;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-border-style: dashed;");

        //Player names



        statsVBoxLeft.getChildren().addAll(statsBoxVBox,statsPlayersVBox);
        statsHBox.getChildren().addAll(statsVBoxLeft);
        classVBox = new VBox(statsHBox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classScrollPane.setContent(classVBox);
        classScrollPane.setStyle("-fx-background: #E2E2E2;" + //This is to make the scrollpane in the background transparent and unfocusable
                "-fx-focus-color: transparent;" +
                "-fx-background-insets: 0, 0, 0, 0;");
        classScrollPane.setMinSize(UsefulConstants.DEFAULT_SCREEN_WIDTH,UsefulConstants.DEFAULT_SCREEN_HEIGHT-100);
        classScrollPane.setMaxSize(UsefulConstants.DEFAULT_SCREEN_WIDTH,UsefulConstants.DEFAULT_SCREEN_HEIGHT-100);

        classPane.setTop(classScrollPane);//Set it to top to place all content directly under menu
    }

    //Local methods
    private void dummyData() {
        //Fake table data, this will be injected into these variables when functionality is programmed

        //Singles
        ///*
        dataTourName = "DummyTournamentSINGLES";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort"};
        dataTeamArr = new String[]{"NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
        //*/

        //Teams
        /*
        dataTourName = "DummyTournamentTEAM";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort","Sherry","Donkey"};
        dataTeamArr = new String[]{"TEAM1","TEAM2","TEAM3","TEAM4","TEAM5","TEAM6","TEAM7","TEAM8"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
        */

        for (int p = 0; p < dataPlayerArr.length; p++) {
            for (int s = 0; s < dataStats.length; s++) {
                dataStatsArr[p][s] = "0";
            }
        }
    }

    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}
