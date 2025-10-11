package Logica;

import Logica.ColaSimple;

public class Caja {

    private int id;
    private ColaSimple<Cliente> colaClientes;
    private boolean abierta;
    private double tiempoAbierta;
    private double tiempoAbiertaTotal;
    private double tiempoUltimaApertura;
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

    public void abrir(double tiempoActual) {
        if (!abierta) {
            abierta = true;
            tiempoUltimaApertura = tiempoActual;
        }
    }

    public void cerrar(double tiempoActual) {
        abierta = false;
        tiempoAbiertaTotal += tiempoActual - tiempoUltimaApertura;
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
            cliente.setTiempoInicioPago(tiempoActual);
            cliente.setTiempoEspera(tiempoActual - cliente.getTiempoLlegada());
            cliente.setTiempoSalida(tiempoActual + cliente.getTiempoPago());
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
        return colaClientes.size(); 
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

    public double getTiempoAbiertaTotal() {
        return tiempoAbiertaTotal;
    }

    public double getTiempoAbiertaActual(double tiempoSimulado) {
        return estaAbierta() ? tiempoSimulado - tiempoUltimaApertura : 0;
    }

    public int getId() {
        return id;
    }
}
