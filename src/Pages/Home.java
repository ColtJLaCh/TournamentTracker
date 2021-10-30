package Pages;

import HelpfulClasses.UsefulConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Home {
    private BorderPane classPane = new BorderPane();

    public Home() {

        ImageView titleHomePage = new ImageView(new Image("/images/hometitletext.png"));

        VBox classVBox = new VBox(titleHomePage);
        classVBox.setAlignment(Pos.TOP_CENTER);
        classVBox.setPadding(new Insets(10,10,10,10));
        classPane.setTop(classVBox);

    }

    public BorderPane getPane() {
        return this.classPane;
    }
}
