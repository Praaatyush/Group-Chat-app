package grpchatapplication;

import java.awt.event.*;
import java.net.ServerSocket;
import javax.swing.*;
import java.io.*;
import java.lang.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

import javax.swing.border.*;
import java.net.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class user3 extends JFrame implements ActionListener,Runnable {

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f1 = new JFrame ();
    static JTextArea ta1;
    
    BufferedWriter writer;
    BufferedReader reader;

//    static ServerSocket skt;
//    static Socket s;
//    static DataInputStream din;
//    static DataOutputStream dout;
    
    Boolean typing ;

    user3() {

        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(54, 120, 200));
        p1.setBounds(0, 0, 320, 50);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(4, 12, 25, 25);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/1.png"));
        Image i5 = i4.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i6);
        l2.setBounds(32, 3, 45, 45);
        p1.add(l2);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/3icon.png"));
        Image i8 = i7.getImage().getScaledInstance(10, 20, Image.SCALE_AREA_AVERAGING);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l6 = new JLabel(i9);
        l6.setBounds(300, 12, 10, 20);
        p1.add(l6);

        JLabel l3 = new JLabel("Group 12");
        l3.setFont(new Font("SAN_SERIT", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(85, 0, 140, 28);
        p1.add(l3);

        JLabel l4 = new JLabel("Vatsal,Pratyush,Harsh,Sanskruti");
        l4.setFont(new Font("SAN_SERIT", Font.PLAIN, 12));
        l4.setForeground(Color.WHITE);
        l4.setBounds(85, 20, 190, 28);
        p1.add(l4);
        
        Timer t = new Timer(1,new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(!typing){
                    l4.setText("Vatsal,Pratyush,Harsh,Sanskruti");
                }
            }
        });
        
        t.setInitialDelay(2000);

        t1 = new JTextField();
        t1.setBounds(5, 500, 240, 35);
        t1.setFont(new Font("SAN_SERIT", Font.PLAIN, 16));
        f1.add(t1);
        
        t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                l4.setText("Pratyush typing...");
                t.stop();
                typing = true;
            }
            public void keyReleased(KeyEvent ke){
                typing = false;
                if(!t.isRunning()){
                    t.start();
                }
            }
        });

        b1 = new JButton("Send");
        b1.setBounds(250, 500, 65, 35);
        b1.setBackground(new Color(54, 120, 200));
        b1.setFont(new Font("SAN_SERIT", Font.PLAIN, 12));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        f1.add(b1);

        a1 = new JPanel();
//        a1.setBounds(5, 55, 389, 438);
        a1.setFont(new Font("SAN_SERIT", Font.PLAIN, 16));       
//        a1.setBackground(Color.WHITE);
//        f1.add(a1);
        
        JScrollPane sp = new JScrollPane(a1);
        sp.setBounds(5, 55, 310, 438);
        sp.setBorder(BorderFactory.createEmptyBorder());
      
        f1.getContentPane().setBackground(Color.WHITE);
        f1.getContentPane().add(sp);
        f1.setLayout(null);
        f1.setSize(320, 550);
        f1.setLocation(680, 80);
        f1.setUndecorated(true);
        f1.setVisible(true);
        
        try{
           Socket socketClient = new Socket("localhost",2002);
           writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
           reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        }catch(Exception e){}
    }

    public void actionPerformed(ActionEvent ae) {
        
        String str = "Praytush: " + t1.getText();
        
        try {
            sendTextToFile(str);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(user3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            writer.write(str);
            writer.write("\r\n");
            writer.flush();
        } catch (Exception e) {}
        t1.setText("");
    }
    
    public void sendTextToFile(String msg) throws FileNotFoundException{
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
         LocalDateTime now = LocalDateTime.now();  
       
        String da = now.format(dtf);
        String ti = l2.getText();
        String chatrec = msg;
        try
        {
           Connection con = database.getConnection();
           String sql = "insert into grpchat(date,chat_time,chat) values(?,?,?)";
           PreparedStatement pst = con.prepareStatement(sql);
           pst.setString(1,da);
           pst.setString(2,ti);
           pst.setString(3,chatrec);
           int updatedRowCount = pst.executeUpdate();
        
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public static JPanel formatLabel(String out){
        
        a1.setLayout(new BoxLayout(a1,BoxLayout.Y_AXIS));
        
        JLabel l1 = new JLabel("<html><p style = \"width : 220px\">" + out + "</p> </html>");
        l1.setFont(new Font("Fahoma", Font.PLAIN, 15));
        l1.setForeground(new Color(255,255,255));
        l1.setBackground(new Color (0,18,49));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(10,10,10,10));
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));
        
        a1.add(l1);
        a1.add(l2);
        return a1;
    }
    
    public void run(){
        try{
            String msg="";
            while((msg = reader.readLine()) != null){
                formatLabel(msg);
            }
        }catch(Exception e){
            
        }
    }
    
    public static void main(String[] args){
//        new user1().setVisible(true);
        user3 one = new user3();
        Thread t1 = new Thread(one);
        t1.start();
    }
}