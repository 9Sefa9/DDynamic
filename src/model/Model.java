package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model{

    public ObservableList commandList;
    public Model(){
        commandList = FXCollections.observableArrayList();
    }
}
