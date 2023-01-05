import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class BookServer {
    HashMap clients;
    BookServer(){
        clients = new HashMap();
        Collections.synchronizedMap(clients);//들어가는 순서대로 정리
    }
    public void start() {
        ServerSocket serverSocket = null;//서버소켓은 여기서만 만듬
        Socket socket = null; //소켓을 가져옴(클라이언트의)
        try {
            serverSocket = new ServerSocket(9999);//서버소켓만듬
            System.out.println("서버가 시작되었습니다.");

            while (true) {
                socket = serverSocket.accept(); //클라이언트 하나씩 가져옴
                System.out.println("[" + socket.getInetAddress() + ":" + socket.getPort() + "]" + "에서 접속하였습니다.");
                ServerReceiver thread = new ServerReceiver(socket);//내용을 가져오기 위해
                thread.start();//쓰레드를 실행()
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        void sendToAll(String msg){
            Iterator it = clients.keySet().iterator();//key의 배열의 주소값을 저장
            while(it.hasNext()){
                try{
                    DataOutputStream out = (DataOutputStream) clients.get(it.next());//key갑의 value를 바뀌줌
                    out.writeUTF(msg);//msg의 내용을 써준다.
                }catch (IOException e){}
            }//보낸다
        }
        public static void main(String args[]){
            new BookServer().start();
        }

        void sendToPerson(String name, String msg){
            try{
                DataOutputStream out = (DataOutputStream) clients.get(name);
                out.writeUTF(msg);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        class ServerReceiver extends Thread {
            Socket socket;
            DataInputStream in;
            DataOutputStream out;

            ServerReceiver(Socket socket) {
                this.socket = socket;
                try {
                    in = new DataInputStream(socket.getInputStream());//클라이언트만다 inputstream만듬
                    out = new DataOutputStream(socket.getOutputStream());//클라이언트마다 outputstream만듬
                } catch (IOException e) {
                }
            }

    public void run(){
        String name ="";
        try {
            name = in.readUTF();//지금 써져있는 것은 받아서 name에 저장
            sendToAll("#" + name + "님이 들어오셨습니다.");

            clients.put(name, out);//hash에 넣어줌 (들어온 사람)
//            if(clients.size()==2){
//                Iterator it = clients.keySet().iterator();//key의 배열의 주소값을 저장
//                while(it.hasNext()){
//                    String next = (String) it.next();//이름을 가져옴
//                    System.out.println("이름 : "+ next);
//                    if(!next.equals(name))
//                        sendToPerson(next,"2번");
//                    else
//                        sendToPerson(next,"1번");
//                }
//            }
            System.out.println("현재 서버접속자 수는 " + clients.size() + "입니다.");
            while (in!=null){
                sendToAll(in.readUTF());
            }
        }catch(IOException e){
            }finally{
                sendToAll("#"+name+"님이 나가셨습니다.");
                clients.remove(name);//나간 것을 암
                System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속을 종료하였습니다.");
                System.out.println("현재 서버접속자 수는"+clients.size()+"입니다.");
            }
        }
    }
}

