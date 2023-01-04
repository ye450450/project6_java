import jdk.nashorn.internal.runtime.ECMAException;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Scanner;

public class BookClient {
    public static void main(String args[]){
        if(args.length!=1){
            System.out.println("잘못입력되었습니다.");
            System.exit(0);
        }
        try{
            String serverIp="172.27.16.1";
            Socket socket = new Socket(serverIp,9999);
            System.out.println("서버에 연결되었습니다.");
            Thread sender = new Thread(new ClientSender(socket,args[0]));
            Thread receiver = new Thread(new ClientReceiver(socket));

            sender.start();
            receiver.start();
        }catch (ConnectException ce){
            ce.printStackTrace();
        }catch (Exception e){}
    }
    static class ClientSender extends Thread{
        Socket socket;
        DataOutputStream out;
        String name;
        ClientSender(Socket socket, String name) {
            this.socket = socket;
            try {
                out = new DataOutputStream(socket.getOutputStream());
                this.name = name;
            } catch (Exception e) {}
        }
        public void run(){
            Scanner scanner = new Scanner(System.in);
            try{
                if(out!=null){
                    out.writeUTF(name);
                }
                while(out!=null)
                    out.writeUTF("["+name+"]"+scanner.nextLine());
            }catch (IOException e){}
        }
    }
    static class ClientReceiver extends  Thread{
        Socket socket;
        DataInputStream in;
        ClientReceiver(Socket socket){
            this.socket = socket;
            try{
                in = new DataInputStream(socket.getInputStream());
            }catch (IOException e){}
        }
        public void run(){
            while(in!=null){
                try{//메세지를 받는 곳
                    if(in.readUTF().equals("1번")){
                        System.out.println("hello");
                    }//
                    if(in.readUTF().equals("2번")){
                        System.out.println("hello2");
                    }//
                    //System.out.println(in.readUTF());
                }catch(IOException e){}
            }
        }
    }
}
