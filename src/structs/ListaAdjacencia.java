package structs;

import java.util.*;

import models.*;
import interfaces.*;

public class ListaAdjacencia implements Grafo {
    private Map<Vertice, ArrayList<Aresta>> listaAdjacencia;
    private int numAresta;

    public ListaAdjacencia() {
        listaAdjacencia = new HashMap<>();
        numAresta = 0;
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
        adicionarAresta(origem, destino, 1.0);
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
        if (!listaAdjacencia.containsKey(origem)){
            listaAdjacencia.put(origem, new ArrayList<>());
        }
        listaAdjacencia.get(origem).add(new Aresta(origem, destino, peso));
        numAresta++;
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino) {
        if (!listaAdjacencia.containsKey(origem)){
            return false;
        }
        for (Aresta aresta : listaAdjacencia.get(origem)){
            if (aresta.destino().equals(destino)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception {
        if (!listaAdjacencia.containsKey(vertice)){
            return 0;
        }
        return listaAdjacencia.get(vertice).size();
    }

    @Override
    public int numeroDeVertices() {
        return listaAdjacencia.size();
    }

    @Override
    public int numeroDeArestas() {
        return numAresta;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        if (listaAdjacencia.containsKey(vertice)){
            for (Aresta aresta : listaAdjacencia.get(vertice)){
                adjacentes.add(aresta.destino());
            }
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
        if (!listaAdjacencia.containsKey(origem)){
            throw new Exception("Origem inexistente!");
        }
        for (Aresta aresta : listaAdjacencia.get(origem)){
            if (aresta.destino().equals(destino)){
                aresta.setarPeso(peso);
                return;
            }
        }
        throw new Exception("Aresta não encontrada!");
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
        ArrayList<Aresta> resultado = new ArrayList<>();
        if (!listaAdjacencia.containsKey(origem)){
            return resultado;
        }
        for (Aresta aresta : listaAdjacencia.get(origem)){
            if (aresta.destino().equals(destino)){
                resultado.add(aresta);
            }
        }
        return resultado;
    }

    @Override
    public ArrayList<Vertice> vertices() {
        return new ArrayList<>(listaAdjacencia.keySet());
    }

    public void adicionarVertice(Vertice vertice) {
        listaAdjacencia.putIfAbsent(vertice, new ArrayList<>());
    }

    @Override
    public double pesoEntre(Vertice origem, Vertice destino) throws Exception {
        if (!listaAdjacencia.containsKey(origem)) {
            throw new Exception("Origem inexistente!");
        }
        for (Aresta aresta : listaAdjacencia.get(origem)) {
            if (aresta.destino().equals(destino)) {
                return aresta.peso();
            }
        }
        throw new Exception("Aresta não encontrada!");
    }
}
