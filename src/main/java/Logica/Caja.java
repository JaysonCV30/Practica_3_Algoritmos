package Logica;

import Logica.Cliente;
import Logica.ColaSimple;

public class Caja {
    private int id;
    private ColaSimple<Cliente> colaClientes;
    private boolean abierta;
    private double tiempoAbierta;
    private int clientesAtendidos;

    public Caja(int id) {
        this.id = id;
        this.colaClientes = new ColaSimple<>();
        this.abierta = false;
    }

    public void abrir() { abierta = true; }
    public void cerrar() { abierta = false; colaClientes = new ColaSimple<>(); }

    public void agregarCliente(Cliente c) {
        colaClientes.insertar(c);
    }

    public Cliente atenderCliente() {
        Cliente c = colaClientes.eliminar();
        if (c != null) {
            clientesAtendidos++;
        }
        return c;
    }

    public boolean estaAbierta() { return abierta; }
    public int getClientesEnEspera() { return colaClientesSize(); }

    public int colaClientesSize() {
        // Método auxiliar para contar elementos en ColaSimple
        int count = 0;
        ColaSimple<Cliente> copia = new ColaSimple<>();
        Cliente temp;
        while ((temp = colaClientes.eliminar()) != null) {
            copia.insertar(temp);
            count++;
        }
        // Restaurar cola original
        while ((temp = copia.eliminar()) != null) {
            colaClientes.insertar(temp);
        }
        return count;
    }

    public ColaSimple<Cliente> getColaClientes() {
        return colaClientes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public double getTiempoAbierta() {
        return tiempoAbierta;
    }

    public void setTiempoAbierta(double tiempoAbierta) {
        this.tiempoAbierta = tiempoAbierta;
    }

    public int getClientesAtendidos() {
        return clientesAtendidos;
    }

    public void setClientesAtendidos(int clientesAtendidos) {
        this.clientesAtendidos = clientesAtendidos;
    }

    
}
