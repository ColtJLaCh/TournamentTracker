package Pages;

import HelpfulClasses.UsefulConstants;
import Nodes.PlayerData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.*;

/** Stats extends Page
 * A statistics page for Tournament Tracker application, extends custom parent class Page and utilizes local and inherited methods to create a layout.
 * Displays stats for all players in descending order, stat shown is selected by a ChoiceBox.
 * @author Colton LaChance
 */
public class Stats extends Page {

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals

    ScrollPane classScrollPane = new ScrollPane(); //Used to hold entire classVBox in a ScrollPane

    //Boxes and layout
    HBox statsHBox = new HBox(); //The HBox that holds the left and right VBoxes together

    VBox statsBoxVBox = new VBox(4); //Holds stats ChoiceBox, and potentially some radio buttons holding sorting options
    ChoiceBox<String> statsBox = new ChoiceBox<>();
    int statChoice = 0;
    int playerSpacing = 64;
    VBox statsPlayersVBox = new VBox(playerSpacing);

    VBox statsVBoxLeft = new VBox(); //The left column of the layout

    VBox statsBarVBox = new VBox(playerSpacing-42.48); //TODO: Change to a more mathematically perfect layout, but until then, this number seems to work well enough

    VBox statsVBoxRight = new VBox(); //The right column of the layout

    //Data -- The globals containing all player data to be inserted into a PlayerData object
    ArrayList<String[]> playerData = new ArrayList<>(); //Holds a list of an array of string containing all the players data, fetched from a table
    String dataTourName = ""; //The name of the tournament
    String[] dataTeamArr; //The ordered array of teams to be matched up to players
    ArrayList<String> uniqueTeams = new ArrayList<String>(); //An arrayList containing only unique values of teams
    String[] dataPlayerArr; //The ordered array of players to matched up to teams
    String[] dataStats; //if teams stats 0 and 1 (Wins/Losses) will be applied to team, all others will be applied to players
    String[][] dataStatsArr; // A 2d array for tracking the stats of each player

    /**Stats constructor
     * Sets up the layout using globals and methods for the Stats page
     * @author Colton LaChance
     */
    public Stats() {
        dummyData();

        //Stats choice box layout
        statsBox.setMinWidth(UsefulConstants.DEFAULT_SCREEN_HEIGHT/6);
        statsBox.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_HEIGHT/6);
        statsBox.getItems().addAll(dataStats);
        statsBox.setValue(dataStats[0]);

