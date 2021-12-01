package Pages;

import Database.*;
import Nodes.TourTab;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.sql.*;

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
    ChoiceBox tourBox = new ChoiceBox();
    VBox tablesVBox = new VBox(2);

    Button deleteButton = new Button("END TOURNAMENT");

    public Delete(TourTab parentTab) {
        this.parentTab = parentTab;

        //Initialize layout assets here, ImageViews, Panes, Text etc...
        Label deletePageLabel = new Label("DELETE TOURNAMENTS");



        //First column for the Tables Choice Box

        tablesVBox.setPadding(new Insets(10,0,0,32));

        tourBox.setMinWidth(200);
        tourBox.setMaxWidth(200);


        //Second column for the delete button
        VBox deleteVBox = new VBox(2);
        Label deleteLabel = new Label("DELETE TOURNAMENT " + parentTab.getText());
        deleteLabel.setFont(Font.font("Monospace",12));
        deleteLabel.setTextFill(new Color(1,0,0,1));
        deleteLabel.setLabelFor(deleteVBox);
        deleteButton.setPrefSize(240,40);
        deleteButton.setMinSize(240,40);
        deleteButton.setMaxSize(240,40);
        deleteButton.setOnAction(e->{
            //String representing user's selection
            String dropTour = parentTab.getText();
            try {
                //Force delete with no alert
                /*dbc.dropTable(parentTab.getText(), conn);
                reconstructClassVBox(deletePageLabel,deleteVBox);
                parentTab.forceClose();*/

                //Alert pops up asking the user if they're sure they want to delete.
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Are you sure you want to delete tournament " + dropTour + "?");
                a.showAndWait();
                if(a.getResult() == ButtonType.OK) {
                    if (dbc.dropTable(parentTab.getText(), conn)) {
                        reconstructClassVBox(deletePageLabel,deleteVBox);
                        System.out.println("Tournament " + dropTour + " successfully deleted.");
                        parentTab.forceClose();
                    } else {
                        System.out.println("Tournament doesn't exist.");
                    }
                }

            } catch (SQLException dropException){
                System.out.println("Error dropping table.");
            }

        });

        deleteVBox.getChildren().addAll(deleteLabel,deleteButton);
        deleteVBox.setPadding(new Insets(100,0,0,0));
        deleteVBox.setAlignment(Pos.CENTER);

        //Add both columns to the HBox

        classVBox = new VBox(deletePageLabel,deleteVBox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}
