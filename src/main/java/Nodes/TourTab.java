package Nodes;

import Main.Main;
import Pages.*;
import javafx.application.Application;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class TourTab extends Tab {
    private static TourTab tab;
    private Main mainPage;
    private boolean lockTab;

    //Page stuff
    public Page page;
    BorderPane pageLayout;
    Home home = new Home();
    Create create = new Create();
    Delete delete = new Delete();
    Update update;
    Stats stats = new Stats();
    View view = new View();

    public enum Pages {
        HOME,
        CREATE,
        UPDATE,
        DELETE,
        STATS,
        VIEW
    }



    public TourTab(Main mainPage) {
        this.mainPage = mainPage;
        page = new Create();
        this.pageLayout = page.getPane();
        this.setText("NEW TOUR");
        this.setOnClosed(e -> {
            mainPage.reconstructRootVBox();
            System.out.println("Moving to home");
        });
        this.setContent(pageLayout);

        mainPage.getMenuItem(Main.MenuItems.MENU_DELETE).setOnAction(e -> {
            if (this.isSelected()) {
                changePage(Pages.DELETE);
                System.out.println("Moving to delete");
            }
        });

        mainPage.getMenuItem(Main.MenuItems.MENU_VIEW).setOnAction(e->{
            changePage(Pages.VIEW);
            System.out.println("Moving to view");
        });

        mainPage.getMenuItem(Main.MenuItems.MENU_STATS).setOnAction(e->{
            changePage(Pages.STATS);
            System.out.println("Moving to stats");
        });
    }

    public void setLockTab(boolean lock) {
        this.lockTab = lock;
        if (this.lockTab == true) {
            this.setClosable(false);
        }
    }

    public void changePage(Pages pageInd) {
        switch (pageInd) {
            case HOME:
                this.page = new Home();
                break;
            case CREATE:
                this.page = new Create();
                break;
            case DELETE:
                this.page = new Delete();
                break;
            case UPDATE:
                this.page = new Update();
                break;
            case VIEW:
                this.page = new View();
                break;
            case STATS:
                this.page = new Stats();
                break;
        }
        this.pageLayout = this.page.getPane();
        this.page.pageSetStyle();
        this.page.pageBehavior();
        this.setContent(pageLayout);
    }
}
