import java.io.*;
import java.net.*;
import java.util.*;

enum Type {Message, Disconnect}

class UserSet {
    String message;
    Type type;

    public UserSet(String message, Type type) {
        this.message = message;
        this.type = type;
    }
}

public class ChattyServer {
    private int port;
    private Queue<UserSet> queueMessage = new LinkedList<>();
    private Queue<UserSet> queueDisconnect = new LinkedList<>();
    private Set<String> userNames = new HashSet<>();
    private Set<ServerThread> users = new HashSet<>();

    public ChattyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 5000;

        ChattyServer chatty = new ChattyServer(port);
        chatty.execute();
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            BroadcastThread broadcastThread = new BroadcastThread(this);
            broadcastThread.start();

            System.out.println("Chatty Server successfully connected to port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                ServerThread user = new ServerThread(socket, this);
                users.add(user);
                user.start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    public void broadcast() {
        System.out.print("");
        UserSet userSet;
        while(!queueDisconnect.isEmpty()) {
            userSet = queueDisconnect.remove();

            for(ServerThread user : users) {
                user.sendMessage(userSet.message);
            }
        }
        while(!queueMessage.isEmpty()) {
            userSet = queueMessage.remove();

            for(ServerThread user : users) {
                user.sendMessage(userSet.message);
            }
        }
    }

    public void enqueue(String message, Type type) {
        UserSet userSet = new UserSet(message, type);
        if(type != Type.Disconnect) {
            queueMessage.add(userSet);
            System.out.print("");
        }
        else
            queueDisconnect.add(userSet);
    }

    public void removeUser(String userName, ServerThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            users.remove(aUser);
            System.out.println("Chatty user " + userName + " quit");
        }
    }

    public boolean hasUsers() { return !this.userNames.isEmpty(); }

    public void addUserName(String userName) { userNames.add(userName); }

    public Set<String> getUserNames() { return this.userNames; }
}
