package methods;

import java.util.*;
import interfaces.*;
import models.*;

public class ArvoreGeradoraMinima {

    public Collection<Aresta> calcularAGM(Grafo grafo, Vertice inicial) throws Exception {
        Set<Vertice> visitados = new HashSet<>();
        List<Aresta> arestasAGM = new ArrayList<>();
        PriorityQueue<Aresta> filaPrioridade = new PriorityQueue<>(Comparator.comparingDouble(Aresta::peso));

        visitados.add(inicial);
        for (Vertice adj : grafo.adjacentesDe(inicial)) {
            filaPrioridade.add(new Aresta(inicial, adj, grafo.pesoEntre(inicial, adj)));
        }

        while (!filaPrioridade.isEmpty() && arestasAGM.size() < grafo.numeroDeVertices() - 1) {
            Aresta menorAresta = filaPrioridade.poll();
            Vertice destino = menorAresta.destino();

            if (visitados.contains(destino)) {
                continue;
            }

            arestasAGM.add(menorAresta);
            visitados.add(destino);

            for (Vertice adj : grafo.adjacentesDe(destino)) {
                if (!visitados.contains(adj)) {
                    filaPrioridade.add(new Aresta(destino, adj, grafo.pesoEntre(destino, adj)));
                }
            }
        }

        if (arestasAGM.size() != grafo.numeroDeVertices() - 1) {
            throw new Exception("O grafo não é conexo, não é possível calcular a AGM.");
        }

        return arestasAGM;
    }
}

