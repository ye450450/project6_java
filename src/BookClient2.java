import jdk.nashorn.internal.runtime.ECMAException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Scanner;

public class BookClient2 {
    Screen screen;

    public void runner(String s,String ip){
        try{
            String serverIp="172.17.223.61";
            Socket socket = new Socket(ip,9999);//소켓을 만들어줌
            System.out.println("서버에 연결되었습니다.");
            //GetUser getUser = new GetUser();
            Thread sender = new Thread(new ClientSender(socket,s));//내용을 보내는 함수선언
            Thread receiver = new Thread(new ClientReceiver(socket));//내용을 받는 함수 선언
            screen = new Screen();
            sender.start();
            receiver.start();
        }catch (ConnectException ce){
            ce.printStackTrace();
        }catch (Exception e){}

    }

    static class ClientSender extends Thread{
        Socket socket;
        String name;
        static DataOutputStream out;
        ClientSender(Socket socket, String name) {
            this.socket = socket;//소켓에 넣어줌
            try {
                out = new DataOutputStream(socket.getOutputStream());
                this.name = name;
            } catch (Exception e) {}
        }
        public void run(){
            //Scanner scanner = new Scanner(System.in);
            //위치값 보내기
            try{
                if(out!=null){
                    out.writeUTF(name);
                }
                while(out!=null) {
//                    if(Board.point_check==1) {
//                        System.out.println("-----------");
//                        out.writeUTF(String.valueOf(Board.position));
//                        Board.point_check= 0;
//                    }
                    //boolean으로 점이 들어옴을 확인 좌표값을 write함
                    //out.writeUTF("[" + name + "]" + scanner.nextLine());
                    //보드에서 boolean을 만들어 클릭할 떄 바뀜
                }
            }catch (IOException e){}
        }
    }
    class ClientReceiver extends Thread{
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
                try{
                    String s = in.readUTF();
                    screen.board.recieve(s);
                    //상대방의 좌표값을 받는다.
                }catch(IOException e){}
            }//계속 대기
        }
    }
}
//    class Login extends JFrame{
//    Login(){
//        setTitle("로그인하기");
//        setLayout(null);
//        setBounds(300,100,600,600);
//        getContentPane().setBackground(Color.white);
//
//        JLabel username = new JLabel("사용자 이름설정");
//        username.setFont(new Font("맑은 고딕",Font.BOLD,25));
//        username.setBounds(40,40,250,50);
//        add(username);
//        setVisible(true);
//    }
//}
