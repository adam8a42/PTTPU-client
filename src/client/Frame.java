package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.http.WebSocketHandshakeException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;
import javax.swing.JFrame;

public class Frame extends JFrame {

    Frame() {
        PTTP_Client frame = new PTTP_Client()
        frame.setSize(200, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        JToggleButton button = new JToggleButton("Encryption")

    }
    public static void main(String[] args){
        Frame frame = new Frame();
        PTTP_Client pttp_client = PTTP_Client();
        pttp_client.communicate("/pttp://localhost/test");
    }




}