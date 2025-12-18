package methods;

import interfaces.Grafo;
import models.Aresta;
import models.Vertice;

import java.util.*;

public class CaminhoMinimo {
    public List<Aresta> calcularCaminhoMinimo(Grafo grafo, Vertice origem, Vertice destino) throws Exception {
        Map<Vertice, Double> distancias = new HashMap<>();
        Map<Vertice, Vertice> predecessores = new HashMap<>();
        PriorityQueue<Vertice> fila = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));

        for (Vertice vertice : grafo.vertices()) {
            distancias.put(vertice, Double.MAX_VALUE);
            predecessores.put(vertice, null);
        }
        distancias.put(origem, 0.0);
        fila.add(origem);

        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();

            for (Vertice adjacente : grafo.adjacentesDe(atual)) {
                double peso = grafo.arestasEntre(atual, adjacente).get(0).peso();
                double novaDistancia = distancias.get(atual) + peso;

                if (novaDistancia < distancias.get(adjacente)) {
                    distancias.put(adjacente, novaDistancia);
                    predecessores.put(adjacente, atual);
                    fila.add(adjacente);
                }
            }
        }

        List<Aresta> caminhoMinimo = new ArrayList<>();
        Vertice atual = destino;

        while (predecessores.get(atual) != null) {
            Vertice anterior = predecessores.get(atual);
            Aresta aresta = grafo.arestasEntre(anterior, atual).get(0);
            caminhoMinimo.add(0, aresta);
            atual = anterior;
        }

        if (distancias.get(destino) == Double.MAX_VALUE) {
            throw new Exception("Não há caminho entre os vértices informados.");
        }

        return caminhoMinimo;
    }
}

