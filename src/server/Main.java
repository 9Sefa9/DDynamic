package server;

import java.io.*;
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
            dis = new DataInputStream(client.getInputStream());
            System.out.println("SOCKET :: /"+client.getRemoteSocketAddress()+" has been connected!");

            String[] clientMsg = dis.readUTF().split(" ");
            switch(clientMsg[0]){
                case "client.sendFile":{
                    clientSendFile(clientMsg);
                    break;
                }
                case "client.receiveFile":{
                    clientReceiveFile(clientMsg[2]);
                    break;
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (dis != null)
                    dis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void clientReceiveFile(String filePath) {
        FileInputStream fis=null;

        try{
            dos = new DataOutputStream(client.getOutputStream());
            fis= new FileInputStream(filePath);

            long fileSize =fis.getChannel().size();
            dos.writeLong(fileSize);
            dos.flush();

            int tmp;
            byte[] buffer = new byte[(int)fileSize + 8192];
            while((tmp = fis.read(buffer)) !=-1){
                dos.write(buffer,0,tmp);
            }
            System.out.println("Sending done.");

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{

                if(dos!=null)
                    dos.close();

                if(fis!=null)
                    fis.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void clientSendFile(String[] clientMsg) {
        FileOutputStream fos=null;
        DataOutputStream dos2 = null;
        try{
            dos = new DataOutputStream(client.getOutputStream());
            fos= new FileOutputStream(clientMsg[2]);
            dos2 = new DataOutputStream(fos);
            long incomingFileSize = dis.readLong();
            int tmp;
            byte[] buffer = new byte[(int)incomingFileSize + 8192];
            while((tmp = dis.read(buffer)) !=-1){
                dos2.write(buffer,0,tmp);
                dos2.flush();
                System.out.println(tmp);
            }
            System.out.println("Receiving done.");

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{

                if(dos2!=null)
                    dos2.close();

                if(fos!=null)
                    fos.close();

                if(dos!=null)
                    dos.close();

                if(dos2!=null)
                    dos2.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}