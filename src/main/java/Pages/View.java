package Pages;

import Database.Database;
import HelpfulClasses.UsefulConstants;
import Nodes.PlayerData;

import Nodes.TourTab;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.sql.*;
import java.util.*;


/** View extends Page
 * A view page for Tournament Tracker application, extends custom parent class Page and utilizes local and inherited methods to create a layout.
 * Displays current tournament information, which players are competing against who and they're wins/losses, as well as an option to show more information on any player/team
 * @author Colton LaChance
 */
public class View extends Page {

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals
    Database dbc = Database.getInstance();

    TourTab parentTab;

    //Data -- The globals containing all player data to be inserted into a PlayerData object
    ObservableList<String[]> playerData;

    String dataTourName = "";
    String[] dataTeamArr; //The ordered array of teams to be matched up to players
    ArrayList<String> uniqueTeams = new ArrayList<String>(); //An arrayList containing only unique values of teams
    String[] dataPlayerArr; //The ordered array of players to matched up to teams
    String[] dataStats; //if teams stats 0 and 1 (Wins/Losses) will be applied to team, all others will be applied to players
    String[][] dataStatsArr; // A 2d array for tracking the stats of each player
    int dataSets = 2; //2 is default set count, sets are the same for all players, so no need to store data in array
    int dataTourStyle = 0; //0 is singles, 1 is teams, not calculated til after data has been received

    TableView<String>[] brackets; //Each set of players/teams competing against eachother ***REMOVED DUE TO UN-NECCESSARY COMPELXITY*** Now only the first index is created currently
    ArrayList<String> columns = new ArrayList<String>(); //The columns of the brackets TableView

    HBox layoutHBox = new HBox(32); //For the observable list and player names on hover
    Label teamPlayerNames = new Label(""); //This is here for team tournaments, appears on the right side on mouse hover over team

    public View(TourTab parentTab, String tourName) {
        this.parentTab = parentTab;
        dataTourName = tourName;
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        //Create the dummy data then insert it into the globals
        //dummyData();
        collectData();
        insertData();

        //Add a text label to top left corner to show tournament type
        Text tourTypeText = new Text("TYPE: " + (dataTourStyle == 0 ? "SINGLES TOURNAMENT" : "TEAMS TOURNAMENT"));

        //Add to class Vbox before creating brackets
        classVBox = new VBox(tourTypeText); //Vbox needed for Top to Bottom layout, add assets here

        createBrackets(); //Create the tableview layout

        ImageView tourImgHomePage1 = new ImageView(new Image("./images/homepagetourimg.png"));

        classVBox.getChildren().add(tourImgHomePage1);

        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods

    /**dummyData()
     * Initializes some dummy data to be inserted into the layout for testing, will be replaced with fetch from table method
     * @author Colton LaChance
     */
    private void dummyData() {
        //Fake table data, this will be injected into these variables when functionality is programmed

        //Singles
        dataTourName = "DummyTournamentSINGLES";
        dataStats = new String[]{"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse", "Jamie", "Bobby", "Billy", "Brody", "Mort"};
        dataTeamArr = new String[]{"NO TEAM", "NO TEAM", "NO TEAM", "NO TEAM", "NO TEAM", "NO TEAM"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
        dataSets = 2;


        //Teams
        /*
        dataTourName = "DummyTournamentTEAM";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort","Sherry","Donkey"};
        dataTeamArr = new String[]{"TEAM1","TEAM2","TEAM3","TEAM4","TEAM5","TEAM6","TEAM7","TEAM8"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
        dataSets = 2;
        */

        Random rand = new Random();
        for (int p = 0; p < dataPlayerArr.length; p++) {
            for (int s = 0; s < dataStats.length; s++) {
                dataStatsArr[p][s] = String.valueOf(rand.nextInt(20));
            }
        }
    }

    private void collectData() {
        Connection conn = dbc.getConnection();
        try {
            System.out.println(dataTourName);
            ResultSet tourData = dbc.getTable(dataTourName,conn);
            ResultSetMetaData tdmd = tourData.getMetaData();

            int rows = 0;
            tourData.last();
            rows = tourData.getRow();
            tourData.beforeFirst();

            dataStats = new String[tdmd.getColumnCount()-4];
            dataPlayerArr = new String[rows];
            dataTeamArr = new String[rows];
            dataStatsArr = new String[dataPlayerArr.length][dataStats.length];

            int row = 0;
            while (tourData.next()) {
                for (int st = 0; st < dataStats.length; st++) {
                    dataStats[st] = tdmd.getColumnName(st+5);
                    dataStatsArr[row][st] = tourData.getString(dataStats[st]);
                }

                dataPlayerArr[row] = tourData.getString("Player");
                dataTeamArr[row] = tourData.getString("TeamName");

                dataSets = Integer.parseInt(tourData.getString("Sets"));
                row += 1;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving tables.");
        }
    }

    /**insertData()
     * Inserts fetched data from some globals into PlayerData objects
     * @author Colton LaChance
     */
    private void insertData() {
        String lastTeam = "";
        for (String team : dataTeamArr) {
            if (!team.equals("NO TEAM")) dataTourStyle = 1;

            if (lastTeam != team) {
                uniqueTeams.add(team);
                lastTeam = team;
            }
        }

        /*REMOVED DUE TO UN-NECCESSARY COMPELXITY
        int amntOfSets = dataSets;
        if (dataTourStyle == 0) {
            amntOfSets = (dataPlayerArr.length+dataSets-1)/dataSets;
        }else{
            if (!uniqueTeams.isEmpty())
                amntOfSets = (uniqueTeams.size()+dataSets-1)/dataSets;
        }
        */

        brackets = new TableView[1];

        playerData = FXCollections.observableArrayList();
        for (int p = 0; p < dataPlayerArr.length; p++) {
            String pId = String.valueOf(p);
            String[] dataToAdd = new PlayerData(pId, dataPlayerArr[p],dataTeamArr[p],dataStatsArr[p]).getData();
            playerData.add(dataToAdd);
        }

        columns.add("Id");
        columns.add("Name");
        columns.add("Team");
        for (int cToAdd = 0; cToAdd < dataStats.length; cToAdd++) {
            columns.add(dataStats[cToAdd]);
        }
    }

    /**createBrackets()
     * Using information from the global properties and playerData, creates layout for the brackets[] TableViews, then adds them to the classVBox
     */
    private void createBrackets() {
        //int bracketNum = 0; //REMOVED
        int lastTeamInd = 0; //This is for teams, makes sure that every bracket has unique teams playing against eachother

        for (TableView bracket : brackets) {
            ObservableList<String[]> bracketData = FXCollections.observableArrayList(); //Create the observable list to insert into bracket

            //Initialize and set bracket parameters
            bracket = new TableView();
            bracket.setMinWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/3);
            bracket.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/3);
            bracket.setFixedCellSize(25);
            bracket.setEditable(false);

            //Create cell factories for columns, based on String[] indexes
            for (int c = 0; c < columns.size(); c++) {
                if (c == 0 || (dataTourStyle == 0 ? c == 1 : c == 2) || c == 3 || c == 4) { //This is here to only show id, team/player name, and wins/losses
                    TableColumn tc = new TableColumn();
                    tc = new TableColumn(columns.get(c));
                    final int index = c;
                    tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                            return new SimpleStringProperty((p.getValue()[index]));
                        }
                    });
                    bracket.getColumns().add(tc);
                }
            }

