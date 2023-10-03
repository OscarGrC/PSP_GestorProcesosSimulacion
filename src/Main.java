import Factories.ProcesoFactory;
import Models.Cola;
import Models.ColaPrioridades;
import Models.Proceso;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //variables
        String userReply;
        List<Proceso> listaProcesos = new ArrayList<>();
        int duracionCiclo = 1;
        int tipoSimulacion = 1;

        do {
            System.out.println("||  Simulador  de procesos  ||");
            System.out.println("||__________________________||");
            System.out.println("||    Elige una Opción:     ||");
            System.out.println("||__________________________||");
            System.out.println("|   1.-Añadir proceso        |");
            System.out.println("|   2.-borrar proceso        |");
            System.out.println("|   3.-Factory Procesos      |");
            System.out.println("|   4.-Leer de CSV           |");
            System.out.println("|   5.-Configurar Simulacion |");
            System.out.println("|   6.-Simular               |");
            System.out.println("|   S.-Salir                 |");
            System.out.println("||___________________________||");
            userReply = getUserAnswer();
            switch (userReply) {
                case "1":
                    listaProcesos.add(crearProceso());
                    break;
                case "2":
                    listaProcesos = borrarProceso(listaProcesos);
                    break;
                case "3":
                    int numeroProcesos = 0;
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Introduce el número de procesos:");
                    numeroProcesos = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el búfer de entrada
                    listaProcesos = ProcesoFactory.getInstance().createProcesoRandom(numeroProcesos);
                    break;
                case"4":
                    // Definir la ruta del archivo CSV
                    String rutaArchivo = "src/csv/procesos.csv";
                    listaProcesos = leerProcesosDesdeCSV(rutaArchivo);

                    break;
                case "5":
                    estadoConfiguracion(duracionCiclo, tipoSimulacion);
                    duracionCiclo = configurarCiclos();
                    tipoSimulacion = configurarPlanificacion();
                    break;
                case "6":
                    simulacion(duracionCiclo, tipoSimulacion, listaProcesos);
                    break;

            }
        } while (!userReply.equals("S"));
    }

    public static String getUserAnswer() {
        Scanner scanner = new Scanner(System.in);
        String localUserReply;
        boolean isValidReply = false;
        String regexMenu09S = "^[1-6S]$";
        System.out.println("Seleciona una opción:");
        do {
            localUserReply = scanner.nextLine().trim().toUpperCase();
            if (!localUserReply.matches(regexMenu09S)) {
                System.out.println("Error");
            } else {
                isValidReply = true;
            }
        } while (!isValidReply);
        return localUserReply;
    }

    public static Proceso crearProceso() {
        Scanner scanner = new Scanner(System.in);
        String localUserReply;
        boolean isValidReply = false;
        String regexPrioridad = "^[0-4]";
        String nombre;
        int prioridad;
        int tiempoServicio;
        int tiempoInicio;

        do {
            System.out.println("Nombre del nuevo proceso:");
            localUserReply = scanner.nextLine().trim();
            if (localUserReply.length() < 1) {
                System.out.println("Error el nombre no puede estar vacio");
            } else {
                isValidReply = true;
            }
        } while (!isValidReply);
        nombre = localUserReply;
        isValidReply = false;
        do {
            System.out.println("Selecciona su prioridad (0-4) 0 es maxima prioridad:");
            System.out.println(" ");
            localUserReply = scanner.nextLine().trim();
            if (!localUserReply.matches(regexPrioridad)) {
                System.out.println("Error");
            } else {
                isValidReply = true;
            }
        } while (!isValidReply);
        prioridad = Integer.parseInt(localUserReply);
        isValidReply = false;
        do {
            System.out.println("Selecciona la duracion en ciclos del proceso min 1 max 20 ");
            System.out.println(" ");
            localUserReply = scanner.nextLine().trim();
            if (Integer.parseInt(localUserReply) < 1 || Integer.parseInt(localUserReply) > 20) {
                System.out.println("Error");
            } else {
                isValidReply = true;
            }
        } while (!isValidReply);
        tiempoServicio = Integer.parseInt(localUserReply);
        isValidReply = false;
        do {
            System.out.println("Selecciona en que ciclo llegara el proceso a la cola min 0 max 50 ");
            System.out.println(" ");
            localUserReply = scanner.nextLine().trim();
            if (Integer.parseInt(localUserReply) > 50) {
                System.out.println("Error");
            } else {
                isValidReply = true;
            }
        } while (!isValidReply);
        tiempoInicio = Integer.parseInt(localUserReply);
        return new Proceso(nombre, prioridad, tiempoServicio, tiempoInicio);
    }

    public static List<Proceso> borrarProceso(List<Proceso> listaProcesos) {
        Scanner scanner = new Scanner(System.in);
        String localUserReply;
        int posicionBorrar = 0;
        boolean seguroBorrado = false;
        if (listaProcesos.size() == 0) {
            System.out.println("Lo siento no existen procesos");
        } else {
            for (int i = 0; i < listaProcesos.size(); i++) {
                System.out.println(listaProcesos.get(i).toString());
            }
            System.out.println("");
            System.out.println("Selecciona el nombre del proceso para borrar");
            localUserReply = scanner.nextLine().trim();
            for (int i = 0; i < listaProcesos.size(); i++) {
                if (listaProcesos.get(i).getNombre().equals(localUserReply)) {
                    posicionBorrar = i;
                    seguroBorrado = true;
                }
            }
            if (seguroBorrado) {
                listaProcesos.remove(posicionBorrar);
                System.out.println("Se borro el Proceso");
            }
            System.out.println(" ");
        }
        return listaProcesos;
    }

    public static void estadoConfiguracion(int duracionCiclos, int tipoSimulacion) {
        System.out.println("");
        System.out.println("El ciclo de la simulacion esta fijado en " + duracionCiclos);
        switch (tipoSimulacion) {
            case 1:
                System.out.println("El tipo de Planificacion es FIFO");
                break;
            case 2:
                System.out.println("El tipo de Planificacion es SJF");
                break;
            case 3:
                System.out.println("El tipo de Planificacion es Round Robin");
                break;
        }
        System.out.println("");
    }

    public static int configurarCiclos() {
        Scanner scanner = new Scanner(System.in);
        String userLocalReply;
        boolean userValidReply = false;
        do {
            System.out.println("¿Cuanto quieres que dure un ciclo?  min 1 max 9");
            System.out.println(" (El tiempo que el programa se apropia del uso del procesador) ");
            userLocalReply = scanner.nextLine().trim();
            if (Integer.parseInt(userLocalReply) > 0 && Integer.parseInt(userLocalReply) < 10) {
                userValidReply = true;
            }
        } while (!userValidReply);
        return Integer.parseInt(userLocalReply);
    }

    public static int configurarPlanificacion() {
        Scanner scanner = new Scanner(System.in);
        String userLocalReply;
        boolean userValidReply = false;
        do {
            System.out.println("¿Que tipo de planificacion quieres usar?");
            System.out.println(" 1.- FIFO --El primer proceso en entrar es el primero en salir");
            System.out.println(" 2.- SJF -- Los procesos mas pequeños son los primeros en salir");
            System.out.println(" 3.- Round Robin -- Los procesos tienen prioridades los más prioritarios son los primeros en salir");
            userLocalReply = scanner.nextLine().trim();
            if (Integer.parseInt(userLocalReply) > 0 && Integer.parseInt(userLocalReply) < 4) {
                userValidReply = true;
            }
        } while (!userValidReply);
        return Integer.parseInt(userLocalReply);
    }

    public static void simulacion(int duracionCiclo, int tipoSimulacion, List<Proceso> listaProcesos) {
        int tiempoSimulacion = 0;
        List<Proceso> listaProcesosLocal = new ArrayList<>();
        switch (tipoSimulacion) {
            case 1:
                // FIFO
                Cola<Proceso> cola = ordenacionBurbujaTiempoInicio(listaProcesos);
                while (cola.tamaño()>0) {
                    Proceso procesoActivo = cola.peek();
                    if (procesoActivo.getTiempoInicio() <= tiempoSimulacion) {
                        procesoActivo.setTiempoEjecucion(procesoActivo.getTiempoEjecucion() + 1);
                        System.out.println("Tiempo simulacion " + tiempoSimulacion + " ----- EJECUTANDO PROCESO ---- " + procesoActivo.getNombre());
                        System.out.println("Duracion del proceso " + procesoActivo.getTiempoServicio() + " Ciclos --Lleva " + procesoActivo.getTiempoEjecucion() + " ciclos");
                        tiempoSimulacion += 1;
                        if (procesoActivo.getTiempoServicio() == procesoActivo.getTiempoEjecucion()) {
                            System.out.println("--El proceso termino--");
                            cola.sacar();
                        }
                    } else {
                        System.out.println("Tiempo simulacion " + tiempoSimulacion + " ----- SIN PROCESOS LISTOS");
                        tiempoSimulacion += 1;
                    }
                }
                System.out.println("----FIN DE TODOS LOS PROCESOS LA COLA ESTA VACIA LA SIMULACION DURO " + tiempoSimulacion + " ciclos");
                break;
            case 2:
                // SJF
                Cola<Proceso> colaSJF = ordenacionBurbujaTiempoInicio(listaProcesos);
                Cola<Proceso> listaProcesosActivos = new Cola<Proceso>();
                while (!colaSJF.estaVacia() || !listaProcesosActivos.estaVacia()) {
                    // si colaSJF (pendientes) tiene que meter un proceso a la lista de Activos
                    if (!colaSJF.estaVacia() && colaSJF.peek().getTiempoInicio() <= tiempoSimulacion) {
                        listaProcesosActivos.meter(colaSJF.peek());
                        System.out.println("Entra a lista procesos " + colaSJF.peek().getNombre());
                        colaSJF.sacar();
                        // ordenamos la cola de procesosActivos
                        listaProcesosActivos =ordenacionBurbujaDuracion(listaProcesosActivos);
                    }
                    if (!listaProcesosActivos.estaVacia()) {
                        Proceso proximoProceso = listaProcesosActivos.sacar();
                        proximoProceso.setTiempoEjecucion(proximoProceso.getTiempoEjecucion()+1);
                        System.out.println("Tiempo simulacion " + tiempoSimulacion + " ----- EJECUTANDO PROCESO ---- " + proximoProceso.getNombre());
                        System.out.println("Duracion del proceso " + proximoProceso.getTiempoServicio() +
                                " Ciclos --Lleva " + proximoProceso.getTiempoEjecucion() + " ciclos");
                        tiempoSimulacion += 1;

                        //comprobamos si termino o tenemos que meterlo de nuevo
                        if (proximoProceso.getTiempoServicio() == proximoProceso.getTiempoEjecucion()) {
                            System.out.println("---PROCESO TERMINADO---");
                        }else{
                            listaProcesosActivos.meter(proximoProceso);
                            listaProcesosActivos=ordenacionBurbujaDuracion(listaProcesosActivos);
                        }
                    } else {
                        System.out.println("SIN PROCESOS ACTIVOS ");
                        tiempoSimulacion += 1;
                    }
                }
                System.out.println("----FIN DE TODOS LOS PROCESOS LA COLA ESTA VACIA LA SIMULACION DURO " + tiempoSimulacion + " ciclos");
                break;
            case 3:
                //Round Robin
                Cola<Proceso> colaPendientes = ordenacionBurbujaTiempoInicio(listaProcesos);
                //aqui podemos pedir al usuario que nos diga cuantas prioridades quiere o que detecte en la colaPendientes
                ColaPrioridades<Proceso> colaEjecucion = new ColaPrioridades<Proceso>(5);
                Proceso proximoProceso ;
                //comenzamos el bucle hasta vaciar las 2 colas
                while (!colaPendientes.estaVacia() || !colaEjecucion.estaVacia()) {
                    // primero revisar si tiene que entrar algun proceso
                    if (!colaPendientes.estaVacia() && colaPendientes.peek().getTiempoInicio() <= tiempoSimulacion) {
                        colaEjecucion.meter(colaPendientes.peek().getPrioridad(),colaPendientes.peek());
                        System.out.println("Entra a lista procesos " + colaPendientes.peek().getNombre());
                        colaPendientes.sacar();
                    }
                    // ahora ejecutamos proceso
                    if(!colaEjecucion.estaVacia()){
                        proximoProceso = colaEjecucion.sacar();
                        //procesamos tantas veces como dure el cuanto
                        for(int i=0; i < duracionCiclo;i++){
                            proximoProceso.setTiempoEjecucion(proximoProceso.getTiempoEjecucion()+1);
                            System.out.println("Tiempo simulacion " + tiempoSimulacion + " ----- EJECUTANDO PROCESO ---- " + proximoProceso.getNombre());
                            System.out.println("Duracion del proceso " + proximoProceso.getTiempoServicio() +
                                    " Ciclos --Lleva " + proximoProceso.getTiempoEjecucion() + " ciclos");
                            tiempoSimulacion += 1;
                            //paramos bucle si el proceso termino
                            if (proximoProceso.getTiempoServicio()==proximoProceso.getTiempoEjecucion()){
                                break;
                            }
                        }
                        // vemos que hacer con el proceso
                        if (proximoProceso.getTiempoServicio()==proximoProceso.getTiempoEjecucion()){
                            System.out.println("---PROCESO TERMINADO---");
                        }else {
                            colaEjecucion.meter(proximoProceso.getPrioridad(),proximoProceso);
                        }
                    }else{
                        System.out.println("SIN PROCESOS ACTIVOS ");
                        tiempoSimulacion += 1;
                    }
                }

        }
    }
        public static Cola<Proceso> ordenacionBurbujaTiempoInicio(List<Proceso> lista) {
            int n = lista.size();
            boolean intercambiado;
            Cola<Proceso> cola = new Cola<Proceso>();
            do {
                intercambiado = false;
                for (int i = 0; i < n - 1; i++) {
                    if (lista.get(i).getTiempoInicio() > lista.get(i + 1).getTiempoInicio()) {
                        Proceso temp = lista.get(i);
                        lista.set(i, lista.get(i + 1));
                        lista.set(i + 1, temp);
                        intercambiado = true;
                    }
                }
            } while (intercambiado);
            System.out.println("La cola queda asi");
            for (int i = 0; i < lista.size(); i++) {
                cola.meter(lista.get(i));
                System.out.println("Posicion:" + i + " " + lista.get(i).toString());
            }
            System.out.println(" ");
            return cola;
        }
    public static Cola<Proceso> ordenacionBurbujaDuracion(Cola<Proceso> listaEntrada) {
        List<Proceso> lista =new  LinkedList<Proceso>();
        while (!listaEntrada.estaVacia()){
            lista.add(listaEntrada.peek());
            listaEntrada.sacar();
        }
        int n = lista.size();
        boolean intercambiado;
        Cola<Proceso> cola = new Cola<Proceso>();
        do {
            intercambiado = false;
            for (int i = 0; i < n - 1; i++) {
                if ((lista.get(i).getTiempoServicio()-lista.get(i).getTiempoEjecucion()) >
                    (lista.get(i+1).getTiempoServicio()-lista.get(i).getTiempoEjecucion()) ) {
                    Proceso temp = lista.get(i);
                    lista.set(i, lista.get(i + 1));
                    lista.set(i + 1, temp);
                    intercambiado = true;
                }
            }
        } while (intercambiado);
        System.out.println("La cola queda asi");
        for (int i = 0; i < lista.size(); i++) {
            cola.meter(lista.get(i));
            System.out.println("Posicion:" + i + " " + lista.get(i).toString());
        }
        System.out.println(" ");
        return cola;
    }


    public static List<Proceso> leerProcesosDesdeCSV(String nombreArchivo) {
        List<Proceso> procesos =  new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            // Leer la primera línea que generalmente contiene los encabezados
            br.readLine(); // Esto omite la primera línea si contiene encabezados

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    String nombre = datos[0];
                    int prioridad = Integer.parseInt(datos[1]);
                    int tiempoServicio = Integer.parseInt(datos[2]);
                    int tiempoInicio = Integer.parseInt(datos[3]);


                    Proceso proceso = new Proceso(nombre, prioridad, tiempoServicio, tiempoInicio);
                    procesos.add(proceso);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return procesos;
    }
    }