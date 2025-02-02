package servidor.domain.UseCase;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import servidor.domain.interfaces.GenericRepositoryInterface;
import servidor.domain.interfaces.RestInterface;
import servidor.domain.model.User;
import servidor.infraestructura.UserDataThread;

public class RegisterUseCase implements RestInterface{
    private GenericRepositoryInterface repository;

    public RegisterUseCase (GenericRepositoryInterface repository){
        this.repository = repository;
    }

    public void responseHttp(String response, PrintWriter pw){
        pw.println(response);
        pw.flush();
    }


    @Override
    public boolean execute(PrintWriter pw, String[] args, Thread context) {


        if (args.length < 3){
            responseHttp("Debes pasar el nombre, email y passw", pw);
            return false;
        }


        User u = (User)repository
                .findByEmail(args[1]); 

        if (u != null){
            responseHttp("Ese usuario ya está registrado ", pw);
            return false;
        }

        repository.add(new User(args));  

        try{


            final String  path="C:\\Users\\danie\\OneDrive\\Escritorio\\TCP coches\\files\\";  
            File file = new File(path + args[0] + ".dat");
            final String absolutePathFile = file.getAbsolutePath();
            
            FileWriter userFile = new FileWriter(absolutePathFile); 
            PrintWriter pwf = new PrintWriter(userFile);
            String info = "Nombre: " +
                args[0] + ", Email: " +
                args[1] + ", Passwd: " +
                args[2];

            pwf.println("Datos del usuario " + info);  //escribimos la información en fichero
            pwf.close();
        }catch (Exception e){
            e.printStackTrace();
            responseHttp("Error al crear fichero del usuario en registro", pw);
            return false;
        }

        responseHttp("Usuario registrado correctamente...", pw);
        ((UserDataThread)context).setLogged(true); //No tengo porqué loguearme después
      
        return true;
        
    }
    
}