package Logica;

import GUI.CobroGrafico;

public class CobroLogico implements Runnable {

    private int tiempoActual = 0;
    private int jornada = 300;
    private int incremento = 1;
    private Simulador simulador;
    private CobroGrafico gui;

    public CobroLogico(Simulador simulador, CobroGrafico gui) {
        this.simulador = simulador;
        this.gui = gui;
    }

    @Override
    public void run() {
        while (tiempoActual < jornada) {
            try {
                Thread.sleep(1000 / incremento);
            } catch (InterruptedException e) {
                break;
            }

            tiempoActual++;

            // Llegada de cliente
            if (Math.random() < 0.3) {
                Cliente nuevo = new Cliente(tiempoActual);
                simulador.asignarCliente(nuevo);
            }

            // Cobro en cada caja
            for (Caja caja : simulador.getCajas()) {
                Cliente cliente = caja.getColaClientes().verProximo();
                if (cliente != null && tiempoActual - cliente.getTiempoLlegada() >= cliente.getTiempoPago()) {
                    Cliente atendido = caja.atenderCliente(tiempoActual);
                    gui.mostrarClienteSaliendo(atendido);
                }
            }
            simulador.actualizarCajas();
            gui.actualizarVista(simulador.getCajas());
            gui.actualizarTiempo(tiempoActual);
        }

        System.out.println("Fin de la jornada.");
    }

    public void setIncremento(int i) {
        this.incremento = i;
    }

    public int getIncremento() {
        return incremento;
    }
}