package Pages;
import Database.Database;
import HelpfulClasses.UsefulConstants;
import LoginCredentials.Login;
import Main.Main;
import Nodes.TourTab;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */


public class Home extends Page {

    Main mainClass;

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals
    private Label loginLabel = new Label("Sign in to Database");
    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Button loginButton = new Button();
    private VBox loginVBox = new VBox();
    private Text loginErrorMessage = new Text();
    //Create a Login so we can use its functionality on the home page.
    Login login = new Login();

    public Home(Main mainClass) {
        this.mainClass = mainClass;

        //Initialize layout assets here, ImageViews, Panes, Text etc...
        ImageView titleHomePage = new ImageView(new Image("./images/hometitletext.png"));
        //ImageView tourImgHomePage1 = new ImageView(new Image("./images/homepagetourimg.png"));
        //ImageView tourImgHomePage2 = new ImageView(new Image("./images/homepagetourimg.png"));
        ImageView sportsGraphic = new ImageView(new Image("./images/sports_graphic.png"));

        titleHomePage.setFitHeight(350);
        titleHomePage.setFitWidth(700);

        sportsGraphic.setFitHeight(420);
        sportsGraphic.setFitWidth(512);

        HBox titleHBox = new HBox(titleHomePage,sportsGraphic);
        titleHBox.setAlignment(Pos.TOP_CENTER);


        //Animations for Images

        TranslateTransition titleDrop = new TranslateTransition(Duration.millis(2000), titleHomePage);
        titleDrop.setFromY(-2750);
        titleDrop.setToY(50);

        ScaleTransition titleStretch = new ScaleTransition(Duration.millis(215), titleHomePage);
        titleStretch.setByX(0.1);
        titleStretch.setByY(-0.18);
        titleStretch.setCycleCount(2);
        titleStretch.setAutoReverse(true);

        TranslateTransition titleSquash = new TranslateTransition(Duration.millis(215), titleHomePage);
        titleSquash.setFromY(50);
        titleSquash.setToY(70);
        titleStretch.setCycleCount(2);
        titleStretch.setAutoReverse(true);

        ParallelTransition titleSquashAndStretch = new ParallelTransition();
        titleSquashAndStretch.getChildren().addAll(titleStretch, titleSquash);

        FadeTransition graphicFadeIn = new FadeTransition(Duration.millis(2500), sportsGraphic);
        graphicFadeIn.setFromValue(0);
        graphicFadeIn.setToValue(1);

        SequentialTransition fullStartUp = new SequentialTransition();
        fullStartUp.getChildren().addAll(titleDrop, titleSquashAndStretch, graphicFadeIn);

        fullStartUp.play();


        //*******FOR NOAH******* Login stuff!
        username.setPromptText("Username...");
        password.setPromptText("Password...");
        loginButton.setText("Login");
        loginButton.setMinWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/15);
        loginButton.setOnAction(e->{
            onLogin(username.getText(), password.getText());
        });

        Font franklinGothicMedium12 = Font.font("Franklin Gothic Medium", 12);
        loginErrorMessage.setFont(franklinGothicMedium12);
        loginErrorMessage.setFill(new Color(1,0,0,0));
        loginVBox.setSpacing(4);
        loginVBox.getChildren().addAll(loginLabel,username,password,loginButton,loginErrorMessage);
        loginVBox.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/10);
        //************************************


        Text createdBy = new Text("Created by Noah Burrows and Colton LaChance!");
        Font franklinGothicMedium24 = Font.font("Franklin Gothic Medium", 24);
        createdBy.setFont(franklinGothicMedium24);

        classVBox = new VBox(titleHBox,loginVBox,createdBy); //Vbox needed for Top to Bottom layout, add assets here
        classVBox.setAlignment(Pos.TOP_CENTER); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(48); //Set spacing here;

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods


    //Called whenever the user presses the login button. If successful it should take you to the next screen.
    private void onLogin(String username, String password) {
        //System.out.println("Loggin in as " + username + " with password " +password);
        if(login.getUsers().contains(username)){
            if(login.checkPassword(username, password)){
                System.out.println("Logging in...");
                login.loginUser(username);
                //Gather all table names from the user's database
                Database dbc = Database.getInstance();
                Connection conn = dbc.getConnection();
                try {
                    //Instantiate Database here so the recent login credentials may be used.
                    DatabaseMetaData md = conn.getMetaData();
                    ResultSet rs = md.getTables(Database.getDb_name(), null, "%", null);
                    //For each table, add its name to the choicebox


                    TourTab firstTab = null; //This is for getting the first tab

                    while (rs.next()) {
                        TourTab premadeTournament = new TourTab(mainClass,true);
                        premadeTournament.setText(rs.getString(3));
                        premadeTournament.changePage(TourTab.Pages.VIEW);

                        //Grab first tab
                        if (firstTab == null) firstTab = premadeTournament;

                        mainClass.tabPane.getTabs().add(premadeTournament);
                        mainClass.reconstructRootVBox();
                    }

                    //Set it as the focused tab so that it's functionality works without switching
                    if (firstTab != null) firstTab.setFocusedTab();

                    if (mainClass.tabPane.getTabs().isEmpty()) {
                        TourTab newTournament = new TourTab(mainClass,false);
                        mainClass.tabPane.getTabs().add(newTournament);
                        mainClass.reconstructRootVBox();
                    }
                } catch (SQLException e) {
                    System.out.println("Error retrieving tables.");
                }
            } else {
                loginErrorMessage.setFill(new Color(1,0,0,1)); // <---- You can set the fill opacity to make the error message visable/invisible
                loginErrorMessage.setText("Incorrect password");
            }
        } else {
            loginErrorMessage.setFill(new Color(1,0,0,1)); // <---- You can set the fill opacity to make the error message visable/invisible
            loginErrorMessage.setText("Invalid username");
        }
    }

    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}
