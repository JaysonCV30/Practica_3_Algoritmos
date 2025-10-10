package Logica;

import Logica.ColaSimple;
import Logica.Cliente;
import Logica.Caja;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulador {
    private List<Caja> cajas;
    private List<Cliente> clientes;
    private boolean modoFilaUnica; // true = fila única, false = múltiples filas
    private double tiempoActual;
    private Random random;

    public Simulador(boolean modoFilaUnica) {
        this.modoFilaUnica = modoFilaUnica;
        this.cajas = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.tiempoActual = 0;
        this.random = new Random();

        for (int i = 0; i < 12; i++) {
            cajas.add(new Caja(i));
        }
    }

    public void ejecutarSimulacion() {
        double tiempoSimulacion = 10 * 60; // 10 horas en minutos
        int idCliente = 0;

        while (tiempoActual < tiempoSimulacion) {
            double tiempoLlegada = tiempoActual + generarAleatorio(0.5, 1.0);
            double tiempoPago = generarAleatorio(3.0, 5.0);

            Cliente cliente = new Cliente(idCliente++, tiempoLlegada, tiempoPago);
            clientes.add(cliente);
            tiempoActual = tiempoLlegada;

            asignarCliente(cliente);
            actualizarCajas();
        }

        procesarPagos();
    }

    public void asignarCliente(Cliente cliente) {
        if (modoFilaUnica) {
            Caja caja = obtenerCajaDisponible();
            if(caja.getClientesEnEspera() < 4){
                caja.agregarCliente(cliente);
            } else {
                for(Caja c : cajas){
                    if(!c.estaAbierta()){
                        c.abrir();
                        c.agregarCliente(cliente);
                        return;
                    }
                }
                obtenerCajaMasCorta().agregarCliente(cliente);
            }
        } else {
            obtenerCajaMasCorta().agregarCliente(cliente);
        }
    }

    private Caja obtenerCajaDisponible() {
        for (Caja caja : cajas) {
            if (!caja.estaAbierta() || caja.getClientesEnEspera() < 4) {
                caja.abrir();
                return caja;
            }
        }
        for (Caja caja : cajas) {
            if (!caja.estaAbierta()) {
                caja.abrir();
                return caja;
            }
        }
        return cajas.get(0); // fallback
    }

    private Caja obtenerCajaMasCorta() {
        Caja mejor = null;
        int min = Integer.MAX_VALUE;
        for (Caja caja : cajas) {
            if (caja.estaAbierta()) {
                int tam = caja.getClientesEnEspera();
                if (tam < min) {
                    min = tam;
                    mejor = caja;
                }
            }
        }
        return mejor != null ? mejor : obtenerCajaDisponible();
    }

    public void actualizarCajas() {
        for (Caja caja : cajas) {
            if (caja.estaAbierta() && caja.getClientesEnEspera() == 0) {
                caja.cerrar();
            }
        }
    }

    private void procesarPagos() {
        for (Caja caja : cajas) {
            while (caja.getClientesEnEspera() > 0) {
                Cliente cliente = caja.atenderCliente(tiempoActual);
                if (cliente != null) {
                    cliente.setTiempoEspera(tiempoActual - cliente.getTiempoLlegada());
                    tiempoActual += cliente.getTiempoPago();
                    cliente.setTiempoSalida(tiempoActual);
                    caja.registrarPago(cliente.getTiempoPago());
                }
            }
        }
    }

    private double generarAleatorio(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public List<Caja> getCajas() {
        return cajas;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}