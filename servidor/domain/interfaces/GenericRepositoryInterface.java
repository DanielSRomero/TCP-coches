package servidor.domain.interfaces;

public interface GenericRepositoryInterface<T> {

   String addCoche(String modelo, int cilindrada);
   String getCoche(int id);
   String getAllCocheIds();
   String updateCoche(int id, String modelo, int cilindrada);
   String deleteCoche(int id);
   public T findByEmail(String email);
   public T findByEmailAndPassw(String email, String pass);
   public void add(T o);      
    
}