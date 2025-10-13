package Logica;

import GUI.CobroGrafico;

public class CobroLogico implements Runnable {

    private double tiempoActual = 0;
    private int jornada = 600;
    private int incremento = 1;
    private Simulador simulador;
    private CobroGrafico gui;
    private double siguienteLlegada = 0;
    private double tiempoMinimoLlegada = 0.5; // en segundos
    private double tiempoMaximoLlegada = 1; // en segundos
    private double tiempoDesdeUltimaLlegada = 0;

    public CobroLogico(Simulador simulador, CobroGrafico gui) {
        this.simulador = simulador;
        this.gui = gui;
    }

    @Override
    public void run() {
        siguienteLlegada = tiempoMinimoLlegada + Math.random() * (tiempoMaximoLlegada - tiempoMinimoLlegada);
        while (tiempoActual < jornada) {
            try {
                Thread.sleep(1000 / incremento);
            } catch (InterruptedException e) {
                break;
            }

            tiempoActual += 1.0 / incremento;

            // Llegada de cliente
            tiempoDesdeUltimaLlegada += 1.0 / incremento;

            if (tiempoDesdeUltimaLlegada >= siguienteLlegada) {
                Cliente nuevo = new Cliente((int)tiempoActual);
                simulador.asignarCliente(nuevo, (int)tiempoActual);
                tiempoDesdeUltimaLlegada = 0;
                siguienteLlegada = tiempoMinimoLlegada + Math.random() * (tiempoMaximoLlegada - tiempoMinimoLlegada);
            }

            // Cobro en cada caja
            for (Caja caja : simulador.getCajas()) {
                Cliente cliente = caja.getColaClientes().verProximo();
                if (cliente != null && tiempoActual - cliente.getTiempoLlegada() >= cliente.getTiempoPago()) {
                    cliente.setTiempoInicioPago(tiempoActual);
                    Cliente atendido = caja.atenderCliente(tiempoActual);
                    simulador.registrarClienteAtendido(atendido);
                    gui.mostrarClienteSaliendo(atendido);
                }
            }
            simulador.actualizarCajas(tiempoActual);
            gui.actualizarVista(simulador.getCajas(), (int)tiempoActual);
            gui.actualizarTiempo((int)tiempoActual);
        }
        gui.getTablaClientes().setVisible(true);
        gui.mostrarEstadisticasClientes(simulador.getClientesAtendidos());
        System.out.println("Fin de la jornada.");
    }

    public void setIncremento(int i) {
        this.incremento = i;
    }

    public int getIncremento() {
        return incremento;
    }
}
