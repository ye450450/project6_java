import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Chat extends JFrame implements ActionListener {
    TextArea write;
    TextField textField;
    public Chat(String name){
        setTitle("채팅창"+"("+name+")");
        setBounds(100,10,650,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white); //배경색 설정
        setLayout(null);

        JButton input = new JButton("입력");
        add(input);
        input.setFont(new Font("맑은 고딕", Font.BOLD,20));
        input.setBounds(480,350,100,50);
        input.addActionListener(this);
        JLabel label = new JLabel("내용:");
        label.setFont(new Font("맑은 고딕",Font.BOLD,25));
        add(label);
        label.setBounds(0,350,100,50);
        textField = new TextField();
        textField.setFont(new Font("맑은 고딕",Font.BOLD,20));
        add(textField);
        textField.setBounds(100,350,450,50);

        write = new TextArea();
        add(write);
        write.setFont(new Font("맑은 고딕",Font.BOLD,20));
        write.setBounds(0,0,600,400);

        setVisible(true);
    }

    public void read(String string){
        write.append(string);
        write.append("\n");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("입력")){
            try{
                BookClient2.ClientSender.out.writeUTF(textField.getText()+"$");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }//돌의 위치를 보냄->보내면 다음 턴을 쉰다.
            textField.setText("");
        }
    }
}
