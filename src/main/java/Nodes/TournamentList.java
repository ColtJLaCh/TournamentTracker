package Nodes;

import Pages.Page;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TournamentList extends VBox {
    private ArrayList<Node> arrList = new ArrayList<Node>();
    private Label label;
    private VBox vBox;
    private Button addButton = new Button("+ ADD NEW");
    private double cellPrefWidth;

    private Boolean listOfList = false;
    private Button listOfListDeleteButton = new Button("DELETE");

    public TournamentList(String labelStr,double spacing, double cellPrefWidth, boolean listOfList) {
        this.label = new Label(labelStr);
        label.setUnderline(true);
        label.setAlignment(Pos.TOP_LEFT);
        label.setLabelFor(vBox);
        vBox = this;
        vBox.setSpacing(spacing);
        if (labelStr != "") vBox.getChildren().add(label);
        this.cellPrefWidth = cellPrefWidth;
        this.listOfList = listOfList;
    }

    public void reconstructVBox() {
        if (!listOfList) {
            vBox.getChildren().clear();
            if (label.getText() != "") vBox.getChildren().add(label);
            for (var i = 0; i < arrList.size(); i++) {
                HBox curHBox = (HBox) arrList.get(i);
                DeleteButton curDelButton = (DeleteButton) curHBox.getChildren().get(1);
                curDelButton.index = i;
                curHBox.getChildren().remove(1);
                curHBox.getChildren().add(curDelButton);
                arrList.set(i, curHBox);
                vBox.getChildren().add(arrList.get(i));
            }
            vBox.getChildren().add(addButton);
        }else{
            vBox.getChildren().clear();
            vBox.getChildren().add(label);
            for (var i = 0; i < arrList.size(); i++) {
                VBox curVBox = (VBox) arrList.get(i);
                HBox curHBox = (HBox) curVBox.getChildren().get(0);
                DeleteButton curDelButton = (DeleteButton) curHBox.getChildren().get(1);
                curDelButton.index = i;
                curHBox.getChildren().remove(1);
                curHBox.getChildren().add(curDelButton);
                curVBox.getChildren().set(0,curHBox);
                arrList.set(i,curVBox);
                vBox.getChildren().add(arrList.get(i));
            }
            vBox.getChildren().add(addButton);
        }
    }

    public void deleteFromList(int index) {
        arrList.remove(index);
        reconstructVBox();
    }

    //Methods to add to pageBehavior
    public void addToList(String initialString, boolean cellDisabled, boolean forceAdd) {
        if (!listOfList) {
            if (!forceAdd) {
                addButton.setOnMouseClicked(e -> {
                    HBox hBox = new HBox();
                    TextField newVal = new TextField(initialString);
                    newVal.setPrefWidth(cellPrefWidth);
                    newVal.setDisable(cellDisabled);
                    DeleteButton deleteAt = new DeleteButton(arrList.size(), "X");
                    deleteAt.setOnMouseClicked(de -> {
                        deleteFromList(deleteAt.index);
                    });
                    deleteAt.setDisable(cellDisabled);
                    hBox.getChildren().addAll(newVal, deleteAt);

                    arrList.add(hBox);

                    reconstructVBox();
                });
            } else {
                HBox hBox = new HBox();
                TextField newVal = new TextField(initialString);
                newVal.setPrefWidth(cellPrefWidth);
                newVal.setDisable(cellDisabled);
                DeleteButton deleteAt = new DeleteButton(arrList.size(), "X");
                deleteAt.setOnMouseClicked(de -> {
                    deleteFromList(deleteAt.index);
                });
                deleteAt.setDisable(cellDisabled);
                hBox.getChildren().addAll(newVal, deleteAt);
                arrList.add(hBox);

                reconstructVBox();
            }
        }else{
            if (!forceAdd) {
                addButton.setOnMouseClicked(e -> {
                    VBox vBox = new VBox();
                    HBox hBox = new HBox();
                    TextField newVal = new TextField(initialString);
                    newVal.setPrefWidth(cellPrefWidth);
                    newVal.setDisable(cellDisabled);
                    DeleteButton deleteAt = new DeleteButton(arrList.size(), "X");
                    deleteAt.setOnMouseClicked(de -> {
                        deleteFromList(deleteAt.index);
                    });
                    deleteAt.setDisable(cellDisabled);
                    hBox.getChildren().addAll(newVal, deleteAt);

                    TournamentList newTourList = new TournamentList("", 2, cellPrefWidth, false);
                    newTourList.addToList("NEW PLAYER", false, true);

                    vBox.getChildren().addAll(hBox, newTourList);
                    arrList.add(vBox);

                    reconstructVBox();
                });
            } else {
                VBox vBox = new VBox();
                HBox hBox = new HBox();
                TextField newVal = new TextField(initialString);
                newVal.setPrefWidth(cellPrefWidth);
                newVal.setDisable(cellDisabled);
                DeleteButton deleteAt = new DeleteButton(arrList.size(), "X");
                deleteAt.setOnMouseClicked(de -> {
                    deleteFromList(deleteAt.index);
                });
                deleteAt.setDisable(cellDisabled);
                hBox.getChildren().addAll(newVal, deleteAt);

                TournamentList newTourList = new TournamentList("", 0, cellPrefWidth, false);
                newTourList.addToList("NEW PLAYER", false, true);

                vBox.getChildren().addAll(hBox, newTourList);
                arrList.add(vBox);

                reconstructVBox();
            }
        }
    }

    public ArrayList<Node> getArrList() {
        return arrList;
    }


    private class DeleteButton extends Button {
        public int index = 0;
        public Button button = this;

        public DeleteButton(int index, String string) {
            this.index = index;
            this.setText(string);
            button = this;
        }
    }
}
