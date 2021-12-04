package Pages;

import Database.Database;
import Database.DBConst;
import java.sql.*;
import HelpfulClasses.UsefulConstants;
import Nodes.TourTab;
import Nodes.TournamentList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.sql.DriverManager;

/** Create extends Page
 * Creates a tournament and using the Database class, uploads a table to a Database.
 * @author Colton LaChance
 */
public class Create extends Page {

    //Initialze the database as global for use
    Database dbc = Database.getInstance();

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals
    ScrollPane classScrollPane = new ScrollPane();

    //Parent Tab
    TourTab parentTab;


    //SECTIONS ------- These are the different sections global variables, needed for functionality.

    //TOUR NAME
    TextField tourNameTextField = new TextField();
    VBox tourNameVbox = new VBox(2);

    //STATS
    TournamentList statList = new TournamentList("STATS (wins/losses/time/ect)",2,UsefulConstants.DEFAULT_SCREEN_WIDTH/10);

    //TEAMS AND PLAYERS
    int tourStyle = 0; //0 = Players, 1 = Teams
    ToggleGroup tourStyleRadToggleGroup = new ToggleGroup();
    RadioButton[] tourStyleRadButton = new RadioButton[2];

    TournamentList playerList = new TournamentList("Players",2,UsefulConstants.DEFAULT_SCREEN_WIDTH/10);

    Label teamsLabel = new Label("Teams (First cell is Team name)");
    VBox teamsVBox = new VBox(10);
    Button addTeam = new Button("+ ADD TEAM");

    //SETS
    VBox counterVBox = new VBox(4);
    int setCount = 2;
    TextField counter = new TextField(String.valueOf(setCount));
    Button counterUp = new Button();
    Button counterDown = new Button();

    //CREATE
    Button createButton = new Button("CREATE TOURNAMENT");
    Label errorText = new Label("ERROR: All names must be under 50 characters");

    //MISC -------- These are for layout or any other parts of functionality unrelated to sections

    //For the two column layout next to stats
    VBox column1VBox = new VBox(48);
    VBox column2VBox = new VBox(48);
    HBox doubleColumnHBox = new HBox(48);

    //Data
    String dataTourName = "";
    int dataTourStyle = tourStyle; //0 is singles, 1 is teams
    String[] dataTeamName; //On singles this will be set to NO TEAM
    String[][] dataPlayerNames; //if teams, first dimension is the player, the second dimension is the team number, if singles team number will be 0
    String[] dataStats; //if teams stats 0 and 1 will be applied to team, all others will be applied to players
    int dataSets = setCount;

    /**Create() CONSTRUCTOR
     * This is where all of the local layout variables go, as well as some functionality, only needed to be declared once.
     * Separated into multiple sections defined by the layout for easier navigation
     */
    public Create(TourTab parentTab) {
        this.parentTab = parentTab;

        reconstructClassVBox();
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        //---------------------------TOURNAMENT NAME---------------------------
        tourNameTextField.setPromptText("Enter tournament name here...");
        tourNameTextField.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/4);

        Label tourNameLabel = new Label("TOURNAMENT NAME (max 50 characters)");
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
        teamsLabel.setAlignment(Pos.TOP_LEFT);
        teamsLabel.setLabelFor(teamsVBox);
        teamsLabel.setUnderline(true);
        teamsVBox.getChildren().addAll(teamsLabel,addTeam);



        //---------------------------SETS---------------------------

