package Main;

import Nodes.TourTab;
import Pages.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Scene currentScene;

    Home home = new Home(this);

    Scene scene;

    //Build a menubar
    MenuBar menu = new MenuBar();
    MenuItem createNew = new MenuItem("Create New...");
    MenuItem updateTour = new MenuItem("Update Tournament");
    MenuItem deleteTour = new MenuItem("Delete Tournament");
    MenuItem exit = new MenuItem("Exit");

    MenuItem viewTour = new MenuItem("View Tournament");
    MenuItem viewStats = new MenuItem("View Statistics");

    public enum MenuItems {
        MENU_CREATE,
        MENU_UPDATE,
        MENU_DELETE,
        MENU_EXIT,
        MENU_VIEW,
        MENU_STATS
    }

    //Create TabPane
    public TabPane tabPane = new TabPane();

    //RootVbox
    VBox rootVBox = new VBox();

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
        //Set up main menu options here due to independent page switching
       createNew.setOnAction(e -> {
            System.out.println("Moving to create");
            TourTab newTab = new TourTab(this,false);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
            reconstructRootVBox();
        });

        exit.setOnAction(e -> {
            System.exit(0);
        });

        //View menu items

        fileMenu.getItems().addAll(createNew,updateTour,deleteTour,exit);
        viewMenu.getItems().addAll(viewTour,viewStats);

        //Add menu items to the bar
        menu.getMenus().addAll(fileMenu, viewMenu);

        //Set current page

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

    public void reconstructRootVBox() {
        this.rootVBox.getChildren().clear();
        if (tabPane.getTabs().isEmpty() || tabPane.getSelectionModel().getSelectedItem() == null) {
            this.rootVBox.getChildren().addAll(this.menu,this.home.getPane());
            this.home.pageSetStyle();
            this.home.pageBehavior();
        }else {
            this.rootVBox.getChildren().addAll(this.menu, this.tabPane);
        }
    }

    public MenuItem getMenuItem(MenuItems mitem) {
        switch (mitem) {
            case MENU_CREATE:
                return createNew;

            case MENU_DELETE:
                return deleteTour;

            case MENU_EXIT:
                return exit;

            case MENU_UPDATE:
                return updateTour;

            case MENU_STATS:
                return viewStats;

            case MENU_VIEW:
                return viewTour;
        }
        return null;
    }
}
