import java.util.Scanner;

public class MainClient {
    public static void main(String []args){
        BookClient2 client2 = new BookClient2();
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        client2.runner(s);
    }
}
