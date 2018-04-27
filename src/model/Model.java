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
            case "client.sendFile":{
                //ObjectOutputStream zu aufwendig!
                serverSendFile(commandArguments[0],commandArguments[1],commandArguments[2]);
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

    private void serverSendFile(String command, String clientFilePath, String serverFilePath) {

        if(!socket.isClosed()) {
            FileInputStream fis = null;
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                fis = new FileInputStream(clientFilePath);
                long bufferSize = fis.getChannel().size();
                dos.writeUTF(command + " " + clientFilePath + " " + serverFilePath);
                dos.flush();

                dos.writeLong(bufferSize);
                dos.flush();


                byte[] buffer = new byte[(int)bufferSize + 8192];
                int tmp;
                while ((tmp = fis.read(buffer))!=-1) {
                    dos.write(buffer, 0, tmp);
                    //dos.flush();
                }

                System.out.println("Sending done.");
                Platform.runLater(()->{this.controller.consoleArea.appendText("Sending done."+"\n");});
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try{
                    if(fis!=null)
                        fis.close();
                    if(dos!=null)
                        dos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            this.controller.consoleArea.appendText("Connection problems!\n");
        }
    }

    private void clearConsole() {
        this.controller.consoleArea.clear();
    }
    private void clientCreateConnection(String ip, String port) {
        try{
            socket = new Socket(ip+"",Integer.parseInt(port));
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
