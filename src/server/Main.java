package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main{
    private Socket client=null;
    private ServerSocket server=null;
    public static void main(String[] args){
        new Main();
    }
    public Main(){
        try{
            server= new ServerSocket(3121);
            while(true){
                client = server.accept();
                new InnerThread(client).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
class InnerThread extends Thread{
    private Socket client;
    public InnerThread(Socket client){
        this.client=client;
    }
    @Override
    public void run() {
        DataOutputStream dos =null;
        DataInputStream dis = null;
        try{
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());

            System.out.println("SOCKET :: /"+client.getRemoteSocketAddress()+" has been connected!");

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}