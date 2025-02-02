import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UserCliente {

    public static void main(String[] args) {
        int port;

        if (args.length < 2){
            System.out.println("Error en numero de argumentos, son 2 (ip-server y puerto)");
            System.exit(1);
        }
        port = Integer.parseInt(args[1]);
        try (Socket socket = new Socket(args[0], port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor:");
            while (true) {
                String command = scanner.nextLine();
                out.println(command);
                if (command.equalsIgnoreCase("n")) break;
                System.out.println("Response: " + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
