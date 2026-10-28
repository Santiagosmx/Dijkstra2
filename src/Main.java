public class Main {

    public static void main(String[] args) {
        String[] vertices = {"A", "B", "C", "D", "E", "F"};
        Dijkstra grafo = new Dijkstra(vertices);

        grafo.agregarArista(0, 1, 2);  // A - B: 2
        grafo.agregarArista(0, 2, 4);  // A - C: 4
        grafo.agregarArista(1, 2, 1);  // B - C: 1
        grafo.agregarArista(1, 3, 7);  // B - D: 7
        grafo.agregarArista(2, 4, 3); // C - E: 3
        grafo.agregarArista(3, 4, 2);  // D - E: 2
        grafo.agregarArista(3, 5, 1);  // D - F: 6
        grafo.agregarArista(4, 5, 5);  // E - F: 3

        grafo.ejecutar(0); // Calcular desde A.
    }
}
