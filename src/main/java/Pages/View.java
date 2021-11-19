package Pages;

import javafx.geometry.Insets;
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


    public View() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        dummyData(); //TODO: Remove this when functionality is added


        Text exampleText = new Text("Some example text!");

        VBox classVBox = new VBox(exampleText); //Vbox needed for Top to Bottom layout, add assets here
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
        dataStatsArr[0] = new String[] {"0","0","0","0","0","0"}; //Wins
        dataStatsArr[1] = new String[] {"0","0","0","0","0","0"}; //Losses
        dataStatsArr[2] = new String[] {"123.8","147.3","223.6","112.1","667.3","888.1"}; //Time
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort"};
        dataTeamArr = new String[]{"NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM"};
        dataSets = 3;

        //Teams
        /*
        dataTourName = "DummyTournamentTEAM";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataStatsArr[0] = new String[] {"0","0","0","0","0","0"}; //Wins
        dataStatsArr[1] = new String[] {"0","0","0","0","0","0"}; //Losses
        dataStatsArr[2] = new String[] {"123.8","147.3","223.6","112.1","667.3","888.1"}; //Time
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort"};
        dataTeamArr = new String[]{"TEAM1","TEAM1","TEAM1","TEAM2","TEAM2","TEAM2"};
        dataSets = 3;
        */

        //After table has been retrieved
        for (String team : dataPlayerArr) {
            if (team != "NO TEAM") dataTourStyle = 1;
        }
    }

    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}

