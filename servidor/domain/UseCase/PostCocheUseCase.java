package servidor.domain.UseCase;

import servidor.domain.interfaces.GenericRepositoryInterface;
import servidor.domain.interfaces.RestInterface;
import servidor.infraestructura.UserDataThread;

import java.io.PrintWriter;

public class PostCocheUseCase implements RestInterface {
    private final GenericRepositoryInterface repository;

    public PostCocheUseCase(GenericRepositoryInterface repository) {
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

        if (args.length < 2) {
            responseHttp("error 400 (falta el modelo y la cilindrada)", pw);
            return false;
        }

        try {
            String modelo = args[0];
            int cilindrada = Integer.parseInt(args[1]);
            responseHttp(repository.addCoche(modelo, cilindrada), pw);
            return true;
        } catch (NumberFormatException e) {
            responseHttp("error 400 (ID inválido)", pw);
            return false;
        }
    }
}