        statsBoxVBox.getChildren().addAll(statsBox);
        statsBoxVBox.setPadding(new Insets(4,4,4,4));
        statsBoxVBox.setStyle("-fx-border-color: gray;" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-border-style: dashed;");

        //Players and stats layout
        insertData();
        populatePlayerAndStatsVBox();

        //Combine all VBoxs and HBoxes and change any other properties/styles
        statsVBoxLeft.getChildren().addAll(statsBoxVBox,statsPlayersVBox);
        statsVBoxLeft.setStyle("-fx-border-color: black;" +
                "-fx-border-width: 0 2 0 0;" +
                "-fx-border-style: solid;");
        statsVBoxLeft.setPadding(new Insets(0,0,UsefulConstants.DEFAULT_SCREEN_HEIGHT/2,0));
        statsPlayersVBox.setAlignment(Pos.TOP_RIGHT); //Set the players Vbox to the right side of the left column vbox

        statsVBoxRight.getChildren().addAll(statsBarVBox);
        statsVBoxRight.setPadding(new Insets(36,0,0,0));

        statsHBox.getChildren().addAll(statsVBoxLeft,statsVBoxRight);
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

    /**dummyData()
     * Initializes some dummy data to be inserted into the layout for testing, will be replaced with fetch from table method
     * @author Colton LaChance
     */
    private void dummyData() {
        //Fake table data, this will be injected into these variables when functionality is programmed

        //Singles
        /*
        dataTourName = "DummyTournamentSINGLES";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort"};
        dataTeamArr = new String[]{"NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
        */

        //Teams
        ///*
        dataTourName = "DummyTournamentTEAM";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort","Sherry","Donkey"};
        dataTeamArr = new String[]{"TEAM1","TEAM2","TEAM3","TEAM4","TEAM5","TEAM6","TEAM7","TEAM8"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
       // */

        Random rand = new Random();
        for (int p = 0; p < dataPlayerArr.length; p++) {
            for (int s = 0; s < dataStats.length; s++) {
                dataStatsArr[p][s] = String.valueOf(rand.nextInt(20));
            }
        }
    }

    /**insertData()
     * Inserts fetched data from some globals into PlayerData objects
     * @author Colton LaChance
     */
    private void insertData() {
        String lastTeam = "";
        for (String team : dataTeamArr) {
            if (lastTeam != team) {
                uniqueTeams.add(team);
                lastTeam = team;
            }
        }

        for (int p = 0; p < dataPlayerArr.length; p++) {
            String pId = String.valueOf(p);
            String[] dataToAdd = new PlayerData(pId, dataPlayerArr[p],dataTeamArr[p],dataStatsArr[p]).getData();
            playerData.add(dataToAdd);
        }
    }

    /**populatePlayerAndStatsVBox()
     * Sorts the playerData list, then instantiates and reconstructs the nodes for layout to reflect the current stat selected, also some positioning things
     */
    private void populatePlayerAndStatsVBox() {
        //Create custom comparator for parsing and comparing stats
        Comparator<String[]> comparator = new Comparator<String[]>() {
            public int compare(String[] strings, String[] otherStrings) {
                if (isNumber(strings[3+statChoice]) && isNumber(otherStrings[3+statChoice])) {
                    return Double.compare(Double.parseDouble(strings[3 + statChoice]), Double.parseDouble(otherStrings[3 + statChoice]));
                }
                return 0;
            }
        };
        //Sort playerData with custom comparator
        playerData.sort(comparator.reversed());

        //Instantiate arrays of information and nodes for layout
        Label[] players = new Label[playerData.size()];
        Rectangle[] statBar = new Rectangle[playerData.size()];
        Label[] statNums = new Label[playerData.size()];

        //Clear necessary VBoxes
        statsPlayersVBox.getChildren().clear();
        statsBarVBox.getChildren().clear();

        //Form layout
        double baseStatVal = 0; //This is used to create a starting length for the stat bars, based on the highest value
        for (int i = 0; i < playerData.size(); i++) {
            //Create the player labels
            players[i] = new Label((playerData.get(i)[1]) + "\n(" + (playerData.get(i)[2]) + ")");
            players[i].setTextAlignment(TextAlignment.CENTER);
            players[i].setPadding(new Insets(10,10,0,0));
            statsPlayersVBox.getChildren().add(players[i]); //Add them to respective VBox

            //Create the rectangles
            double statVal = Double.parseDouble(playerData.get(i)[3+statChoice]); //Current parsed stat value from table
            statBar[i] = new Rectangle();
            statBar[i].setFill(new Color(0.80,0.85,0,1)); //Greenish yellow
            if (i == 0) baseStatVal = statVal; //If this is the first index, the base length is set
            statBar[i].setWidth(statVal/baseStatVal*UsefulConstants.DEFAULT_SCREEN_WIDTH/2); //Set the base length to half the size of default width, all other stats will be fractions of this
            statBar[i].setHeight(48);

            //Create the numbers on the rectangles
            statNums[i] = new Label(String.valueOf(statVal));
            statNums[i].setOpacity(0.8); //Set the opacity to blend it in
            statNums[i].setAlignment(Pos.CENTER);
            statNums[i].setPadding(new Insets(0,0,0,4));
            statNums[i].setTranslateY(-56); //Move it up so it's overlapping
            statsBarVBox.getChildren().addAll(statBar[i],statNums[i]); //Add the bars and nums to respective VBox
        }
    }

    /** isNumber(String input)
     * @param input The string inputted, to be checked whether or not is double
     * @return returns true if double, false if not
     */
    static private boolean isNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Methods to add to pageBehavior

    /**onStatsBoxValueChanged()
     * Execute some local methods and get the index of the ChoiceBox on value changed
     * @author Colton LaChance
     */
    private void onStatsBoxValueChanged() {
        statsBox.setOnAction(event -> {
            for (int i = 0; i < statsBox.getItems().size(); i++) {
                if (statsBox.getValue() == statsBox.getItems().get(i)) statChoice = i;
            }

            populatePlayerAndStatsVBox();

            reconstructClassVBox(statsHBox);
        });
    }

    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {
        onStatsBoxValueChanged();
    }
}
