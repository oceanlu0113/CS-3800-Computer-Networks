import java.net.*;

public class ChattyClient {
    private int port;
    private String host;

    public ChattyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        ChattyClient client = new ChattyClient(host, port);
        client.execute();
    }

    public void execute() {
        try {
            GUI gui = new GUI();
            gui.createGUI();

            Socket socket = new Socket(host, port);

            System.out.println("Connected to the Chatty server");
            new InputThread(socket, gui).start();
            new OutputThread(socket, gui).start();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}