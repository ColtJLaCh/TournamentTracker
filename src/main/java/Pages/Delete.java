package Pages;

import Database.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.sql.*;

import static Database.DBConst.DB_NAME;
import static Database.DBConst.DB_USER;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class Delete extends Page {

    //Initialze the database as global for use
    Database dbc = Database.getInstance();

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals
    ScrollPane classScrollPane = new ScrollPane();

    Button deleteButton = new Button("END TOURNAMENT");

    public Delete() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        VBox tablesVBox = new VBox(2);
        Label tableLabel = new Label("Tournaments managed by " + DB_USER);
        tableLabel.setLabelFor(tablesVBox);
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
        tablesVBox.getChildren().addAll(tableLabel,tourBox);

        VBox deleteVBox = new VBox(2);
        Label deleteLabel = new Label("Delete selected tournament");
        deleteLabel.setUnderline(true);
        deleteLabel.setLabelFor(deleteVBox);
        deleteButton.setPrefSize(240,40);
        deleteButton.setMinSize(240,40);
        deleteButton.setMaxSize(240,40);
        deleteButton.setOnAction(e->{
            if(tourBox.getSelectionModel().getSelectedItem()!=null) {
                System.out.println("Deleting " + tourBox.getSelectionModel().getSelectedItem().toString());
                try {
                    if(dbc.dropTable(tourBox.getSelectionModel().getSelectedItem().toString(), conn)){
                        System.out.println("Tournament " + tourBox.getSelectionModel().getSelectedItem().toString() + " successfully deleted.");
                    } else {
                        System.out.println("Tournament doesn't exist.");
                    }
                } catch (SQLException dropException){
                    System.out.println("Error dropping table.");
                }
            }
        });

        deleteVBox.getChildren().addAll(deleteLabel,deleteButton);


        HBox hbox = new HBox(tablesVBox, deleteVBox);

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
