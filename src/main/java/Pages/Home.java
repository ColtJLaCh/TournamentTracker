package Pages;
import HelpfulClasses.UsefulConstants;
import LoginCredentials.Login;
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

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */


public class Home extends Page {

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals
    private Label loginLabel = new Label("Sign in to Database");
    private TextField username = new TextField();
    private PasswordField password = new PasswordField();
    private Button loginButton = new Button();
    private VBox loginVBox = new VBox();
    private Text loginErrorMessage = new Text();
    Login login = new Login();

    public Home() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...
        ImageView titleHomePage = new ImageView(new Image("./images/hometitletext.png"));
        ImageView tourImgHomePage1 = new ImageView(new Image("./images/homepagetourimg.png"));
        ImageView tourImgHomePage2 = new ImageView(new Image("./images/homepagetourimg.png"));

        HBox titleHBox = new HBox(tourImgHomePage1,titleHomePage,tourImgHomePage2);
        titleHBox.setAlignment(Pos.TOP_CENTER);


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
        loginErrorMessage.setFill(new Color(1,0,0,0)); // <---- You can set the fill opacity to make the error message visable/invisible
        loginErrorMessage.setText("Invalid username or password");
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

    //Methods to add to pageBehavior
    private void onLogin(String username, String password) {
        //System.out.println("Loggin in as " + username + " with password " +password);
        if(login.getUsers().contains(username)){
            if(login.checkPassword(username, password)){
                System.out.println("Logging in...");
                /*
                Insert code to switch scenes here!

                 */
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
