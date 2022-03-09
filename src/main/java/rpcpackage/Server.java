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
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    
    public static void main(String args[]) throws IOException{
        getClients();
        startGame();
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
            System.out.println("Payer 1 chose: " + player1Choice + " and player 2 chose: " + player2Choice);
            if(player1Choice.equals(player2Choice)){
                System.out.println("Result: Draw");
            }
            else {
                switch(player1Choice+player2Choice){
                    case "rs":
                    case "pr":
                    case "sp":
                        System.out.println(player1.getUsername() + " wins!");
                        player1.addPoint();
                        break;
                    case "sr":
                    case "ps":
                    case "rp":
                        System.out.println(player2.getUsername() + " wins!");
                        player2.addPoint();
                        break;
                }
            }
        }
    }
    
    static void getClients() {
        try{
            ServerSocket ss = new ServerSocket(7777);
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
