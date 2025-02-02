package servidor.data;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import servidor.domain.interfaces.GenericRepositoryInterface;
import servidor.domain.model.Coche;
import servidor.domain.model.User;

public class CocheRepository<T> implements GenericRepositoryInterface<T>{

    private List<Coche> coches;
    private List<User> userList;
    private AtomicInteger automaticIdUsers;
    private AtomicInteger automaticIdCoches;




    public CocheRepository() {
        automaticIdUsers = new AtomicInteger(0);
        automaticIdCoches = new AtomicInteger(0);
        userList = new ArrayList<User>();
        coches = new ArrayList<Coche>();
        /*
        Coches.add(new Coche(1, "mercedes", 2000));
        Coches.add(new Coche(2, "alfa", 2200));
        Coches.add(new Coche(3, "audi", 2500));
        Coches.add(new Coche(4, "bmw", 3000));
        Coches.add(new Coche(5, "cupra", 1700));
        */
    }
    @Override
    public synchronized String addCoche(String modelo, int cilindrada) {
        Coche Coche = new Coche(automaticIdCoches.incrementAndGet(), modelo, cilindrada);
        coches.add(Coche);
        return Coche.toString();
    }

    @Override
    public synchronized String getCoche(int id) {
        return coches.stream()
            .filter(c -> c.getId() == id)
            .map(Coche::toString)
            .findFirst()
            .orElse("error 400 (no existe ese vehículo)");
    }

    @Override
    public synchronized String getAllCocheIds() {
        return coches.stream().map(c -> String.valueOf(c.getId())).toList().toString();
    }

    @Override
    public synchronized String updateCoche(int id, String modelo, int cilindrada) {
        for (Coche c : coches) {
            if (c.getId() == id) {
                c.setModelo(modelo);
                c.setCilindrada(cilindrada);
                return "objeto actualizado";
            }
        }
        return "error 400 (no existe ese vehículo)";
    }

    @Override
    public synchronized String deleteCoche(int id) {
        return coches.removeIf(c -> c.getId() == id) ? "vehículo eliminado" : "error 400 (no existe ese vehículo)";
    }
    @Override
    public T findByEmail(String email) {
        User user = userList
            .stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst()
            .orElse(null);
        
        return (T) user;
    }
    @Override
    public T findByEmailAndPassw(String email, String pass) {
        User user = userList.stream()
        .filter(
            (u) -> u.getEmail().equals(email) && 
                    u.getPasswd().equals(pass))
        .findFirst()
        .orElse(null);

        return (T) user;
    }
    @Override
    public void add(T u) {
        if (u instanceof User){
        User user = (User) u;
        user.setId(automaticIdUsers.incrementAndGet());
        userList.add(user);
       }  
    }
}
