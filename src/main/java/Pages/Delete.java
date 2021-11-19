package Pages;

import Database.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.sql.*;

import static Database.DBConst.DB_NAME;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class Delete extends Page {

    //Initialze the database as global for use
    Database dbc = Database.getInstance();

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals
    ScrollPane classScrollPane = new ScrollPane();

    public Delete() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        ChoiceBox tourBox = new ChoiceBox();
        Connection conn = dbc.getConnection();
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(DB_NAME, null, "%", null);
            while (rs.next()) {
                tourBox.getItems().add(rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving tables.");
        }


        HBox hbox = new HBox(tourBox);

        classVBox = new VBox(hbox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods


    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}
