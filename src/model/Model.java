package model;

import controller.Controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.io.*;
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
            case "client.createConnection":{
                clientCreateConnection(commandArguments[1],commandArguments[2]);
                break;
            }
            case "client.closeConnection":{
                clientCloseConnection();
                break;
            }
            case "server.sendFile":{
                serverSendFile(commandArguments[1],commandArguments[2]);
                break;
            }
            case "clear":{
                clearConsole();
                break;
            }
            default:{
                this.controller.consoleArea.appendText("Command not found!\n");
            }

        }
    }

    private void serverSendFile(String clientFilePath, String serverFilePath) {
        if(!socket.isClosed()){
            try{
                int bufferSize = (int) new FileInputStream(clientFilePath).getChannel().size();
                byte[] buffer = new byte[bufferSize];
                whi
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void clearConsole() {
        this.controller.consoleArea.clear();
    }

    private void clientCreateConnection(String ip, String port) {
        try{
            socket = new Socket(ip,Integer.parseInt(port));
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            Platform.runLater(()->{this.controller.consoleArea.appendText("Connection Successful!\n");});


        }catch(IOException e ){
            Platform.runLater(()->{this.controller.consoleArea.appendText("No Connection found!\n");});
        }
    }
    private void clientCloseConnection() {
        try{
            this.socket.close();
            Platform.runLater(()->{this.controller.consoleArea.appendText("Connection closed!\n");});
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
