import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerThread extends Thread {
    private Socket socket;
    private ChattyServer server;
    private PrintWriter writer;

    public ServerThread(Socket socket, ChattyServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String clientMsg;
            String[] message;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm:ss a");
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            printUsers();

            String userName = reader.readLine();
            server.addUserName(userName);

            LocalDateTime now = LocalDateTime.now();
            String timeStamp = "[" + format.format(now) + "]: ";
            String serverMsg = "New Chatty user " + timeStamp + userName;
            System.out.println(serverMsg);
            server.enqueue(serverMsg, Type.Message);

            do {
                clientMsg = reader.readLine();
                message = clientMsg.split(" ");
                System.out.println(clientMsg);
                serverMsg = "[" + userName + "]" + clientMsg;
                if(!message[2].equals("."))
                    server.enqueue(serverMsg, Type.Message);
            } while (!message[2].equals("."));

            server.removeUser(userName, this);
            socket.close();

            serverMsg = "[" + userName + "]" + message[0] + message[1] + " has left Chatty.";
            server.enqueue(serverMsg, Type.Disconnect);
        } catch (IOException e) {
            System.out.println("ClientThread error: " + e.getMessage());
        }
    }

    public void sendMessage(String message) { writer.println(message); }

    public void printUsers() {
        if (server.hasUsers())
            writer.println("Users in Chatty: " + server.getUserNames());
        else
            writer.println("No users in Chatty");
    }
}
