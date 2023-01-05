import javax.swing.*;
import java.awt.*;

public class Victory2 extends JFrame {
    public Victory2(){
        setTitle("결과창");
        setLayout(null);
        getContentPane().setBackground(new Color(245, 255, 128));
        setBounds(500,100,730,850);

        JLabel content= new JLabel("승리하셨습니다.");
        JLabel congratution= new JLabel("축하드립니다.");
        content.setFont(new Font("맑은 고딕", Font.BOLD,50));
        congratution.setFont(new Font("맑은 고딕", Font.BOLD,60));
        content.setForeground(Color.BLACK);
        congratution.setForeground(Color.BLACK);
        content.setBounds(170,170,1000,200);
        congratution.setBounds(160,250,500,200);
        add(content);
        add(congratution);

        ImageIcon image = new ImageIcon(Victory2.class.getResource("축하.PNG"));
        Image change_img= image.getImage();
        change_img = change_img.getScaledInstance(400,100,Image.SCALE_DEFAULT);//이미지 사이즈 조절
        image = new ImageIcon(change_img);
        JLabel img = new JLabel(image);
        add(img);
        img.setBounds(160,20,400,100);
        ImageIcon image2 = new ImageIcon(Victory2.class.getResource("축하2.PNG"));
        Image change_img2= image2.getImage();
        change_img2 = change_img2.getScaledInstance(300,300,Image.SCALE_DEFAULT);//이미지 사이즈 조절
        image2 = new ImageIcon(change_img2);
        JLabel img2 = new JLabel(image2);
        add(img2);
        img2.setBounds(200,420,300,300);

        setVisible(true);

    }

}
