import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputThread extends Thread {
    private Socket socket;
    private PrintWriter output;
    private GUI gui;

    public OutputThread(Socket socket, GUI gui) {
        this.socket = socket;
        this.gui = gui;

        try {
            OutputStream out = socket.getOutputStream();
            output = new PrintWriter(out, true);
        } catch (IOException e) {
            System.out.println("OutputStream error: " + e.getMessage());
        }
    }

    public void run() {
        String message, timeStamp;

        String userName = "";
        while(userName.equals("")) {
           userName = gui.getUserName();
        }
        output.println(userName);

        do {
            message = "";
            while(message.equals(""))
                message = gui.getMessage();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            timeStamp = "[" + format.format(now) + "]: ";
            output.println(timeStamp + message);
        } while (!message.equals("."));

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Output error: " + e.getMessage());
        }
    }
}