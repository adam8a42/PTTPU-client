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

public class PTTP_Client {
	String decodedString;
	public PTTP_Client() {

	}
    public String[] communicate(String command) throws IOException {
		decodedString = "";
		String message;
		int port;
		String host;
		String pathToFile = "";

		message = command;
		String[] split = message.split("/");

		if (split[0].equals("pttp:")) {

			port = 42750;
			host = split[2];
			int i=3;
			while(i < split.length) {
				pathToFile += ("/" + split[i]);
				i++;
			}
		}
		else if (split[0].equals("pttpu:")) {

			port = 42751;
			host = split[2];
			int i=3;
			while(i < split.length) {
				pathToFile += ("/" + split[i]);
				i++;
			}
		}
		else {
			System.out.println("Incorrect input.");
			return decodedString.split(System.lineSeparator());
		}
		//pttp://localhost/test
		try (Socket socket = new Socket(host, port)) {
			socket.setSoTimeout(1000);

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			if(port == 42750) {

				writer.println("GET " + pathToFile + " PTTP/1.0");
				String line;
				while ((line = reader.readLine()) != null) {
					decodedString += (line+ "\n");
					System.out.println(decodedString);
				}
				socket.close();
			}
			else if(port == 42751) {

				String encodedString = Base64.getEncoder().encodeToString(("GET " + pathToFile + " PTTP/1.0").getBytes());
				writer.println(encodedString);

				String line;
				while ((line = reader.readLine()) != null) {
					byte[] decodedBytes = Base64.getDecoder().decode(line);
					decodedString += new String(decodedBytes);
				}
				socket.close();
			}
		}catch (SocketTimeoutException ste){

		}catch (UnknownHostException uhe) {
			System.out.println("Unknown Host\n");
		}catch (ConnectException ce){
			System.out.println("Failed to Connect\n");
		}
		String[] out = decodedString.split("\n");
		return out;
	}
}
