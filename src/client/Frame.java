package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.*;
import java.awt.BorderLayout;



public class Frame extends JFrame {
    JToggleButton button;
    JComboBox files;
    PTTP_Client pttp_client;
    public String[] ls;
    public boolean ready;
    public Frame() {
        ready = false;
        ls = new String[1];
        ls[0] = ("..");
        pttp_client = new PTTP_Client();
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        button = new JToggleButton("Encryption");
        this.add(button, BorderLayout.NORTH);
        button.setVisible(true);
        files = new JComboBox<String>(ls);
        this.add(files, BorderLayout.CENTER);
        files.setVisible(true);
        files.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(ready) {
                    try {
                        System.out.println("1");
                        String command = "";
                        if (button.getModel().isSelected())
                            command += "pttpu://";
                        else command += "pttp://";
                        command += "localhost/";
                        command += Frame.this.files.getSelectedItem();
                        String[] list = Frame.this.pttp_client.communicate(command);
                        Frame.this.readls(list);
                    } catch (IOException e2) {
                        System.out.println("IO exception");
                    }
                }
            }
        });
    }
    public void readls(String[] list)
    {
        ready = false;
        this.ls = new String[list.length+1];
        this.ls[list.length] = "..";
        for (int i = 0; i < list.length; i++)
        {
            this.ls[i] = list[i];
        }
        DefaultComboBoxModel model = (DefaultComboBoxModel) this.files.getModel();
        model.removeAllElements();
        for (String item : this.ls) {
            model.addElement(item);
        }
        files.setModel(model);
        this.files.repaint();
        ready = true;
    }
    public static void main(String[] args){
        Frame frame = new Frame();
        try {
            String command = "pttp";
            if (frame.button.getModel().isSelected())
                command = "pttpu";
            String[] list = frame.pttp_client.communicate(command + "://localhost/ ");
            frame.readls(list);
            frame.ready = true;
        }
        catch(IOException e){
            System.out.println("IO exception");
        }
    }
}