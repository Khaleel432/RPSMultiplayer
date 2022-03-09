/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rpcpackage;
import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Khaleel
 */
public class Client {
    
    public static void main(String args[]) throws IOException{
        Socket s = new Socket("localhost",7777);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        
        while(true){
            sendMessage(br,dis,dos);
            /*if(recieved.equals("")){
                recieved = dis.readUTF();
                System.out.println(recieved);
            }
            else{
                sendMessage(br,dis,dos);
            }
            String recieved = dis.readUTF();
            System.out.println(recieved);
            
            String sendMessage = br.readLine();
            if(sendMessage.equals("stop")){
                System.out.println("Closing Connection");
                closeResources(s,br,dis,dos);
                System.out.println("Connection closed");
                break;
            }
            
            dos.writeUTF(sendMessage);
            dos.flush();*/
        }
    }
    
    static void sendMessage(BufferedReader br, DataInputStream dis, DataOutputStream dos) {
        try{
            String recieved = dis.readUTF();
            System.out.println(recieved);
            if(!recieved.equals("Waiting for opponent")){
                String sendMessage = br.readLine();
                dos.writeUTF(sendMessage);
                dos.flush();
                System.out.println("Sending message: " + sendMessage);
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    static void closeResources(Socket s, BufferedReader br, DataInputStream dis, DataOutputStream dos)throws IOException{
        s.close();
        br.close();
        dis.close();
        dos.close();
    }
}
