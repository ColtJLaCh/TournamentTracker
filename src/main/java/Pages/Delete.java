package Pages;

import Database.*;
import Nodes.TourTab;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    Connection conn = dbc.getConnection();

    //Parent Tab
    TourTab parentTab;

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals
    HBox hbox = new HBox();
    ChoiceBox tourBox = new ChoiceBox();
    VBox tablesVBox = new VBox(2);
    Label tableLabel = new Label("Tournaments managed by " + DB_USER);

    Button deleteButton = new Button("END TOURNAMENT");

    public Delete(TourTab parentTab) {
        this.parentTab = parentTab;

        //Initialize layout assets here, ImageViews, Panes, Text etc...
        Label deletePageLabel = new Label("DELETE TOURNAMENTS");



        //First column for the Tables Choice Box

        tablesVBox.setPadding(new Insets(10,0,0,32));
        tableLabel.setUnderline(true);
        tableLabel.setLabelFor(tablesVBox);


        tourBox.setMinWidth(200);
        tourBox.setMaxWidth(200);

        reconstructChoiceBox();

        //Second column for the delete button
        VBox deleteVBox = new VBox(2);
        Label deleteLabel = new Label("Delete selected tournament");
        deleteLabel.setUnderline(true);
        deleteLabel.setLabelFor(deleteVBox);
        deleteButton.setPrefSize(240,40);
        deleteButton.setMinSize(240,40);
        deleteButton.setMaxSize(240,40);
        deleteButton.setOnAction(e->{
            // Make sure the user has actually selected something
            if(tourBox.getSelectionModel().getSelectedItem()!=null) {
                //String representing user's selection
                String dropTour = tourBox.getSelectionModel().getSelectedItem().toString();
                try {
                    //Alert pops up asking the user if they're sure they want to delete.
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setContentText("Are you sure you want to delete tournament " + dropTour + "?");
                    a.showAndWait();
                    if(a.getResult() == ButtonType.YES) {
                        if (dbc.dropTable(tourBox.getSelectionModel().getSelectedItem().toString(), conn)) {
                            reconstructChoiceBox();
                            reconstructClassVBox(deletePageLabel, hbox);
                            System.out.println("Tournament " + dropTour + " successfully deleted.");
                            //For Colton - Navigate to View page here!
                        } else {
                            System.out.println("Tournament doesn't exist.");
                        }
                    }
                } catch (SQLException dropException){
                    System.out.println("Error dropping table.");
                }
            }
        });

        deleteVBox.getChildren().addAll(deleteLabel,deleteButton);
        deleteVBox.setPadding(new Insets(10,0,0,0));

        //Add both columns to the HBox
        hbox = new HBox(tablesVBox, deleteVBox);
        hbox.setSpacing(64);

        classVBox = new VBox(deletePageLabel,hbox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods
    public void reconstructChoiceBox() {
        tourBox.getItems().clear();
        tablesVBox.getChildren().clear();
        //Gather all table names from the user's database
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(DB_NAME, null, "%", null);
            //For each table, add its name to the choicebox
            while (rs.next()) {
                tourBox.getItems().add(rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving tables.");
        }
        tablesVBox.getChildren().addAll(tableLabel,tourBox);
    }

    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}
