import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String args[]){
        Socket socket = null;
        try{
            socket = new Socket("172.27.16.1",9999);
            System.out.println(socket + ": 연결됨");
            new GetUser();
            OutputStream toServer = socket.getOutputStream();//글을 쓰기위한 outputStream

            //글을 받기위한 thread
            ServerHandler handler = new ServerHandler(socket);
            handler.start();

            byte[] buf = new byte[1024];
            int count;

            while((count = System.in.read(buf))!=-1){
                toServer.write(buf,0,count);
                toServer.flush();
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
class ServerHandler extends Thread{
    Socket socket = null;
    public ServerHandler(Socket sock){
        this.socket=sock;
    }
    public void run(){
        InputStream fromServer = null;
        try{
            fromServer = socket.getInputStream();
            //BufferedReader br = new BufferedReader(new InputStreamReader(fromServer));
            byte[] buf = new byte[1024];
            int count;
            while((count = fromServer.read(buf))!=-1){
                System.out.write(buf,0,count);
            }
        } catch (IOException e) {
            System.out.println("연결종료( "+e+")");
        }
        finally {
            try{
                if(fromServer!=null)
                    fromServer.close();
                if(socket!=null){
                    socket.close();
                }//서버를 나간 client를 제거해준다.
            }catch (IOException e){

            }
        }
    }
}
