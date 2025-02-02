package servidor.domain.model;

public class Coche {
    private final int id;
    private String modelo;
    private int cilindrada;

    public Coche(int id, String modelo, int cilindrada) {
        this.id = id;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
    }

    public int getId() { return id; }
    public String getModelo() { return modelo; }
    public int getCilindrada() { return cilindrada; }

    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setCilindrada(int cilindrada) { this.cilindrada = cilindrada; }

    @Override
    public String toString() {
        return String.format("{'id':'%d', 'modelo':'%s', 'cilindrada':'%d'}", id, modelo, cilindrada);
    }
}
