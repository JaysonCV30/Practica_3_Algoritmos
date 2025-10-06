package Logica;

public class Cliente {
    private int id;
    private double tiempoLlegada;
    private double tiempoPago;
    private double tiempoEspera;
    private double tiempoSalida;

    public Cliente(int id, double tiempoLlegada, double tiempoPago, double tiempoEspera, double tiempoSalida) {
        this.id = id;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoPago = tiempoPago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(double tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public double getTiempoPago() {
        return tiempoPago;
    }

    public void setTiempoPago(double tiempoPago) {
        this.tiempoPago = tiempoPago;
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
