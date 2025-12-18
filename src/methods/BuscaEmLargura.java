package methods;

import java.util.*;

import interfaces.*;
import models.*;

public class BuscaEmLargura {
    private Map<Vertice, Integer> distancia;
    private Map<Vertice, Vertice> predecessor;
    private Map<Vertice, String> cores;
    private List<Aresta> arestasArvore;

    public BuscaEmLargura() {
        this.distancia = new HashMap<>();
        this.predecessor = new HashMap<>();
        this.cores = new HashMap<>();
        this.arestasArvore = new ArrayList<>();
    }

    public void executar(Grafo grafo, Vertice inicial) throws Exception {
        for (Vertice vertice : grafo.vertices()){
            cores.put(vertice, "BRANCO");
            distancia.put(vertice, Integer.MAX_VALUE);
            predecessor.put(vertice, null);
        }

        cores.put(inicial, "CINZA");
        distancia.put(inicial, 0);

        Queue<Vertice> fila = new LinkedList<>();
        fila.add(inicial);

        while(!fila.isEmpty()){
            Vertice u = fila.poll();

            for (Vertice v : grafo.adjacentesDe(u)) {
                if (cores.get(v).equals("BRANCO")) {
                    cores.put(v, "CINZA");
                    distancia.put(v, distancia.get(u) + 1);
                    predecessor.put(v, u);
                    arestasArvore.add(new Aresta(u, v));
                    fila.add(v);
                }
            }

            cores.put(u, "PRETO");
        }
    }

    public Map<Vertice, Integer> getDistancias() {
        return distancia;
    }
    public Map<Vertice, Vertice> getPredecessores() {
        return predecessor;
    }
    public List<Aresta> getArestasArvore() {
        return arestasArvore;
    }
}

