import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        //Build a menubar
        MenuBar menu = new MenuBar();
        //Build menu items
        Menu fileMenu = new Menu("File");
        Menu creditsMenu = new Menu("View");

        //File Menu items
        MenuItem createNew = new MenuItem("Create New...");
        MenuItem updateTour = new MenuItem("Update Tournament");
        MenuItem deleteTour = new MenuItem("Delete Tournament");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e->{
            System.exit(0);
        });


        fileMenu.getItems().addAll(createNew,updateTour,deleteTour,exit);
        //Add menu items to the bar
        menu.getMenus().addAll(fileMenu, creditsMenu);
        root.setTop(menu);
        //Create TabPane
        TabPane tabPane = new TabPane();

        Scene scene = new Scene(root,1024,768);
        stage.setTitle("Tournament Tracker");
        stage.setScene(scene);
        stage.show();
    }
}