        /*REMOVED UNTIL FARTHER NOTICE FOR DUE TO UN-NECCESSARY COMPLEXITY

        Label setsLabel = new Label("Sets (amount of players per set)");
        setsLabel.setUnderline(true);
        setsLabel.setLabelFor(counterVBox);

        HBox counterHBox = new HBox(10);
        Label counterLabel = new Label("Sets of Sub-Brackets");
        counterLabel.setLabelFor(counter);
        counterLabel.setTranslateY(6); //Move label down slightly to center it
        counterLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        counter.setFont(Font.font("Mono-space", FontWeight.BOLD, 12));
        counter.setAlignment(Pos.CENTER);
        counter.setPrefSize(30,30);
        counter.setMaxSize(30,30);
        counter.setTextFormatter(new TextFormatter<String>(change -> { //This lambda forces sets textfield to only contain 2 digits and always have at least a 0 value
            if (change.getControlNewText().length() <= 2 && isNumber(change.getControlNewText())) {
                if (isNumber(counter.getText())) {
                    setCount = Integer.parseInt(counter.getText());
                }
                return change;
            }else if (change.getControlNewText().length() > 0) {
                return null;
            }else{
                change.setText("0");
                setCount = 0;
                return change;
            }
        }));
        counterHBox.getChildren().addAll(counter,counterLabel);

        //Counter buttons for sets
        counterUp.setPrefSize(30,30);
        counterUp.setId("counter");
        counterUp.setStyle("#counter {" +
                "-fx-background-color: white; " +
                "-fx-shape: 'M 300 50 250 100 350 100 z';" +
                "-fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);" +
                "}" +
                "#counter:pressed {" +
                "-fx-background-color: black;" +
                "}");
        counterUp.setOnMouseClicked(up -> {
            if (setCount < 99) {
                setCount += 1;
            }else{
                setCount = 0;
            }

            counter.setText(String.valueOf(setCount));
        });

        counterDown.setPrefSize(30,30);
        counterDown.setId("counter");
        counterDown.setStyle("#counter {" +
                "-fx-background-color: white; " +
                "-fx-shape: 'M 300 50 250 100 350 100 z';" +
                "-fx-effect: dropshadow(gaussian, black, 1, 1, 0, 0);" +
                "}" +
                "#counter:pressed {" +
                "-fx-background-color: black;" +
                "}");
        counterDown.setRotate(180);
        counterDown.setOnMouseClicked(down -> {
            if (setCount > 0) {
                setCount -= 1;
            }else{
                setCount = 99;
            }
            counter.setText(String.valueOf(setCount));
        });

        counterVBox.setAlignment(Pos.CENTER_LEFT);
        counterVBox.getChildren().addAll(setsLabel,counterUp,counterHBox,counterDown);
        */

        //---------------------------CREATE---------------------------
        VBox createVBox = new VBox(2);
        Label createLabel = new Label("Create tournament with parameters");
        createLabel.setUnderline(true);
        createLabel.setLabelFor(createVBox);
        createButton.setPrefSize(240,40);
        createButton.setMinSize(240,40);
        createButton.setMaxSize(240,40);
        Font franklinGothicMedium12 = Font.font("Franklin Gothic Medium", 12);
        errorText.setFont(franklinGothicMedium12);
        errorText.setTextFill(new Color(1,0,0,1)); // <---- You can set the fill opacity to make the error message visable/invisible
        errorText.setOpacity(0);
        createVBox.getChildren().addAll(createLabel,createButton,errorText);


        //---------------------------FINAL LAYOUT---------------------------
        //This is to set up the two columns next to the stats
        column1VBox.getChildren().addAll(tourStyleVBox,playerList);
        column2VBox.getChildren().addAll(counterVBox,createVBox);
        doubleColumnHBox.getChildren().addAll(statList,column1VBox,column2VBox);

        //---------------------------CLASS STUFF---------------------------
        classVBox = new VBox(tourNameVbox,doubleColumnHBox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(50); //Set spacing here
        classScrollPane.setContent(classVBox);
        classScrollPane.setStyle("-fx-background: #E2E2E2;" + //This is to make the scrollpane in the background transparent and unfocusable
                "-fx-focus-color: transparent;" +
                "-fx-background-insets: 0, 0, 0, 0;");
        classScrollPane.setMinSize(UsefulConstants.DEFAULT_SCREEN_WIDTH,UsefulConstants.DEFAULT_SCREEN_HEIGHT-100);
        classScrollPane.setMaxSize(UsefulConstants.DEFAULT_SCREEN_WIDTH,UsefulConstants.DEFAULT_SCREEN_HEIGHT-100);

        classPane.setTop(classScrollPane);//Set it to top to place all content directly under menu
    }

    //Local methods
    /** isNumber(String input)
     * @param input The string inputted, to be checked whether or not is integer
     * @return returns true if int, false if not
     */
    static private boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** reloadTeams()
     * Adding a new Team or swapping to Team Tournament Style, this method reloads all functionality for the teams. Ie: On click events for the create new and delete buttons
     * @author Colton LaChance
     */
    public void reloadTeams() {
        for (int i = 1; i < teamsVBox.getChildren().size()-1; i++) {
            VBox teamTourList = (VBox) teamsVBox.getChildren().get(i);
            TournamentList team = (TournamentList) teamTourList.getChildren().get(1);
            addToTourList(team,"NEW PLAYER");
        }
    }

