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
    boolean make = false;
    Wait wait;
    String input_name;//이름을 저장

    public void runner(String s,String ip){
        try{
            String serverIp="172.17.223.61";
            Socket socket = new Socket(ip,9999);//소켓을 만들어줌
            System.out.println("서버에 연결되었습니다.");
            //GetUser getUser = new GetUser();
            input_name = s;
            Thread sender = new Thread(new ClientSender(socket,s));//내용을 보내는 함수선언
            Thread receiver = new Thread(new ClientReceiver(socket));//내용을 받는 함수 선언
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

                    if (s.contains("#")) {
                        String arr[] = s.split("#");
                        if(arr[0].equals("1")) {
                            if (screen != null)
                                screen.dispose();
                            screen = new Screen(arr[1] + "(나)", arr[2] + "(상대)", 2, 15);
                        }//내꺼가 있으면 종료
                        else{
                            if (screen != null)
                                screen.dispose();
                            screen = new Screen(arr[1] + "(상대)", arr[2] + "(나)", 2, 15);
                        }
                        make = true;//만들어진 것을 확인
                        if(wait!=null)
                            wait.dispose();
                    }
                    else if(s.contains("*")){
                        new Victory(input_name);//승리창 띄우고, 대기
                        wait = new Wait();//대기창을 띄움
                    }//상대방이 나간경우
                    try{
                        int size = Integer.parseInt(s);
                        if(size==1){
                            wait= new Wait();//사람이 들어올 때까지 잠시 기다려주세요.
                        }
                        else if(size>=3) {
                            new Loading();
                            break;
                        }
                    }catch (Exception e){}
                    if(make)
                        screen.board.recieve(s);
                    //상대방의 좌표값을 받는다.
                }catch(IOException e){}
            }//계속 대기
        }
    }
}
    class Loading extends JFrame {
        Loading() {
            setTitle("알림창");
            setLayout(null);
            setBounds(300, 100, 800, 300);
            getContentPane().setBackground(Color.white);

            JLabel username = new JLabel("방이 다 차서 잠시 후 입장해주세요....");
            username.setFont(new Font("맑은 고딕", Font.BOLD, 25));
            username.setBounds(40, 40, 600, 50);
            add(username);
            setVisible(true);
        }
    }
        class Wait extends JFrame{
            Wait(){
                setTitle("대기창");
                setLayout(null);
                setBounds(300,100,800,300);
                getContentPane().setBackground(Color.white);

                JLabel username = new JLabel("사람이 없어 잠시 기다려주세요....");
                username.setFont(new Font("맑은 고딕",Font.BOLD,25));
                username.setBounds(40,40,600,50);
                add(username);
                setVisible(true);
            }
}
