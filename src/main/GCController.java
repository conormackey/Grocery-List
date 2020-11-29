package main;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import main.model.GroceryList;
import main.model.SampleData;

public class GCController implements Initializable {


    //This is the list of editable text components of the grocery list. Each list has its own listview. I made 5
    //lists and a main, so 6 total.
    // ***NOTE*** if you use scenebuilder to edit or add anything you will need to go
    //back through the FXML file and re-add the FX:ID to the tabs, labels, and listView because scenebuilder wipes them
    @FXML
    private Label tab1Name;
    @FXML
    private ListView<String> tab1List;
    @FXML
    private Tab tab1;

    @FXML
    private Label tab2Name;
    @FXML
    private ListView<String> tab2List;
    @FXML
    private Tab tab2;

    @FXML
    private Label tab3Name;
    @FXML
    private ListView<String> tab3List;
    @FXML
    private Tab tab3;

    @FXML
    private Label tab4Name;
    @FXML
    private ListView<String> tab4List;
    @FXML
    private Tab tab4;

    @FXML
    private Label tab5Name;
    @FXML
    private ListView<String> tab5List;
    @FXML
    private Tab tab5;

    @FXML
    private Label tabMainName;
    @FXML
    private ListView<String> tabMainList;
    @FXML
    private Tab tabMain;

//    private GroceryList selectedGL;
//    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
//    private ChangeListener<GroceryList> GCChangeListener;
//    private final ObservableList<GroceryList> glList = FXCollections.observableArrayList(GroceryList.extractor);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
        On startup an array of data is created using populate lists and a method from Sampledata. It is just a
        random assortment of grocery items from a premade list of possibilities. I did it this way so each run would
        be slightly different and to ensure the main was updating correctly.
         */

        //data has 5 grocery lists premade to put use as sample data for the lists
        ArrayList<GroceryList> data = populateLists();

        //Set the name, list and tabname. The tabname is just the first letter of the author's name. We can change that
        //its just what I put for the time being.
        tab1Name.setText(data.get(0).getAuthor());
        tab1List.setItems( data.get(0).getItems());
        tab1.setText(String.valueOf(data.get(0).getAuthor().charAt(0)));

        tab2Name.setText(data.get(1).getAuthor());
        tab2List.setItems( data.get(1).getItems());
        tab2.setText(String.valueOf(data.get(1).getAuthor().charAt(0)));

        tab3Name.setText(data.get(2).getAuthor());
        tab3List.setItems( data.get(2).getItems());
        tab3.setText(String.valueOf(data.get(2).getAuthor().charAt(0)));

        tab4Name.setText(data.get(3).getAuthor());
        tab4List.setItems( data.get(3).getItems());
        tab4.setText(String.valueOf(data.get(3).getAuthor().charAt(0)));

        tab5Name.setText(data.get(4).getAuthor());
        tab5List.setItems( data.get(4).getItems());
        tab5.setText(String.valueOf(data.get(4).getAuthor().charAt(0)));

        tabMainName.setText("Main");
        tabMain.setText("Main");

        //I made a method to update the Main list so it will be easy to update it when we make changes to lists
        updateMainList();

//        listView.setItems(glList); //set the listView in the scene as the list of items from sample data
//
//        listView.getSelectionModel().selectedItemProperty().addListener(
//                GCChangeListener = (observable, oldValue, newValue) -> {
//                    System.out.println("Last Selected item: " + newValue);
//                    System.out.println("Current Selected:");
//                    for(GroceryList gl : listView.getSelectionModel().getSelectedItems()){
//                        System.out.println(gl.toString());
//                    }
//                    selectedGL = newValue;
//                    modifiedProperty.set(false);
//                    if (newValue != null) {
//                        // Populate controls with selected list
//                        authorTextField.setText(selectedGL.getAuthor());
//                        itemsView.setItems(selectedGL.getItems());
//                    } else {
//                        titleTextField.setText("");
//                        authorTextField.setText("");
//                    }
//
//                });
//
//        // Pre-select the first item
//        listView.getSelectionModel().selectFirst();

    }

    //Create the array of premade data to use for examples. The names are just placeholders
    //See SampleData to see how the lists are made.
    private ArrayList<GroceryList> populateLists() {
       ArrayList<GroceryList> rtnList = new ArrayList<GroceryList>();
       String[] names = {"Alpha", "Bravo", "Charlie", "Delta", "Echo"};

        for(int i=0;i<5;i++) {
            rtnList.add(SampleData.fillSampleData(names[i]));
        }
        return rtnList;
    }

    //updateMainList uses a method called combinelists to check all lists and copy unique entries into the main. The
    //main list will have duplicates in the fringe case that the first list contains duplicates, but that can be fixed
    //in the future.
    private void updateMainList() {

        //I had to make the starting ObservableList this way because otherwise it would change other lists along the
        //way.
        ObservableList<String> currentList = FXCollections.observableArrayList();
        for(int i=0;i<tab1List.getItems().size();i++) {
            currentList.add(tab1List.getItems().get(i));
        }

        //These calls just add in unique data to the running list.
        combineLists(currentList,tab2List.getItems());
        combineLists(currentList,tab3List.getItems());
        combineLists(currentList,tab4List.getItems());
        combineLists(currentList,tab5List.getItems());

        tabMainList.setItems(currentList);
    }

    //combineLists takes in two lists and modifies the first one by adding the elements from the second that are not
    //already contained within it. It uses a nested for loop to check each new entry by all entries already within the
    //list.
    private void combineLists(ObservableList<String> currentList, ObservableList<String> compareList) {
        for(int i=0;i<compareList.size();i++) {

            boolean repeatItem = false;
            for(int j=0;j<currentList.size();j++) {

                if(compareList.get(i).equalsIgnoreCase(currentList.get(j))) {
                    repeatItem = true;
                }
            }
            if(repeatItem==false) {
                currentList.add(compareList.get(i));
                System.out.println(compareList.get(i));
            }
        }

    }

}
