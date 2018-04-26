package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
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
            System.err.println("SERVER STARTED");
            while(true){
                client = server.accept();
                new InnerThread(client  ).start();
                Thread.sleep(500);
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
class InnerThread extends Thread{
    private Socket client;
    private DataOutputStream dos =null;
    private DataInputStream dis = null;
    public InnerThread(Socket client){
        this.client=client;
    }
    @Override
    public void run() {

        try{
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
            System.out.println("SOCKET :: /"+client.getRemoteSocketAddress()+" has been connected!");

            String[] clientMsg = dis.readUTF().split(" ");
            switch(clientMsg[0]){
                case "client.sendFile":{
                    clientSendFile(clientMsg);
                    break;
                }
                case "client.receiveFile":{

                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void clientSendFile(String[] clientMsg) {
        FileOutputStream fos=null;
        try{
            fos= new FileOutputStream(clientMsg[2]);
            int incomingFileSize = dis.readInt();
            int tmp;
            byte[] buffer = new byte[incomingFileSize];
            while((tmp = dis.read(buffer)) != -1){
                fos.write(buffer,0,tmp);
                fos.flush();
            }
            if(fos!=null)
                fos.close();
            System.out.println("Receiving done.");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}