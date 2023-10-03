package Models;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ColaPrioridades<T> {
    private Cola<T>[] colasDePrioridades;

    public ColaPrioridades(int numeroPrioridades) {
        // Crear un array de colas basado en el número de prioridades
        colasDePrioridades = new Cola[numeroPrioridades];
        for (int i = 0; i < numeroPrioridades; i++) {
            colasDePrioridades[i] = new Cola<T>(); // Así se crearía una nueva Cola vacía
        }
    }

    public void meter(int prioridad, T elemento) {
        if (prioridad >= 0 && prioridad < colasDePrioridades.length) {
            colasDePrioridades[prioridad].meter(elemento);
        } else {
            System.out.println("Prioridad fuera de rango.");
        }
    }

    public T sacar() {
        // Desencolar desde la cola de mayor prioridad que no esté vacía
        for (int i = 0; i < colasDePrioridades.length; i++) {
            if (!colasDePrioridades[i].estaVacia()) {
                return colasDePrioridades[i].sacar();
            }
        }
        return null; // Retorna null si todas las colas están vacías
    }

    public boolean estaVacia() {
        // Verificar si todas las colas internas están vacías
        for (int i = 0; i < colasDePrioridades.length; i++) {
            if (!colasDePrioridades[i].estaVacia()) {
                return false; // La cola de prioridades no está vacía
            }
        }
        return true; // La cola de prioridades está vacía
    }

    public int getNumeroPrioridades() {
        return colasDePrioridades.length;
    }
}