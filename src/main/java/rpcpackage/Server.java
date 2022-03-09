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
        ServerSocket ss = new ServerSocket(7777);
        System.out.println("Waiting for players");
        getClients(ss);
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
        startGame();
    }
    
    static void startGame() {
        List<String> choices = new ArrayList<String>();
        //ClientHandler player1 = clientHandlers.get(0);
        //ClientHandler player2 = clientHandlers.get(1);
        System.out.println("Game started!");
        for(ClientHandler client:clientHandlers){
            choices.add(client.getAnswer());
        }
        System.out.println("Payer 1 chose: " + choices.get(0) + " and player 2 chose: " + choices.get(1));
        if(choices.get(0).equals(choices.get(1))){
            System.out.println("Result: Draw");
        }
        else if(choices.get(0).equals("r") && (choices.get(1).equals("s"))){
            System.out.println("Result: Player 1 wins!");
        }
    }
    
    static void getClients(ServerSocket ss) {
        try{
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
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    static void updateClientList(ClientHandler client){
        clientHandlers = client.getClientHandlers();
    }
}
