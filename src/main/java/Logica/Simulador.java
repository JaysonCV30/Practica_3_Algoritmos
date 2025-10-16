package Logica;

import Logica.ColaSimple;
import Logica.Cliente;
import Logica.Caja;
import java.util.Random;

public class Simulador {

    private Caja[] cajas;
    private ColaSimple<Cliente> clientes;
    private ColaSimple<Cliente> clientesAtendidos;
    private boolean modoFilaUnica;
    private Random random;

    public Simulador(boolean modoFilaUnica) {
        this.modoFilaUnica = modoFilaUnica;
        this.cajas = new Caja[12];
        this.clientes = new ColaSimple<>();
        this.clientesAtendidos = new ColaSimple<>();
        this.random = new Random();

        for (int i = 0; i < cajas.length; i++) {
            cajas[i] = new Caja(i);
        }
        if (!modoFilaUnica) {
            for (Caja caja : cajas) {
                caja.abrir(0);
            }
        }
    }

    public void ejecutarSimulacion() {
        double tiempoActual = 0;
        double tiempoSimulacion = 180;
        int idCliente = 0;

        while (tiempoActual < tiempoSimulacion) {
            double tiempoLlegada = tiempoActual + generarAleatorioSegundos(0.5, 1.0);
            double tiempoPago = generarAleatorioSegundos(3.0, 5.0);

            Cliente cliente = new Cliente(idCliente++, tiempoLlegada, tiempoPago);
            clientes.insertar(cliente);
            tiempoActual = tiempoLlegada;

            asignarCliente(cliente, tiempoActual);
            actualizarCajas(tiempoActual);
        }

        procesarPagos(tiempoActual);
        cerrarCajasFinal(tiempoActual);
    }

    public void asignarCliente(Cliente cliente, double tiempoActual) {
        if (modoFilaUnica) {
            Caja caja = obtenerCajaDisponible(tiempoActual);
            if (caja.getClientesEnEspera() < 4) {
                caja.agregarCliente(cliente);
            } else {
                for (Caja c : cajas) {
                    if (!c.estaAbierta()) {
                        c.abrir(tiempoActual);
                        c.agregarCliente(cliente);
                        return;
                    }
                }
                obtenerCajaMasCorta(tiempoActual).agregarCliente(cliente);
            }
        } else {
            obtenerCajaMasCorta(tiempoActual).agregarCliente(cliente);
        }
    }

    private Caja obtenerCajaDisponible(double tiempoActual) {
        for (Caja caja : cajas) {
            if (!caja.estaAbierta() || caja.getClientesEnEspera() < 4) {
                caja.abrir(tiempoActual);
                return caja;
            }
        }
        for (Caja caja : cajas) {
            if (!caja.estaAbierta()) {
                caja.abrir(tiempoActual);
                return caja;
            }
        }
        return cajas[0];
    }

    private Caja obtenerCajaMasCorta(double tiempoActual) {
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
        return mejor != null ? mejor : obtenerCajaDisponible(tiempoActual);
    }

    public void actualizarCajas(double tiempoActual) {
        if (modoFilaUnica) {
            for (Caja caja : cajas) {
                if (caja.estaAbierta() && caja.getClientesEnEspera() == 0) {
                    caja.cerrar(tiempoActual);
                }
            }
        }
    }

    private void procesarPagos(double tiempoActual) {
        for (Caja caja : cajas) {
            while (caja.getClientesEnEspera() > 0) {
                Cliente cliente = caja.atenderCliente(tiempoActual);
                if (cliente != null) {
                    cliente.setTiempoEspera(tiempoActual - cliente.getTiempoLlegada());
                    cliente.setTiempoInicioPago(tiempoActual);
                    tiempoActual += cliente.getTiempoPago();
                    cliente.setTiempoSalida(tiempoActual);
                    caja.registrarPago(cliente.getTiempoPago());
                    registrarClienteAtendido(cliente);
                }
            }
        }
    }

    private double generarAleatorioSegundos(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public Caja[] getCajas() {
        return cajas;
    }

    public ColaSimple<Cliente> getClientes() {
        return clientes;
    }

    public void cerrarCajasFinal(double tiempoActual) {
        for (Caja caja : cajas) {
            if (caja.estaAbierta()) {
                caja.cerrar(tiempoActual);
            }
        }
    }

    public String generarResumenEstadisticas() {
        StringBuilder sb = new StringBuilder();
        for (Caja caja : cajas) {
            sb.append("Caja ").append(caja.getId() + 1).append(":\n");
            sb.append("  Clientes atendidos: ").append(caja.getClientesAtendidos()).append("\n");
            sb.append("  Tiempo abierta: ").append(String.format("%.2f", caja.getTiempoAbiertaTotal())).append("\n\n");
        }
        return sb.toString();
    }

    //Solamente se usa para visualizar estadisticas no afecta a la cola ni se modifica.
    public String generarResumenClientes() {
        StringBuilder sb = new StringBuilder();
        for (Cliente c : clientes.obtenerElementos()) {
            sb.append("Cliente ").append(c.getId()).append(":\n");
            sb.append("  Espera: ").append(String.format("%.2f", c.getTiempoEspera())).append(" min\n");
            sb.append("  Pago: ").append(String.format("%.2f", c.getTiempoPago())).append(" min\n\n");
        }
        return sb.toString();
    }

    public void registrarClienteAtendido(Cliente cliente) {
        clientesAtendidos.insertar(cliente);
    }

    public ColaSimple<Cliente> getClientesAtendidos() {
        return clientesAtendidos;
    }
}
