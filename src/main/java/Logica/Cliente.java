package Logica;

public class Cliente {

    private int id;
    private double tiempoLlegada;
    private double tiempoPago;
    private double tiempoEspera;
    private double tiempoSalida;
    private double tiempoInicioPago;

    public Cliente(int id, double tiempoLlegada, double tiempoPago) {
        this.id = id;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoPago = tiempoPago;
    }

    public Cliente(int idLlegada) {
        this.id = idLlegada;
        this.tiempoLlegada = idLlegada;
        this.tiempoPago = Math.random() * 2 + 3; // entre 3 y 5 segundos
    }

    public int getId() {
        return id;
    }

    public double getTiempoLlegada() {
        return tiempoLlegada;
    }

    public double getTiempoPago() {
        return tiempoPago;
    }

    public double getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(double tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public double getTiempoSalida() {
        return tiempoSalida;
    }

    public void setTiempoSalida(double tiempoSalida) {
        this.tiempoSalida = tiempoSalida;
    }

    public void setTiempoInicioPago(double tiempoInicioPago) {
        this.tiempoInicioPago = tiempoInicioPago;
    }

    public double getTiempoInicioPago() {
        return tiempoInicioPago;
    }

    public double getTiempoEsperaCalculado() {
        return tiempoInicioPago - tiempoLlegada;
    }

    public double getDuracionPago() {
        return tiempoSalida - tiempoInicioPago;
    }
}
