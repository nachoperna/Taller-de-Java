package ejercicio4;

public interface Barberia {
    boolean llegaCliente() throws InterruptedException;
    void trabajaBarbero() throws InterruptedException;
    void cerrar();
}
