import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Dijkstra{

    private String[] vertices; // Lista con cada uno de los nombres de los vertices
    private List<List<int[]>> grafo; // Una lista que contiene un arreglo de enteros contenida en otra lista

    // Constructor que se ejecuta cada vez que se crea un objeto
    public Dijkstra(String[] vertices) {
        this.vertices = vertices;
        grafo = new ArrayList<>();

        // Se agrega una nueva lista por cada índice a la lista creada anteriormente
        for (int i = 0; i < vertices.length; i++) {
            grafo.add(new ArrayList<>());
        }
    }

    //Metodo que crea una conexión al grafo
    public void agregarArista(int origen, int destino, int peso) {
        if (peso < 0) {
            throw new IllegalArgumentException(
                    "Dijkstra no admite pesos negativos."
            );
        }
        // (([]))
        grafo.get(origen).add(new int[]{destino, peso}); // Obtiene la lista del origen, crea un arreglo con los números destino y peso para agregarlo a esa lista
        grafo.get(destino).add(new int[]{origen, peso});
    }

    // Metodo para el cálculo de las distancias mínimas
    public void ejecutar(int origen) {
        int cantidad = vertices.length;
        int infinito = Integer.MAX_VALUE;

        int[] distancia = new int[cantidad]; // Guarda la menor distancia conocida para cada vértice
        boolean[] visitado = new boolean[cantidad]; // Indica si un vértice ya ha sido procesado definitivamente (empieza con todos los valores como falsos)
        int[] anterior = new int[cantidad]; // Guarda el vértice anterior en el camino hacia el próximo destino

        for (int i = 0; i < cantidad; i++) {
            distancia[i] = infinito;
            anterior[i] = -1;
        }

        distancia[origen] = 0;

        PriorityQueue<int[]> cola = new PriorityQueue<>(
                Comparator.comparingInt(a -> a[1]) // El metodo compareInt ya tiene establecido que orden de prioridad es del menor a mayor
        );

        cola.add(new int[]{origen, 0});

        while (!cola.isEmpty()) {
            int[] elemento = cola.poll(); // Devuelve el arreglo completo de cada elemento de la cola
            int actual = elemento[0]; // Se toma el índice

            // Si ya hemos visitado el nodo que continue con la siguiente iteración
            if (visitado[actual]) {
                continue;
            }

            visitado[actual] = true;

            // Por cada uno de los vecinos de el elemento actual se ejecuta el código
            for (int[] arista : grafo.get(actual)) {
                int vecino = arista[0];
                int peso = arista[1];

                // Relajación
                if (!visitado[vecino]) {
                    int nuevaDistancia = distancia[actual] + peso;

                    if (nuevaDistancia < distancia[vecino]) {
                        distancia[vecino] = nuevaDistancia;
                        anterior[vecino] = actual;

                        cola.add(new int[]{vecino, nuevaDistancia});
                    }
                }
            }
        }

        System.out.println("Distancias desde " + vertices[origen] + ":");

        for (int i = 0; i < cantidad; i++) {
            System.out.print(vertices[i] + ": ");

            if (distancia[i] == infinito) {
                System.out.println("No existe camino");
            } else {
                System.out.print("Distancia = " + distancia[i]);
                System.out.print(", Camino = ");
                imprimirCamino(i, anterior);
                System.out.println();
            }
        }
    }

    private void imprimirCamino(int destino, int[] anterior) {
        int[] camino = new int[vertices.length]; // Guarda los índices del recorrido
        int cantidad = 0; // Cuenta cuantos vértices hemos guardado
        int actual = destino; // Comienza en el destino

        // El ciclo empieza en destino y va retrocediendo hasta llegar al origen
        while (actual != -1) {
            camino[cantidad] = actual;
            cantidad++;
            actual = anterior[actual];
        }

        for (int i = cantidad - 1; i >= 0; i--) {
            System.out.print(vertices[camino[i]]);

            if (i > 0) {
                System.out.print(" -> ");
            }
        }
    }
}
/*
V = Cantidad de vertices en el grafo
E = Cantidad de aristas o conexiones del grafo

Complejidad temporal: Mide como aumenta el tiempo de ejecución según el tamaño del grafo

 O(( V + E ) logV)

 Recorremos cada uno de los Vertices O(V) en:
    for (int i = 0; i < cantidad; i++) {
            distancia[i] = infinito;
            anterior[i] = -1;
        }
La operación poll extrae con la menor distancia, extraer elementos de una cola de prioridad toma O(logV)
Sin embargo, para nuestro algoritmo se tienen entradas duplicadas y por lo tanto hay una complejidad
de (O((V+E)log V))
while (!cola.isEmpty()) {
    int[] elemento = cola.poll();
    int actual = elemento[0];

Recorrer las conexiones
El algoritmo revisa las conexiones de los vértices que procesa, para cada arista esto se examina
un número constante de dos veces O(E)
for (int[] arista : grafo.get(actual)) {
    int vecino = arista[0];
    int peso = arista[1];

    Al combinar estas operaciones, obtenemos la complejidad temporal habitual:
    O((V+E)logV)
    O(V)+O(E)+O((V+E)log V)
 */

