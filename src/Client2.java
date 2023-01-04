import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client2 {
    public static void main(String args[]){
        Socket socket = null;
        try{
            socket = new Socket("172.27.16.1",9999);
            System.out.println(socket + ": 연결됨");
            GetUser getUser= new GetUser();
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
