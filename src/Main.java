import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class Main extends JFrame implements Runnable {
    static public Socket connection;
    static private ObjectInputStream input;
    static private ObjectOutputStream output;

    public static void  main (String args[]){
        new Main("Test");
    }
    public  Main(String name){
        super(name);
        setLayout(new FlowLayout());
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

       final JTextField t1 = new JTextField(10);
       final JButton b1 = new JButton("Send");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==b1){
                    sendData(t1.getText());
                }
            }
        });
        add(t1);
        add(b1);
    }
    @Override
    public void run() {

            try {
                connection = new Socket(InetAddress.getByName("127.0.0.1"), 5678);
                while (true){
                    output = new ObjectOutputStream(connection.getOutputStream());
                    input = new ObjectInputStream(connection.getInputStream());
                    JOptionPane.showMessageDialog(null,(String)input.readObject());
                }
            }catch (UnknownHostException en){
            }
             catch (IOException e) {
                e.printStackTrace();
            }
            catch (HeadlessException e){
            }
            catch (ClassNotFoundException e){
            }

    }

    private static void sendData (Object obj){
        try {
            output.flush();
            output.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
