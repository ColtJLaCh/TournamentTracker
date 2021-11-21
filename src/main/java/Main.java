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

    public Page page;
    BorderPane pageLayout;
    Home home = new Home();
    Create create = new Create();
    Delete delete = new Delete();
    Update update;
    Stats stats = new Stats();
    View view = new View();

    Scene scene;

    //Build a menubar
    MenuBar menu = new MenuBar();

    //Create TabPane
    TabPane tabPane = new TabPane();

    //RootVbox
    VBox rootVBox = new VBox();

    enum Pages {
        HOME,
        CREATE,
        UPDATE,
        DELETE,
        STATS,
        VIEW
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        //Build menu items
        Menu fileMenu = new Menu("File");
        Menu viewMenu = new Menu("View");

        //File Menu items
        MenuItem createNew = new MenuItem("Create New...");
        createNew.setOnAction(e->{
            changePage(Pages.CREATE);
            System.out.println("Moving to create");
        });
        MenuItem updateTour = new MenuItem("Update Tournament");
        MenuItem deleteTour = new MenuItem("Delete Tournament");
        deleteTour.setOnAction(e->{
            changePage(Pages.DELETE);
            System.out.println("Moving to delete");
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e->{
            System.exit(0);
        });

        //View menu items
        MenuItem viewTour = new MenuItem("View Tournament");
        viewTour.setOnAction(e->{
            changePage(Pages.VIEW);
            System.out.println("Moving to view");
        });
        MenuItem viewStats = new MenuItem("View Statistics");
        viewStats.setOnAction(e->{
            changePage(Pages.STATS);
            System.out.println("Moving to stats");
        });

        fileMenu.getItems().addAll(createNew,updateTour,deleteTour,exit);
        viewMenu.getItems().addAll(viewTour,viewStats);

        //Add menu items to the bar
        menu.getMenus().addAll(fileMenu, viewMenu);

        //Set current page
        changePage(Pages.HOME);

        //Add all elements together
        reconstructRootVBox();

        //Set root Vbox to maintian top-bottom layout
        root.setTop(rootVBox);

        //root styles
        root.setStyle("-fx-background-color: #E2E2E2;");

        //Show scene!
        scene = new Scene(root,1240,780);
        stage.setTitle("Tournament Tracker");
        stage.setScene(scene);
        stage.show();
    }

    private void changePage(Pages pageInd) {
        switch (pageInd) {
            case HOME:
                page = new Home();
            break;
            case CREATE:
                page = new Create();
            break;
            case DELETE:
                page = new Delete();
            break;
            case UPDATE:
                page = new Update();
            break;
            case VIEW:
                page = new View();
            break;
            case STATS:
                page = new Stats();
            break;
        }
        pageLayout = page.getPane();
        page.pageSetStyle();
        //Start page behavior
        page.pageBehavior();
        reconstructRootVBox();
    }

    private void reconstructRootVBox() {
        rootVBox.getChildren().clear();
        rootVBox.getChildren().addAll(menu,tabPane,pageLayout);
    }
}