    /**collectData()
     * This method collects all needed data for creating a new table and stores it into the data globals initialized at start of class.
     * @author Colton LaChance
     */
    public void collectData() {
        //TOUR NAME
        dataTourName = tourNameTextField.getText().replaceAll("\s", "_");

        //TOUR STYLE
        dataTourStyle = tourStyle;
        if (tourStyle == 0) { //Tour style == 0 (SINGLES)

            //PLAYER NAMES
            dataPlayerNames = new String[1][playerList.getArrList().size()];
            dataTeamName = new String[1];
            dataTeamName[0] = "NO TEAM";
            for (int i = 0; i < playerList.getArrList().size(); i++) {
                TextField playerTextField = (TextField)playerList.getArrList().get(i).getChildren().get(0);
                dataPlayerNames[0][i] = playerTextField.getText();
                System.out.println("SINGLES PLAYER #" + i + ": " + dataPlayerNames[0][i]);
            }

        }else{ //Tour style == 1 (TEAMS)

            //PLAYER AND TEAM NAMES
            int teamsVBoxSize = teamsVBox.getChildren().size()-2; // -2 for label and add button
            int largestTeamSize = 0;

            for (int i = 1; i <= teamsVBoxSize; i++) {
                VBox teamVBox = (VBox)teamsVBox.getChildren().get(i);
                TournamentList teamList = (TournamentList)teamVBox.getChildren().get(1);
                if (teamList.getArrList().size() > largestTeamSize) largestTeamSize = teamList.getArrList().size();
            }

            dataPlayerNames = new String[teamsVBoxSize][largestTeamSize];
            dataTeamName = new String[teamsVBoxSize];

            for (int t = 1; t <= teamsVBoxSize; t++) {
                VBox team = (VBox)teamsVBox.getChildren().get(t);
                HBox teamBox = (HBox)team.getChildren().get(0);
                TextField teamTextField = (TextField) teamBox.getChildren().get(0);

                dataTeamName[t-1] = teamTextField.getText();
                TournamentList teamTourList = (TournamentList) team.getChildren().get(1);

                for (int p = 0; p < teamTourList.getArrList().size(); p++) {
                    TextField playerTextField = (TextField) teamTourList.getArrList().get(p).getChildren().get(0);
                    dataPlayerNames[t-1][p] = playerTextField.getText();

                    //FOR DEBUGGING
                    //System.out.println("TEAM PLAYER #" + p + " ON TEAM " + dataTeamName[t-1] + ": " + dataPlayerNames[t-1][p]);
                }

            }
        }

        //STATS
        dataStats = new String[statList.getArrList().size()];
        for (int s = 0; s < statList.getArrList().size(); s++) {
            HBox statCell = statList.getArrList().get(s);
            TextField statTextField = (TextField)statCell.getChildren().get(0);
            dataStats[s] = statTextField.getText().replaceAll("\s", "_");
            System.out.println("STAT #" + s + ": " + dataStats[s]);
        }
        dataSets = setCount;
    }

    //Methods to add to pageBehavior

    /** addToTourList(tourList, initialCellString)
     * This method was originally used for reloading the class vbox, but that's no longer the case as it would only reload once when this was called.
     * Now it's just here because I've used it multiple times throughout the class and am keeping it for future touch ups if some extra functionality is needed.
     * @param tourList
     * @param initialCellString
     * @author Colton LaChance
     */
    public void addToTourList(TournamentList tourList, String initialCellString) {
        tourList.addToList(initialCellString,false, false);
    }

