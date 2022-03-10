/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rpcpackage;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Khaleel
 */
public class ClientHandler extends Thread{
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String username;
    private String result = "";
    private int points = 0;
    
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) throws IOException{
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        dos.writeUTF("Please type a username:");
        this.username = dis.readUTF();
        clientHandlers.add(this);
    }
    
    @Override
    public void run(){
        try {
            if(clientHandlers.size()<2){
                dos.writeUTF("Server joined");
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    void updateResult(String result) {
        this.result = result;
    }
    
    void disconnectClient() {
        try{
            dos.writeUTF("disconnect");
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
    String listMenu() {
        ClientHandler player1 = clientHandlers.get(0);
        ClientHandler player2 = clientHandlers.get(1);
        String menu = "\nRock Paper Scissors " + Server.version;
        menu += "\n" + player1.getUsername() + ":" + player1.getPoints();
        menu += "\t" + player2.getUsername() + ":" + player2.getPoints();
        menu += "\n" + result;
        menu += listChoice();
        return menu;
    }
    
    String listChoice() {
        String message = "\n\nPlease choose one of the following:\n";
        message += "    Scissors:   s\n";
        message += "    Paper:      p\n";
        message += "    Rock:       r\n";
        message += "Choice(s/p/r): ";
        return message;
    }
    
    boolean invalidChoice(String choice){
        if(choice.equals("s") || choice.equals("p") || choice.equals("r")){
            return false;
        }
        return true;
    }
    
    public String getAnswer() {
        String choice = "";
        try{
            System.out.println("Getting answer");
            //dos.writeUTF("clearConsole");
            dos.writeUTF(listMenu());
            choice = dis.readUTF();
            while(invalidChoice(choice)){
                String message = "Invalid choice, please try again." + listMenu();
                dos.writeUTF(message);
                choice = dis.readUTF();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        return choice;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void resetPoints(){
        points = 0;
    }
    
    public void addPoint(){
        points++;
    }
    
    public String getUsername(){
        return username;
    }
    
    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
}
