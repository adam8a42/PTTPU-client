package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;


public class Frame extends JFrame {
    boolean noJComboBoxListener;
    JToggleButton encryptButton, runButton;
    JComboBox files;
    JTextField address;
    JPanel topPanel;
    PTTP_Client pttp_client;
    public String[] ls;
    public Frame() {
        noJComboBoxListener = false;
        ls = new String[]{".."};
        this.setLocationRelativeTo(null);
        pttp_client = new PTTP_Client();
        this.setSize(400, 140);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        encryptButton = new JToggleButton("Encryption");
        this.add(encryptButton, BorderLayout.SOUTH);
        encryptButton.setVisible(true);

        runButton = new JToggleButton("Run");
        files = new JComboBox<String>(ls);
        this.add(files, BorderLayout.CENTER);
        files.setVisible(true);

        address = new JTextField();

        address.setEditable(true);
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,2));
        this.add(topPanel, BorderLayout.NORTH);
        topPanel.add(address);
        topPanel.add(runButton);
        topPanel.setVisible(true);
        address.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(address.getText().split("/")[0].equals("pttp:")) && !(address.getText().split("/")[0].equals("pttpu:")) )
                {

                    if (Frame.this.encryptButton.getModel().isSelected()) {
                        address.setText("pttpu://" + address.getText());
                    }
                    else {
                        address.setText("pttp://" + address.getText());
                    }

                }
                if( address.getText().charAt(address.getText().length()-1)!='/')
                    address.setText(address.getText() + "/");
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    String command = address.getText();
                    String[] list = Frame.this.pttp_client.communicate(command);
                    Frame.this.readls(list);
                } catch (IOException e2) {
                    System.out.println("IO exception");
                }
                Frame.this.runButton.setSelected(false);

            }
        });
        files.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Frame.this.files.getSelectedItem()!=null && !Frame.this.noJComboBoxListener){
                    String[] paths = Frame.this.address.getText().split("/");
                    String element = Frame.this.files.getSelectedItem().toString();
                    if(element == ".." && paths.length >3) {
                        String path = "";
                        for(int i = 0; i< paths.length-1;i++)
                            path += (paths[i] + "/");
                        Frame.this.address.setText(path);
                    }
                    else if(element != "..")
                        Frame.this.address.setText(Frame.this.address.getText() + element + "/");
            }   }
        });
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Frame.this.encryptButton.isSelected() && Frame.this.address.getText().split("/")[0].equals("pttp:"))
                {
                    String[] path = Frame.this.address.getText().split("/");
                    path[0] = "pttpu:";
                    String res = "";
                    for (String i : path)
                        res += (i + "/");
                    Frame.this.address.setText(res);
                }
                if(!Frame.this.encryptButton.isSelected() && Frame.this.address.getText().split("/")[0].equals("pttpu:"))
                {
                    String[] path = Frame.this.address.getText().split("/");
                    path[0] = "pttp:";
                    String res = "";
                    for (String i : path)
                        res += (i + "/");
                    Frame.this.address.setText(res);
                }
            }
        });
        this.setVisible(true);
    }
    public void readls(String[] list)
    {
        noJComboBoxListener = true;
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
        noJComboBoxListener = false;
    }
    public static void main(String[] args) {
        Frame frame = new Frame();
    }
}
