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
        System.out.println("Rock Paper Scissors" + Server.version);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Please input the server IP Address: ");
        String address = br.readLine();
        System.out.print("Please input the server port: ");
        int port = Integer.parseInt(br.readLine());
        
        System.out.println("Connecting to " + address + ":" + port);
        InetAddress ip = InetAddress.getByName(address);
        
        Socket s = new Socket(ip,port);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        sendMessage(s,br,dis,dos);
        System.out.println("Lost connection to server");
    }
    
    static void sendMessage(Socket s,BufferedReader br, DataInputStream dis, DataOutputStream dos) {
        boolean isConnected = true;
        while(isConnected==true){
            try{
                String recieved = dis.readUTF();
                if(recieved.equals("Server joined")){
                    recieved = dis.readUTF();
                }
                else if(recieved.equals("disconnect")){
                    isConnected = false;
                    System.out.println("Game complete, disconnecting from server.");
                    closeResources(s,br,dis,dos);
                }
                if(recieved.equals("clearConsole")){
                    try{
                        clearConsole();
                        recieved = dis.readUTF();
                    }
                    catch(IOException e){
                        System.out.println(e);
                    }
                    catch(InterruptedException e){
                        System.out.println(e);
                    }
                }
                if (isConnected) {
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
    }
    
    final static void clearConsole() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
    
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
    
    static void closeResources(Socket s,BufferedReader br, DataInputStream dis, DataOutputStream dos)throws IOException{
        s.close();
        br.close();
        dis.close();
        dos.close();
    }
}
