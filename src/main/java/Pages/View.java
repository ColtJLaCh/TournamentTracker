package Pages;

import HelpfulClasses.UsefulConstants;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class View extends Page {

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals

    //Data
    String dataTourName = "";
    String[] dataTeamArr; //The ordered array of teams to be matched up to players
    String[] dataPlayerArr; //The ordered array of players to matched up to teams
    String[] dataStats; //if teams stats 0 and 1 (Wins/Losses) will be applied to team, all others will be applied to players
    String[][] dataStatsArr; // A 2d array for tracking the stats of each player
    int dataSets = 2; //2 is default set count, sets are the same for all players, so no need to store data in array
    int dataTourStyle = 0; //0 is singles, 1 is teams, not calculated til after data has been received

    TableView[] brackets;

    public View() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        dummyData(); //TODO: Replace this with retrieve method when functionality is added

        Text tourTypeText = new Text("TYPE: " + (dataTourStyle == 0 ? "SINGLES TOURNAMENT" : "TEAMS TOURNAMENT"));

        TableColumn id = new TableColumn("Id");
        TableColumn name = new TableColumn("Name");
        TableColumn wins = new TableColumn("Wins");
        TableColumn losses = new TableColumn("Losses");

        classVBox = new VBox(tourTypeText); //Vbox needed for Top to Bottom layout, add assets here

        int bracketNum = 0;
        for (TableView bracket : brackets) {
            bracket = new TableView();
            bracket.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/3);
            bracket.getColumns().addAll(id,name,wins,losses);
            bracket.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            bracket.setPrefHeight(UsefulConstants.DEFAULT_SCREEN_HEIGHT/10);
            classVBox.getChildren().add(bracket);

            Text setLine = new Text("-------------------------------------------------------------------------------------------------------------------------------------");
            if (bracketNum % (int)Math.ceil(brackets.length/2) == 0
                    && bracketNum >= brackets.length/2 && bracketNum < brackets.length-1) {
                classVBox.getChildren().add(setLine);
            }

            bracketNum++;
        }

        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods
    private void dummyData() {
        //Fake table data, this will be injected into these variables when functionality is programmed

        //Singles
        dataTourName = "DummyTournamentSINGLES";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataStatsArr = new String[dataStats.length][];
        dataStatsArr[0] = new String[] {"0","0","0","0","0","0"}; //Wins
        dataStatsArr[1] = new String[] {"0","0","0","0","0","0"}; //Losses
        dataStatsArr[2] = new String[] {"123.8","147.3","223.6","112.1","667.3","888.1"}; //Time
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort"};
        dataTeamArr = new String[]{"NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM"};
        dataSets = 2;

        //Teams
        /*
        dataTourName = "DummyTournamentTEAM";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataStatsArr[0] = new String[] {"0","0","0","0","0","0"}; //Wins
        dataStatsArr[1] = new String[] {"0","0","0","0","0","0"}; //Losses
        dataStatsArr[2] = new String[] {"123.8","147.3","223.6","112.1","667.3","888.1"}; //Time
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort"};
        dataTeamArr = new String[]{"TEAM1","TEAM1","TEAM1","TEAM2","TEAM2","TEAM2"};
        dataSets = 2;
        */

        //After table has been retrieved
        for (String team : dataTeamArr) {
            if (team != "NO TEAM") dataTourStyle = 1;
        }

        int amntOfSets = dataSets;
        if (dataTourStyle == 0) {
            amntOfSets = (int)Math.ceil(dataPlayerArr.length/dataSets);
        }else{
            amntOfSets = (int)Math.ceil(dataTeamArr.length/dataSets);
        }

        brackets = new TableView[amntOfSets];
    }

    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}

