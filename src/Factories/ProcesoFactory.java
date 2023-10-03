package Factories;

import Models.Proceso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcesoFactory {

    private int contador = 1;

    // Patrón Singleton
    // Donde almacenamos la instancia única
    private static ProcesoFactory instance = null;

    private ProcesoFactory() {
        // Constructor privado para evitar la creación de instancias
    }

    public static ProcesoFactory getInstance() {
        if (instance == null) {
            instance = new ProcesoFactory();
        }
        return instance;
    }

    public List<Proceso> createProcesoRandom(int numeroProcesos) {
        List<Proceso> listaProcesos = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numeroProcesos; i++) {
            String nombre = "P" + contador;
            int prioridad = random.nextInt(5);
            int tiempoServicio = random.nextInt(15)+1 ; // Entre 1 y 15
            int tiempoInicio = random.nextInt(10); // Entre 0 y 9

            contador++;

            listaProcesos.add(new Proceso(nombre, prioridad, tiempoServicio, tiempoInicio));
        }

        return listaProcesos;
    }
}