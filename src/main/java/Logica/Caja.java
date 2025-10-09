package Logica;

import Logica.ColaSimple;

public class Caja {

    private int id;
    private ColaSimple<Cliente> colaClientes;
    private boolean abierta;
    private double tiempoAbierta;
    private int clientesAtendidos;
    private double tiempoTotalPagos;

    public Caja(int id) {
        this.id = id;
        this.colaClientes = new ColaSimple<>();
        this.abierta = false;
        this.tiempoAbierta = 0;
        this.clientesAtendidos = 0;
        this.tiempoTotalPagos = 0;
    }

    public void abrir() {
        abierta = true;
    }

    public void cerrar() {
        abierta = false;
        colaClientes = new ColaSimple<>();
    }

    public void agregarCliente(Cliente cliente) {
        if (colaClientes.size() < 20) {
            colaClientes.insertar(cliente);
        } else {
            System.out.println("La caja " + id + " está llena. Cliente no agregado.");
        }
    }

    public Cliente atenderCliente(double tiempoActual) {
        Cliente cliente = colaClientes.eliminar();
        if (cliente != null) {
            clientesAtendidos++;
            double tiempoEspera = tiempoActual - cliente.getTiempoLlegada();
            cliente.setTiempoEspera(tiempoEspera);
            cliente.setTiempoSalida(tiempoActual);
            registrarPago(cliente.getTiempoPago());
        }
        return cliente;
    }

    public void registrarPago(double tiempoPago) {
        tiempoTotalPagos += tiempoPago;
    }

    public boolean estaAbierta() {
        return abierta;
    }

    public int getClientesEnEspera() {
        return colaClientes.size(); // usa tu método size() en ColaSimple
    }

    public ColaSimple<Cliente> getColaClientes() {
        return colaClientes;
    }

    public int getClientesAtendidos() {
        return clientesAtendidos;
    }

    public double getTiempoTotalPagos() {
        return tiempoTotalPagos;
    }

    public int getId() {
        return id;
    }
}