            //This checks to see what data from playerData to load into the current bracket. Whether singles or teams
            if (dataTourStyle == 0) {
                if (playerData.size() > 1) {
                    for (int p = 0; p < dataPlayerArr.length; p++) {
                        bracketData.add(playerData.get(p));
                    }
                }else{
                    bracketData.add(playerData.get(0));
                }
            }else{
                int teamNum = 0;
                for (int t = lastTeamInd; t < playerData.size(); t++) { //lastTeamIndex is used to find teams in current bracket
                    if (!playerData.get(t)[2].equals(playerData.get(lastTeamInd)[2]) || t == 0) {
                        playerData.get(t)[0] = String.valueOf(teamNum);
                        teamNum++;
                        bracketData.add(playerData.get(t));
                        lastTeamInd = t;
                    }
                }
            }


            //Set the items and set some params of the current bracket (TableView)
            bracket.setItems(bracketData);


            //Get the row for the team, add player names to label
            if (dataTourStyle == 1) {
                bracket.setRowFactory(tableView -> {
                    final TableRow<String[]> row = new TableRow<>();

                    row.hoverProperty().addListener((observable) -> {
                        final String[] player = row.getItem();

                        if (row.isHover() && player != null) {
                            teamPlayerNames.setText("PLAYERS : ");
                            for (int p = 0; p < dataPlayerArr.length; p++) {
                                if (playerData.get(p)[2].equals(player[2])) {
                                    teamPlayerNames.setText(teamPlayerNames.getText() + "\n" + playerData.get(p)[1]);
                                }
                            }
                        }
                    });

                    return row;
                });
            }

            bracket.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            bracket.setMinHeight(bracket.getFixedCellSize()*(bracket.getItems().size()+1));
            bracket.setMaxHeight(bracket.getFixedCellSize()*(bracket.getItems().size()+1));

            //Add bracket to classVBox
            layoutHBox.getChildren().add(bracket);
            if (dataTourStyle == 1) layoutHBox.getChildren().add(teamPlayerNames);
            classVBox.getChildren().add(layoutHBox);

            //bracketNum++; REMOVED
        }
    }

    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}

