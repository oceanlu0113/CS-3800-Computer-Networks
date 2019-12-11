package timeclient;

import java.net.*;
import java.io.*;

public class TimeClient {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    // take inputs and send to server
    public TimeClient(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            input = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
        String line = "";
        while (!line.equals("Over")) {
            try {
                line = input.readLine();
                out.writeUTF(line);
            } catch (IOException i) {
                System.out.println(i);
            }
        }
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    /*public TimeClient(String hostname) {
        int port = 13; // tcp daytime protocol
        try (Socket socket = new Socket(hostname, port)) {
            InputStream input = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            int character;
            StringBuilder data = new StringBuilder();
            while ((character = reader.read()) != -1) {
                data.append((char) character);
            }
            System.out.println(data);

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }*/

    public static void main(String[] args) {
        TimeClient client = new TimeClient("127.0.0.1", 7000);
        //TimeClient client = new TimeClient("time.nist.gov");
    }
}
