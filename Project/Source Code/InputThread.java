import java.io.*;
import java.net.*;

public class InputThread extends Thread {
    private BufferedReader input;
    private GUI gui;

    public InputThread(Socket socket, GUI gui) {
        this.gui = gui;

        try {
            InputStream in = socket.getInputStream();
            input = new BufferedReader(new InputStreamReader(in));
        } catch (IOException e) {
            System.out.println("Input Stream Error: " + e.getMessage());
        }
    }

    public void run() {
        while (true) {
            try {
                String message = input.readLine();
                gui.updateChat(message);
            } catch (IOException e) {
                System.out.println("You have left Chatty");
                break;
            }
        }
    }
}

