package Models;

public class Proceso {
    private String nombre;
    private int prioridad;
    private int tiempoServicio;
    private int tiempoInicio;
    private int tiempoEjecucion;



    public Proceso(String nombre, int prioridad, int tiempoServicio, int tiempoInicio) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.tiempoServicio = tiempoServicio;
        this.tiempoInicio = tiempoInicio;
        this.tiempoEjecucion = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getTiempoServicio() {
        return tiempoServicio;
    }

    public int getTiempoInicio() {
        return tiempoInicio;
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void setTiempoServicio(int tiempoServicio) {
        this.tiempoServicio = tiempoServicio;
    }

    public void setTiempoInicio(int tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public void setTiempoEjecucion(int tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    @Override
    public String toString() {
        return "Nombre:" + nombre + " Prioridad:" + prioridad + " T.Servicio:" + tiempoServicio + " T.Inicio:" + tiempoInicio;
    }
}