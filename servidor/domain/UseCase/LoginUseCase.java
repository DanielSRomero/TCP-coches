package servidor.domain.UseCase;

import servidor.domain.interfaces.RestInterface;
import servidor.domain.model.User;
import servidor.domain.interfaces.GenericRepositoryInterface;
import servidor.infraestructura.UserDataThread;

import java.io.PrintWriter;

public class LoginUseCase implements RestInterface {
    private GenericRepositoryInterface repository;

   public LoginUseCase (GenericRepositoryInterface repository){
        this.repository = repository;
     }

     public void responseHttp(String response, PrintWriter pw){
        pw.println(response);
        pw.flush();
    }


     
    @Override
    public boolean execute(PrintWriter pw, String[] args,   Thread context) {

        if (args.length < 2){
            responseHttp("Debes pasar el email y passw", pw);
            return false;
        }


        User user = (User)repository
        .findByEmailAndPassw(args[0], args[1]);  //Buscamos por email

        
        if(user == null){
            responseHttp("Usuario no encontrado", pw);
            return false;
        }
        else{
            responseHttp("Usuario logueado correctamente ", pw);
            ((UserDataThread)context).setLogged(true);
            return true;
        }
               
        
    }
}
