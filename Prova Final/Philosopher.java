import java.io.*;
import java.net.*;
import java.util.Random;

public class Philosopher {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String response = in.readLine(); // Recebe HI e ID
            int id = Integer.parseInt(response.split(" ")[1]);

            Random rand = new Random();
            while (true) {
                
                System.out.println("Philosopher " + id + " is thinking.");
                out.println("THINKING " + id);
                Thread.sleep(Math.max(0, (long) (rand.nextGaussian() * 2000 + 5000)));

                
                out.println("REQUEST_FORKS " + id);
                response = in.readLine();
                if ("GRANTED".equals(response)) {
                    System.out.println("Philosopher " + id + " is eating.");
                    out.println("EATING " + id);
                    Thread.sleep(3000);

                    
                    out.println("RELEASE_FORKS " + id);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
