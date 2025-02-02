package servidor.aplicacion;

import servidor.domain.interfaces.GenericRepositoryInterface;
import servidor.domain.interfaces.RestInterface;
import servidor.infraestructura.UserDataThread;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import servidor.data.CocheRepository;
import servidor.domain.UseCase.*;

public class Routing {
    private final HashMap<String, RestInterface> maganerEndPoints;  //Simulamos el gestor de endpoint que el servidor puede tratar. 
    private final GenericRepositoryInterface repository; 

    public Routing() {
        this.repository = new CocheRepository<>();
        maganerEndPoints = new HashMap<>();
        maganerEndPoints.put("post", new PostCocheUseCase(this.repository));
        maganerEndPoints.put("get", new GetCocheUseCase(this.repository));
        maganerEndPoints.put("getAll", new GetAllCochesUseCase(this.repository));
        maganerEndPoints.put("put", new PutCocheUseCase(this.repository));
        maganerEndPoints.put("delete", new DeleteCocheUseCase(this.repository));
        maganerEndPoints.put("login", new LoginUseCase(this.repository));
        maganerEndPoints.put("logout", new LogoutUseCase(this.repository));
        maganerEndPoints.put("reg" , new RegisterUseCase(this.repository));
    }

    public void responseHttp(String response, PrintWriter pw){
        pw.println(response);
        pw.flush();
    }
     public boolean execute(PrintWriter pw, String body, UserDataThread context){
        System.out.println (body);
        String [] args = body.split(" ");  //separamos en líneas lo recibido como dato.
        
        try{
            System.out.println("Estamos dentro del hash, El verbo es "+ args[0]);
            String verb = args[0]; //extraemos el verbo del servicio.
            
            RestInterface endPoint = maganerEndPoints.get(verb);  //Seleccionamos el Servicio. Cada servicio será un caso de uso.

            if (endPoint == null){
                responseHttp("Error, debes pasar un comando válido", pw);
                return false;
            }
            

            String [] operationsArgs = new String[args.length - 1];  //copiamos sólo argumentos.
            System.arraycopy(args, 1,  operationsArgs, 0, args.length - 1);

            return(endPoint.execute(pw, operationsArgs, context));

        }catch(ArrayIndexOutOfBoundsException e){
            responseHttp("Error, debes pasar la acción", pw);
            return false;
        }
    }
}