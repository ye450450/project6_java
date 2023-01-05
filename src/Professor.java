import javax.swing.*;
import java.awt.*;

public class Professor extends JFrame {
    Professor(){
        setTitle("교수님");
        setLayout(null);
        getContentPane().setBackground(new Color(255,255,255));
        setBounds(500,100,730,850);
        setVisible(true);

        ImageIcon image = new ImageIcon(Professor.class.getResource("교수님.PNG"));
        Image change_img= image.getImage();
        change_img = change_img.getScaledInstance(500,300,Image.SCALE_DEFAULT);//이미지 사이즈 조절
        image = new ImageIcon(change_img);
        JLabel img = new JLabel(image);
        add(img);
        img.setBounds(130,50,500,300);
        ImageIcon image2 = new ImageIcon(Professor.class.getResource("눈.jpg"));
        Image change_img2= image2.getImage();
        change_img2 = change_img2.getScaledInstance(300,300,Image.SCALE_DEFAULT);//이미지 사이즈 조절
        image2 = new ImageIcon(change_img2);
        JLabel img2 = new JLabel(image2);
        add(img2);
        img2.setBounds(200,420,300,300);
    }
    public static void main(String [] args){
        new Professor();
            }
}
