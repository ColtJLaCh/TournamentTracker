package Nodes;

import Main.Main;
import javafx.application.Application;
import javafx.scene.control.Tab;

public class TourTab extends Tab {
    private static TourTab tab;
    private Main mainPage;

    private TourTab(Main mainPage) {
        this.mainPage = mainPage;
        this.setText("NEW TOUR");
        this.setOnClosed(e -> {
            if (this.isSelected()) {
                mainPage.changePage(Main.Pages.HOME);
            }
        });
    }
}
