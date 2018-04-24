package controller;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
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
    public TextArea consoleArea;

    @FXML
    public TextField inputField;

    public Text text;

    public Model model;
    private String inputText;
    private int upAndDownIndex = 0;
    public void initialize() {
        //setze konsolen-text style
            model = new Model(this);
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
                this.consoleArea.appendText(this.text.getText()+"\n");
                upAndDownIndex = this.model.commandList.size()-1;
                this.inputField.clear();
                model.processCommand(inputText.split(" "));
                break;
            }
            case "UP":{
                if(!this.model.commandList.isEmpty()) {
                    this.inputField.setText(this.model.commandList.get(this.upAndDownIndex).toString());
                    if (this.upAndDownIndex != 0)
                        this.upAndDownIndex -= 1;
                }
                break;
            }
            case "DOWN":{
                if(!this.model.commandList.isEmpty()) {
                    this.inputField.setText(this.model.commandList.get(this.upAndDownIndex).toString());
                    if (this.upAndDownIndex != this.model.commandList.size() - 1)
                        this.upAndDownIndex += 1;
                }
                break;
            }
            default: {
                //misstypes
            }
        }
    }
}
