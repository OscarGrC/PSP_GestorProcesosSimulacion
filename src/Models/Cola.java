package Models;


import java.util.LinkedList;

public class Cola<T> {
    private java.util.LinkedList<T> elementos = new LinkedList<T>();

    // Añadir un elemento a la cola
    public void meter(T elemento) {
        elementos.addLast(elemento);
    }

    // Extraer el primer elemento de la cola
    public T sacar() {
        //Si la lista esta vacia
        if (elementos.isEmpty()) {
            System.out.println("Lista Vacia");
            return null;
            //si existen elementos
        }else{
            T proximoElemento = elementos.peek();
            elementos.removeFirst();
            return  proximoElemento;
        }
    }

    // Verificar si la cola está vacía
    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    // Obtener el tamaño de la cola
    public int tamaño() {
        return elementos.size();
    }

    // Obtener el primer elemento de la cola sin extraerlo
    public T peek() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return elementos.getFirst();
    }


}
