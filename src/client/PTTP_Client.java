package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PTTP_Client {

    public static void main(String[] args) throws IOException {
 
    	String message;
        Scanner sc = new Scanner(System.in);			// GET PTTP/1.0
        message = sc.nextLine();
                
        InetAddress serverAddress = InetAddress.getByName("localhost");
        System.out.println(serverAddress + ":" +Config.PORT);

        byte[] stringContents = message.getBytes(Config.ENCODING);
/*
        
        Socket socket = new Socket(serverAddress, Config.PORT);
        DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
        sentPacket.setAddress(serverAddress);
        sentPacket.setPort(42750);
        socket.send(sentPacket);
        
        DatagramPacket recievePacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
        socket.setSoTimeout(1010);
*/
        
        try (Socket socket = new Socket(Config.HOST, Config.PORT)) {

        	socket.setSoTimeout(1000);
        	
        	OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(message);
 
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            String line;
 
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }catch (SocketTimeoutException ste){
            System.out.println("Timeout");
        }
        
/*        
        try{
            socket.receive(recievePacket);
            System.out.println("Confirmation received");
        }catch (SocketTimeoutException ste){
            System.out.println("Timeout");
        }
  
        while(true) {
        	
        	String command;
            command= sc.nextLine();
            
            byte[] string = command.getBytes(Config.ENCODING);

            DatagramPacket commandPacket = new DatagramPacket(string, string.length);
            commandPacket.setAddress(serverAddress);
            commandPacket.setPort(Config.PORT);
            socket.send(commandPacket);
            
            recievePacket = new DatagramPacket( new byte[Config.BUFFER_SIZE], Config.BUFFER_SIZE);
            
            try{
                socket.receive(recievePacket);
                String list = new String(recievePacket.getData(), 0, recievePacket.getLength(), Config.ENCODING);
                System.out.println(list);
                
            }catch (SocketTimeoutException ste){
                System.out.println("Timeout");
            }
        }
*/
    }
}