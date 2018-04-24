package model;

import controller.Controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Model{

    public ObservableList commandList;
    public Controller controller;
    public Socket socket;
    private DataOutputStream dos=null;
    private DataInputStream dis = null;
    public Model(Controller controller){
        this.controller = controller;
        commandList = FXCollections.observableArrayList();
    }

    public void processCommand(String[] commandArguments) {
        switch(commandArguments[0]){
            case "createConnection":{
                createConnection(commandArguments[1],commandArguments[2]);
                break;
            }
            case "closeConnection":{
                closeConnection();
                break;
            }
            default: this.controller.consoleArea.appendText("ERROR!\n");

        }
    }
    private void createConnection(String ip, String port) {
        try{
            socket = new Socket(ip,Integer.parseInt(port));
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            Platform.runLater(()->{this.controller.consoleArea.appendText("Connection Successful!\n");});


        }catch(IOException e ){
            Platform.runLater(()->{this.controller.consoleArea.appendText("No Connection found!\n");});
        }
    }
    private void closeConnection() {
        try{
            this.socket.close();
            Platform.runLater(()->{this.controller.consoleArea.appendText("Connection closed!\n");});
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