    /**createTeam()
     * This method reconstructs the teamsVbox when the addTeam button is clicked, appending a new team to the end.
     * @author Colton LaChance
     */
    private void createTeam() {
        addTeam.setOnMouseClicked(e-> {
            TournamentList newTourList = new TournamentList("",2,UsefulConstants.DEFAULT_SCREEN_WIDTH/10);
            TextField teamName = new TextField("NEW TEAM");
            Button removeTeam = new Button("REMOVE");
            HBox teamHBox = new HBox();
            teamHBox.getChildren().addAll(teamName,removeTeam);

            newTourList.addToList("NEW PLAYER",false,true);

            VBox teamVBox = new VBox(2);
            teamVBox.getChildren().addAll(teamHBox,newTourList);

            teamsVBox.getChildren().remove(teamsVBox.getChildren().size()-1);
            teamsVBox.getChildren().addAll(teamVBox,addTeam);

            classVBox = reconstructClassVBox(tourNameVbox,doubleColumnHBox);

            removeTeam.setOnMouseClicked(r->{
                teamsVBox.getChildren().remove(teamVBox);
                classVBox = reconstructClassVBox(tourNameVbox,doubleColumnHBox);
                reloadTeams();
            });
            reloadTeams();
        });
        reloadTeams();
    }

    /**setTournamentStyle()
     * switches tournament style when selecting one of the two radio buttons Singles or Teams
     * @author Colton LaChance
     */
    public void setTournamentStyle() {
        tourStyleRadButton[0].setOnMouseClicked(e0->{
            tourStyle = 0;
            column1VBox.getChildren().set(1,playerList);
            reconstructClassVBox(tourNameVbox,doubleColumnHBox);
        });
        tourStyleRadButton[1].setOnMouseClicked(e1->{
            tourStyle = 1;
            column1VBox.getChildren().set(1,teamsVBox);
            reconstructClassVBox(tourNameVbox,doubleColumnHBox);
        });
    }

    /**createTournament()
     * Compiles the data together from the collectData method and inputs it into the Database using the Database .class sql methods,
     * after checking if all information is valid. (Matches the sql table parameters)
     * @author Colton LaChance
     */
    public void createTournament() {
        createButton.setOnMouseClicked(createTour ->{
            collectData();

            //Check if all data is valid
            Boolean dataTourNameOkay = true;
            Boolean dataTeamNameOkay = true;
            Boolean dataPlayerNameOkay = true;
            Boolean dataStatsOkay = true;

            if (dataTourName.length() > 50) dataTourNameOkay = false;
            if (tourStyle == 0) {
                for (int p = 0; p < dataPlayerNames[0].length; p++) {
                    if (dataPlayerNames[0][p].length() > 50) {
                        dataPlayerNameOkay = false;
                        errorText.setText("ERROR: All names must be under 50 characters");
                    }
                }
            }else {
                for (int t = 0; t < teamsVBox.getChildren().size()-2; t++) {
                    if (dataTeamName[t].length() > 50) dataTeamNameOkay = false;
                    for (int p = 0; p < dataPlayerNames[t].length; p++) {
                        if (dataPlayerNames[t][p].length() > 50) {
                            dataPlayerNameOkay = false;
                            errorText.setText("ERROR: All names must be under 50 characters");
                        }
                    }
                }
            }

            for (int s = 0; s < dataStats.length; s++) {
                if (dataStats[s].length() > 50) {
                    dataStatsOkay = false;
                    errorText.setText("ERROR: All names must be under 50 characters");
                }
                for (int s2 = 0; s2 < dataStats.length; s2++) {
                    if (s != s2 && dataStats[s].equals(dataStats[s2])) {
                        dataStatsOkay = false;
                        errorText.setText("ERROR: Duplicate stat names!");
                    }
                }
            }

            if (dataTourNameOkay &&
                    dataTeamNameOkay &&
                    dataPlayerNameOkay &&
                    dataStatsOkay) {
                errorText.setOpacity(0);
                try {
                    if(dbc.createTable(dataTourName,dataSets,dataStats,dbc.getConnection())){
                        for (int i = 0; i < dataTeamName.length; i++) {
                            dbc.addToTable(dataTourName, dataPlayerNames[i],dataTeamName[i], dbc.getConnection());
                        }
                        System.out.println("CREATED TABLE!");
                        parentTab.setLockTab(true);
                        parentTab.setText(dataTourName);
                        parentTab.changePage(TourTab.Pages.VIEW);
                    } else {
                        //If tournament already exists, send error
                        errorText.setOpacity(1);
                    }

                }catch (Exception e){
                    //dbc.close();
                    e.printStackTrace();
                }
            }else{
                errorText.setOpacity(1);
            }
        });
    }

    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {
        addToTourList(statList,"NEW STAT");
        addToTourList(playerList,"NEW PLAYER");
        createTeam();
        setTournamentStyle();
        createTournament();
    }
}
