package servidor.domain.UseCase;

import java.io.PrintWriter;

import servidor.domain.interfaces.GenericRepositoryInterface;
import servidor.domain.interfaces.RestInterface;
import servidor.infraestructura.UserDataThread;

public class PutCocheUseCase implements RestInterface {
    private final GenericRepositoryInterface repository;

    public PutCocheUseCase(GenericRepositoryInterface repository) {
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

        if (args.length < 3) {
            responseHttp("error 400 (falta el ID, modelo y cilindrada)", pw);
            return false;
        }

        try {
            int id = Integer.parseInt(args[0]);
            String modelo = args[1];
            int cilindrada = Integer.parseInt(args[2]);
            responseHttp(repository.updateCoche(id, modelo, cilindrada), pw);
            return true;
        } catch (NumberFormatException e) {
            responseHttp("error 400 (ID inválido)", pw);
            return false;
        }
    }
}
