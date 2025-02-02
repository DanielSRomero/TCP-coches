package servidor.domain.UseCase;

import servidor.domain.interfaces.RestInterface;
import servidor.domain.interfaces.GenericRepositoryInterface;
import servidor.infraestructura.UserDataThread;

import java.io.PrintWriter;

public class GetAllCochesUseCase implements RestInterface {
    private final GenericRepositoryInterface repository;

    public GetAllCochesUseCase(GenericRepositoryInterface repository) {
        this.repository = repository;
    }
    public void responseHttp(String response, PrintWriter pw){
        pw.println(response);
        pw.flush();
    }
    @Override
    public boolean execute(PrintWriter pw, String[] args, Thread context) {
        if (!((UserDataThread)context).isLogged()){
            responseHttp("error 400 (usuario no autenticado)", pw);
            return false;
        }
        try {
            responseHttp(repository.getAllCocheIds(), pw);
            return true;
        } catch (NumberFormatException e) {
            responseHttp("error 400 (ID inválido)", pw);
            return false;
        }
    }
}
