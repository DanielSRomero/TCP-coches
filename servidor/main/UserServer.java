package servidor.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import servidor.aplicacion.Routing;
import servidor.infraestructura.UserDataThread;


public class UserServer {
    private static Routing routing;  //Gestor de operaciones.
    int port;
    public static void main(String[] args) {
        int port = -1; 
        if (args.length == 0) {
            System.out.println("Debes pasar el puerto a escuchar");
            System.exit(1);  
        }


        try{
            port = Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
            System.out.println("Error en el tipo puerto");
            System.exit(2); 
        }
        routing = new Routing();  

        System.out.println("Servidor a la escucha del puerto  " + port);
        System.out.println("Esperando conexión ......");
    
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {

                Socket socketClient = serverSocket.accept();
                System.out.printf("Conectando con %s:%d%n",
                    socketClient.getInetAddress(),
                    socketClient.getPort()
                );
    
                new UserDataThread(socketClient,  routing).start();
            }
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
 
}