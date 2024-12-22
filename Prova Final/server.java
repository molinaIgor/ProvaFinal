import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static final Map<Integer, PhilosopherRecord> philosophers = new HashMap<>();
    private static final Object[] forks = new Object[5]; 

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);

            
            for (int i = 0; i < forks.length; i++) forks[i] = new Object();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new PhilosopherHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class PhilosopherHandler extends Thread {
        private Socket socket;
        private int id;

        public PhilosopherHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ) {
                String input;
                id = new Random().nextInt(1000);
                philosophers.put(id, new PhilosopherRecord());

                out.println("HI " + id);

                while ((input = in.readLine()) != null) {
                    String[] command = input.split(" ");

                    switch (command[0]) {
                        case "THINKING":
                            philosophers.get(id).incrementThinking();
                            out.println("ACK THINKING");
                            break;

                        case "REQUEST_FORKS":
                            synchronized (forks[id % 5]) {
                                synchronized (forks[(id + 1) % 5]) {
                                    out.println("GRANTED");
                                }
                            }
                            break;

                        case "EATING":
                            philosophers.get(id).incrementEating();
                            out.println("ACK EATING");
                            break;

                        case "RELEASE_FORKS":
                            out.println("ACK RELEASED");
                            break;

                        case "DISCONNECT":
                            out.println("BYE");
                            philosophers.remove(id);
                            return;

                        default:
                            out.println("ERROR");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class PhilosopherRecord {
    private int thinkingCount = 0;
    private int eatingCount = 0;

    public synchronized void incrementThinking() {
        thinkingCount++;
    }

    public synchronized void incrementEating() {
        eatingCount++;
    }
}
