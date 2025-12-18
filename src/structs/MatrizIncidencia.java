package structs;

import java.util.*;

import models.*;
import interfaces.*;

public class MatrizIncidencia implements Grafo {
    private double[][] matriz;
    private ArrayList<Vertice> vertices;
    private ArrayList<Aresta> arestas;

    public MatrizIncidencia(int numeroDeVertices){
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
        for (int i = 0; i < numeroDeVertices; i++){
            vertices.add(new Vertice(i));
        }
    }

    private void expandirMatriz() {
        if (matriz == null){
            matriz = new double[vertices.size()][1];
        } else {
            double[][] novaMatriz = new double[vertices.size()][arestas.size() + 1];
            for (int i = 0; i < matriz.length; i++){
                System.arraycopy(matriz[i], 0, novaMatriz[i], 0, matriz[i].length);
            }
            matriz = novaMatriz;
        }
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
        adicionarAresta(origem, destino, 1.0);
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
        if (origem.getId() >= vertices.size() || destino.getId() >= vertices.size()){
            throw new Exception("Vertice não existe!");
        }
        expandirMatriz();
        Aresta aresta = new Aresta(origem, destino, peso);
        arestas.add(aresta);
        matriz[origem.getId()][arestas.size() - 1] = peso;
        matriz[destino.getId()][arestas.size() - 1] = -1; // Para grafo direcionado
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino) {
        for (int i = 0; i < arestas.size(); i++){
            if (matriz[origem.getId()][i] > 0 && matriz[destino.getId()][i] < 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception {
        int grau = 0;
        for (int i = 0; i < arestas.size(); i++){
            if (matriz[vertice.getId()][i] != 0){
                grau++;
            }
        }
        return grau;
    }

    @Override
    public int numeroDeVertices() {
        return vertices.size();
    }

    @Override
    public int numeroDeArestas() {
        return arestas.size();
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        for (int i = 0; i < arestas.size(); i++){
            if (matriz[vertice.getId()][i] > 0){
                for (int j = 0; j < vertices.size(); j++){
                    if (matriz[j][i] < 0){
                        adjacentes.add(vertices.get(j));
                    }
                }
            }
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
        for (int i = 0; i < arestas.size(); i++){
            if (matriz[origem.getId()][i] > 0 && matriz[destino.getId()][i] < 0){
                matriz[origem.getId()][i] = peso;
                matriz[destino.getId()][i] = -peso;
                arestas.get(i).setarPeso(peso);
                return;
            }
        }
        throw new Exception("Aresta não existe!");
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
        ArrayList<Aresta> resultado = new ArrayList<>();
        for (int i = 0; i < arestas.size(); i++){
            if (matriz[origem.getId()][i] > 0 && matriz[destino.getId()][i] < 0){
                resultado.add(arestas.get(i));
            }
        }
        return resultado;
    }

    @Override
    public ArrayList<Vertice> vertices() {
        return vertices;
    }

    @Override
    public void adicionarVertice(Vertice vertice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'adicionarVertice'");
    }

    @Override
    public double pesoEntre(Vertice origem, Vertice destino) throws Exception {
        for (int i = 0; i < arestas.size(); i++) {
            if (matriz[origem.getId()][i] > 0 && matriz[destino.getId()][i] < 0) {
                return arestas.get(i).peso();
            }
        }
        throw new Exception("Aresta não encontrada");
    }

}
