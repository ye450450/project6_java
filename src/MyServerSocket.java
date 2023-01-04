import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MyServerSocket extends Thread{
    private static ArrayList<Socket> clients = new ArrayList<Socket>(5);//5명을 상대할 수 있게 만든
    private Socket sock;

    public MyServerSocket(Socket sock){
        this.sock= sock;
    }

    public void run(){
        InputStream fromClient = null;
        OutputStream toClient = null;

        try{
            System.out.println(sock + "연결됨");
            fromClient = sock.getInputStream();

            byte[] buf = new byte[1024];//글을 읽기 위해
            int count;
            while((count = fromClient.read(buf))!=-1){
                for(Socket s : MyServerSocket.clients){
                    if(sock!= s){
                        toClient = s.getOutputStream();
                        toClient.write(buf,0,count);
                        toClient.flush();//나머지 버퍼를 없애준다.
                    }//자기 자신한테는 보내지 않는다.
                }
                System.out.write(buf,0,count);
            }//계속 읽을 때까지 대기한다. -1은 종료하는 경우이다.
        } catch (IOException e) {
            System.out.println(sock +":에러("+e+")");
        }
        finally {
            try{
                if(sock!=null){
                    sock.close();
                    remove(sock);
                }//서버를 나간 client를 제거해준다.
                fromClient = null;
                toClient = null;
            }catch (IOException e){

            }
        }
    }//쓰레드에서 하는 일이 여기에 있다.

    public void remove(Socket sock){
        for(Socket s: MyServerSocket.clients){
            if(sock==s){
                MyServerSocket.clients.remove(sock);
                break;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        java.net.ServerSocket serverSocket = new java.net.ServerSocket(9999);//포트번호9999로 서버 소켓을 만듬

        while(true){
            Socket client = serverSocket.accept();//accpet는 소켓타입을 반환한다.
            clients.add(client);//arrylist에 접속한 socket을 넣어줌

            MyServerSocket myserver = new MyServerSocket(client);
            myserver.start();//쓰레드가 시작됨

        }//네트워크 서버는 항상 떠있어야한다.

    }
}
