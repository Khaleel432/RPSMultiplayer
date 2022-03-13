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
public class Server {
    public static String version = "Version 0.3";
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    
    public static void main(String args[]) throws IOException{
        getClients();
        startGame();
        disconnectClients();
    }
    
    static void startGame() {
        List<String> choices = new ArrayList<String>();
        ClientHandler player1 = clientHandlers.get(0);
        ClientHandler player2 = clientHandlers.get(1);
        player1.resetPoints();
        player2.resetPoints();
        System.out.println("Game started!");
        while((player1.getPoints() != 3) && (player2.getPoints() != 3)){
            for(ClientHandler client:clientHandlers){
                choices.add(client.getAnswer());
            }
            String player1Choice = choices.get(0);
            String player2Choice = choices.get(1);
            choices.clear();
            System.out.println(player1.getUsername() + " chose: " + player1Choice + " and " + player2.getUsername() + " chose: " + player2Choice);
            String result = "";
            if(player1Choice.equals(player2Choice)){
                System.out.println("Result: Draw");
                result = "Result was a draw!";
                sendResult(result);
                System.out.println(result);
            }
            else {
                switch(player1Choice+player2Choice){
                    case "rs":
                    case "pr":
                    case "sp":
                        result = "Result: " + player1.getUsername() + " wins!";
                        sendResult(result);
                        System.out.println(result);
                        player1.addPoint();
                        break;
                    case "sr":
                    case "ps":
                    case "rp":
                        result = "Result: " + player2.getUsername() + " wins!";
                        sendResult(result);
                        System.out.println(result);
                        player2.addPoint();
                        break;
                }
            }
        }
    }
    
    static void disconnectClients(){
        for(ClientHandler client:clientHandlers){
            client.disconnectClient();
        }
    }
    
    static void sendResult(String result) {
        for(ClientHandler client:clientHandlers){
            client.updateResult(result);
        }
    }
    
    static void getClients() {
        try{
            ServerSocket ss = new ServerSocket(7777);
            ss.setSoTimeout(10000);// TEST THIS 
            System.out.println("Waiting for players");
            while(clientHandlers.size()<2){
                Socket s = ss.accept();
                System.out.println("The client "+s+" has connected!");
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                
                System.out.println("Assigning client " + s + " a new thread.");
                ClientHandler newClient = new ClientHandler(s,dis,dos);
                Thread t = newClient;
                t.start();
                updateClientList(newClient);
                System.out.println(newClient.getUsername() + " has registered");
            }
            
            System.out.println("Current number of clients: " + clientHandlers.size());
            System.out.flush();
            System.out.printf("Current connected clients: ");
            for(ClientHandler client:clientHandlers){
                System.out.printf(client.getUsername());
                if(clientHandlers.indexOf(client)!=clientHandlers.size()-1){
                    System.out.printf(", ");
                }
            }
            System.out.println();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    static void updateClientList(ClientHandler client){
        clientHandlers = client.getClientHandlers();
    }
}
