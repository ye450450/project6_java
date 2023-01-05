import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class MainClient extends JFrame implements ActionListener {
    JTextField id_text;
    JTextField pass_text;
    public MainClient(){
        setTitle("로그인하기");
        setLayout(null);
        setBounds(300,100,600,400);
        getContentPane().setBackground(Color.white);

        JLabel username = new JLabel("사용자 이름설정");
        username.setFont(new Font("맑은 고딕",Font.BOLD,25));
        username.setBounds(40,40,250,50);
        add(username);
        JLabel id = new JLabel("닉네임 :");
        id.setFont(new Font("맑은 고딕",Font.BOLD,20));
        id.setBounds(40,100,120,50);
        add(id);
        id_text = new JTextField();
        id_text.setFont(new Font("맑은 고딕",Font.BOLD,20));
        id_text.setBounds(150,100,230,50);
        add(id_text);
        JLabel pass = new JLabel("ip주소 :");
        pass.setFont(new Font("맑은 고딕",Font.BOLD,20));
        pass.setBounds(40,150,120,50);
        add(pass);
        pass_text = new JTextField();
        pass_text.setFont(new Font("맑은 고딕",Font.BOLD,20));
        pass_text.setBounds(150,150,230,50);
        add(pass_text);

        JButton enter = new JButton("입장하기");
        enter.setFont(new Font("맑은 고딕",Font.BOLD,20));
        enter.setBounds(150,220,200,50);
        add(enter);

        enter.addActionListener(this );
        setVisible(true);
    }
    public static void main(String []args){
        new MainClient();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("입장하기")) {
            BookClient2 client2 = new BookClient2();
            client2.runner(id_text.getText(),pass_text.getText());
            dispose();
        }
    }
}
