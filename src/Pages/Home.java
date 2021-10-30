package Pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class Home {
    private BorderPane classPane = new BorderPane();

    public Home() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...
        ImageView titleHomePage = new ImageView(new Image("/images/hometitletext.png"));
        ImageView tourImgHomePage1 = new ImageView(new Image("/images/homepagetourimg.png"));
        ImageView tourImgHomePage2 = new ImageView(new Image("/images/homepagetourimg.png"));

        HBox titleHBox = new HBox(tourImgHomePage1,titleHomePage,tourImgHomePage2);
        titleHBox.setAlignment(Pos.TOP_CENTER);

        Text createdBy = new Text("Created by Noah Burrows and Colton LaChance!");
        Font franklinGothicMedium = Font.font("Franklin Gothic Medium", 24);
        createdBy.setFont(franklinGothicMedium);

        VBox classVBox = new VBox(titleHBox,createdBy); //Vbox needed for Top to Bottom layout, add assets here
        classVBox.setAlignment(Pos.TOP_CENTER); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(100,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(180); //Set spacing here;

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    public BorderPane getPane() {
        return this.classPane;
    }
}
