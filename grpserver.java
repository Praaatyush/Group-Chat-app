package grpchatapplication;

import java.net.*;
import java.io.*;
import java.util.*;

public class grpserver implements Runnable{
    
    Socket socket;
    
    public static Vector client = new Vector ();
    
    public grpserver(Socket socket){
        try{
            this.socket = socket;
        }catch(Exception e){
            
        }
    }
    
    public void run(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            client.add(writer);
            
            while(true){
                String data = reader.readLine().trim();
                System.out.println("Received: " + data);
                
                for(int i=0; i < client.size(); i++){
                    try{
                        BufferedWriter bw = (BufferedWriter)client.get(i);
                        bw.write(data);
                        bw.write("\r\n");
                        bw.flush();
                        
                    }catch(Exception e){
                        
                    }
                }
            }
        }catch(Exception e){
            
        }
    }
    
    public static void main(String[] args) throws Exception {
        ServerSocket s = new ServerSocket(2002);
        while(true){
            Socket socket = s.accept();
            grpserver server = new grpserver(socket);
            Thread thread = new Thread(server);
            thread.start();
        }
    }
}
