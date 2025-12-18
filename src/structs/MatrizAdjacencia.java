/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structs;

import java.util.*;

import models.*;
import interfaces.*;

/**
 *
 * @author Douglas
 */
public class MatrizAdjacencia implements Grafo {
    private double[][] matriz;
    private ArrayList<Vertice> vertices;
    private int numArestas;

    public MatrizAdjacencia(int numeroDeVertices){
        matriz = new double[numeroDeVertices][numeroDeVertices];
        vertices = new ArrayList<>();

        for (int i = 0; i < numeroDeVertices; i++){
            vertices.add(new Vertice(i));
        }
        numArestas = 0;
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception {
        adicionarAresta(origem, destino, 1.0);
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception {
        if (origem.getId() >= matriz.length || destino.getId() >= matriz.length){
            throw new Exception("Vertice não existe!");
        }
        if (matriz[origem.getId()][destino.getId()] == 0) {
            numArestas++;
        }
        matriz[origem.getId()][destino.getId()] = peso;
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino) {
        return matriz[origem.getId()][destino.getId()] != 0;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception {
        int grau = 0;
        for (int i = 0; i < matriz.length; i++){
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
        return numArestas;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception {
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        for (int i = 0; i < matriz.length; i++){
            if (matriz[vertice.getId()][i] != 0){
                adjacentes.add(vertices.get(i));
            }
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception {
        if (origem.getId() >= matriz.length || destino.getId() >= matriz.length){
            throw new Exception("Vertice não existe!");
        }
        matriz[origem.getId()][destino.getId()] = peso;
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception {
        ArrayList<Aresta> arestas = new ArrayList<>();
        if (existeAresta(origem, destino)) {
            arestas.add(new Aresta(origem, destino, matriz[origem.getId()][destino.getId()]));
        }
        return arestas;
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
        if (origem.getId() >= matriz.length || destino.getId() >= matriz.length) {
            throw new Exception("Vértice inválido.");
        }
        return matriz[origem.getId()][destino.getId()];
    }

}
