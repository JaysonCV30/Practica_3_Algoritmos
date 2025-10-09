package Logica;

public class Cliente {

    private int id;
    private double tiempoLlegada;
    private double tiempoPago;
    private double tiempoEspera;
    private double tiempoSalida;

    public Cliente(int id, double tiempoLlegada, double tiempoPago) {
        this.id = id;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoPago = tiempoPago;
    }

    public Cliente(int id, double tiempoLlegada) {
        this.id = id;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoPago = Math.random() * 5 + 5; // entre 5 y 10 segundos
    }

    public Cliente(int idLlegada) {
        this.id = idLlegada;
        this.tiempoLlegada = idLlegada;
        this.tiempoPago = Math.random() * 5 + 5; // entre 5 y 10 segundos
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
}
