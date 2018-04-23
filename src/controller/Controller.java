package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.awt.event.ActionEvent;

public class Controller {

    @FXML
    public TextFlow textFlow;

    @FXML
    public TextField inputField;

    public Text text;
    public void initialize() {
        //setze konsolen-text style
            consoleTextProperties();
    }
    private void consoleTextProperties(){
        text = new Text("222");
        text.setFont(Font.font("Monospaced"));
        text.setTextAlignment(TextAlignment.LEFT);
        text.setStyle("-fx-font-size: 18; -fx-fill: darkblue;");
    }

    @FXML
    public void handleInputField(KeyEvent event){
        switch(event.getCode().toString()){
            case "ENTER":{

                break;
            }
        }
    }
}
