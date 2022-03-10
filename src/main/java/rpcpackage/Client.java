/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rpcpackage;
import java.net.*;
import java.io.*;

/**
 *
 * @author Khaleel
 */
public class Client {
    
    public static void main(String args[]) throws IOException{
        boolean isConnected = true;
        Socket s = new Socket("localhost",7777);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        
        while(isConnected==true){
            sendMessage(br,dis,dos,isConnected);
        }
        System.out.println("Lost connection to server");
        closeResources(s,br,dis,dos);
    }
    
    static void sendMessage(BufferedReader br, DataInputStream dis, DataOutputStream dos, Boolean isConnected) {
        try{
            String recieved = dis.readUTF();
            if(recieved.equals("Server joined")){
                recieved = dis.readUTF();
            }
            else if(recieved.equals("disconnect")){
                isConnected = false;
                System.out.println("Game complete, disconnecting from server.");
            }
            if (isConnected) {
                /*else if(recieved.equals("clearConsole")){
                clearConsole();
                System.out.println("Clearing Console");
                recieved = dis.readUTF();
                }*/
                System.out.println(recieved);
                String sendMessage = br.readLine();
                dos.writeUTF(sendMessage);
                dos.flush();
                System.out.println(choice(sendMessage));
                //if(!recieved.equals("Server joined")){}
                System.out.println("Waiting for opponent");
            }
            
        }
        catch(IOException e){
            System.out.println(e);
            isConnected = false;
        }
    }
    
    /*static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }*/
    
    static String choice(String choice) {
        if(choice.equals("r")){
            return "You chose rock.";
        }
        else if(choice.equals("p")){
            return "You chose paper.";
        }
        else if(choice.equals("s")){
            return "You chose scissor";
        }
        return "";
    }
    
    static void closeResources(Socket s, BufferedReader br, DataInputStream dis, DataOutputStream dos)throws IOException{
        s.close();
        br.close();
        dis.close();
        dos.close();
    }
}
