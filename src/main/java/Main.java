import Pages.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Scene currentScene;

    Page page;
    BorderPane pageLayout;
    Home home = new Home();
    Create create = new Create();
    Delete delete;
    Update update;
    Stats stats;
    View view;

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        VBox rootVBox = new VBox();

        //Build a menubar
        MenuBar menu = new MenuBar();
        //Build menu items
        Menu fileMenu = new Menu("File");
        Menu viewMenu = new Menu("View");

        //File Menu items
        MenuItem createNew = new MenuItem("Create New...");
        MenuItem updateTour = new MenuItem("Update Tournament");
        MenuItem deleteTour = new MenuItem("Delete Tournament");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e->{
            System.exit(0);
        });

        //View menu items
        MenuItem viewTour = new MenuItem("View Tournament");
        MenuItem viewStats = new MenuItem("View Statistics");

        fileMenu.getItems().addAll(createNew,updateTour,deleteTour,exit);
        viewMenu.getItems().addAll(viewTour,viewStats);

        //Add menu items to the bar
        menu.getMenus().addAll(fileMenu, viewMenu);

        //Create TabPane
        TabPane tabPane = new TabPane();

        //Set current page
        page = home;
        pageLayout = page.getPane();
        //Start page behavior
        page.pageBehavior();

        //Add all elements together
        rootVBox.getChildren().addAll(menu,tabPane,pageLayout);

        //Set root Vbox to maintian top-bottom layout
        root.setTop(rootVBox);

        //root styles
        root.setStyle("-fx-background-color: #E2E2E2"); //Very light gray

        //Show scene!
        Scene scene = new Scene(root,1240,780);
        stage.setTitle("Tournament Tracker");
        stage.setScene(scene);
        stage.show();
    }
}
