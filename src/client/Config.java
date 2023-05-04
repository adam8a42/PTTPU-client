package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Config {
	public static final String HOST = "localhost";
    public static final int PORT = 42750;
    public static final int PORT_U = 42751;
   // public static final Charset ENCODING = StandardCharsets.US_ASCII;
    public static final String ENCODING = "utf8";
    public static final int BUFFER_SIZE = 1024;
}