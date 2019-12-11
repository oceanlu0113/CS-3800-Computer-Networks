public class BroadcastThread extends Thread {
    private ChattyServer server;

    public BroadcastThread(ChattyServer server) { this.server = server; }

    public void run(){
        while(true)
            server.broadcast();
    }

}
