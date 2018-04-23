package controller;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import model.Model;

import java.awt.event.ActionEvent;

public class Controller {

    @FXML
    public TextFlow textFlow;

    @FXML
    public TextField inputField;

    public Text text;

    public Model model;
    private String inputText;
    public void initialize() {
        //setze konsolen-text style
            model = new Model();
            consoleTextProperties();
    }
    private void consoleTextProperties(){
        text = new Text();
        text.setFont(Font.font("Monospaced"));
        text.setTextAlignment(TextAlignment.LEFT);
        text.setStyle("-fx-font-size: 18; -fx-fill: darkblue;");
    }

    @FXML
    public void handleInputField(KeyEvent event){
        switch(event.getCode().toString()){
            case "ENTER":{
                //process typed code in console(somehow)
                inputText = this.inputField.getText();
                this.model.commandList.add(inputText);
                this.text.setText(inputText);
                this.textFlow.getChildren().add(this.text);
                break;
            }
            case "UP":{
                break;
            }
            case "DOWN":{
                break;
            }
            default: {
                //misstypes
            }
        }
    }
}
