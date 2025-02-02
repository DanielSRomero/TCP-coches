package servidor.domain.UseCase;

import java.io.PrintWriter;

import servidor.domain.interfaces.GenericRepositoryInterface;
import servidor.domain.interfaces.RestInterface;
import servidor.infraestructura.UserDataThread;

/*
 * VERSIÓN DE Santiago Rodenas Herráiz, para PSP 22-23
 * SIMULAMOS EL CASO DE USO QUE SIMULA EL DESLOGUEO
 */
public class LogoutUseCase implements RestInterface{
    private GenericRepositoryInterface repository;

    public LogoutUseCase (GenericRepositoryInterface repository){
        this.repository = repository;
    }


    public void responseHttp(String response, PrintWriter pw){
        pw.println(response);
        pw.flush();
    }

    /*
     *  @param pw (flujo salida), args (argumentos del comando), context (hilo que atiende al cliente)
     *  @return boolean true (correcto), false(no correcto)
     */
    @Override
    public boolean execute(PrintWriter pw, String[] args,  Thread context) {
        
        if (!((UserDataThread)context).isLogged()){
            responseHttp("Acción no permitida. Debes estar registrado!!", pw);
            return false;
        }


        //modificamos el contexto con el login a falso.
        ((UserDataThread)context).setLogged(false);
        ((UserDataThread)context).setExit();

        return true;
    }
    
}