package util;

import models.*;
import methods.*;
import structs.*;
import interfaces.*;

import java.io.*;
import java.util.*;

public class AlgoritmosImplementados implements AlgoritmosEmGrafos{
    @Override
    public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int numVertices = Integer.parseInt(br.readLine().trim());
            Grafo grafo;
    
            switch (t) {
                case MATRIZ_DE_ADJACENCIA:
                    grafo = new MatrizAdjacencia(numVertices);
                    break;
                case MATRIZ_DE_INCIDENCIA:
                    grafo = new MatrizIncidencia(numVertices);
                    break;
                case LISTA_DE_ADJACENCIA:
                    grafo = new ListaAdjacencia();
                    for (int i = 0; i < numVertices; i++) {
                        grafo.adicionarVertice(new Vertice(i));
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de representação desconhecido!");
            }
    
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" ");
                int origem = Integer.parseInt(partes[0]);
                String[] arestas = partes[1].split(";");
    
                for (String aresta : arestas) {
                    if (!aresta.isEmpty()) {
                        String[] destinoPeso = aresta.split("-");
                        int destino = Integer.parseInt(destinoPeso[0]);
                        double peso = Double.parseDouble(destinoPeso[1]);
                        grafo.adicionarAresta(new Vertice(origem), new Vertice(destino), peso);
                    }
                }
            }
            return grafo;
        }
    }

    @Override
    public Collection<Aresta> buscaEmLargura(Grafo g) {
        try {
            BuscaEmLargura bfs = new BuscaEmLargura();
            bfs.executar(g, g.vertices().get(0));
            return  bfs.getArestasArvore();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Collection<Aresta> buscaEmProfundidade(Grafo g) {
        try {
            BuscaEmProfundidade dfs = new BuscaEmProfundidade();
            dfs.executar(g, g.vertices().get(0));
            return dfs.getAresta_arvore();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public ArrayList<Aresta> menorCaminho(Grafo g, Vertice origem, Vertice destino) throws Exception {
        return null;
    }

    @Override
    public boolean existeCiclo(Grafo g) {
        return false;
    }

    @Override
    public Collection<Aresta> agmUsandoKruskall(Grafo g, Vertice inicial) {
        try {
            if (inicial == null || !g.vertices().contains(inicial)) {
                throw new IllegalArgumentException("Vértice inicial inválido ou não encontrado no grafo.");
            }
    
            ArvoreGeradoraMinima agm = new ArvoreGeradoraMinima();
            return agm.calcularAGM(g, inicial);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) throws Exception {
        return 0;
    }

    @Override
    public boolean ehArvoreGeradora(Grafo g, Collection<Aresta> arestas) {
        return false;
    }

    @Override
    public ArrayList<Aresta> caminhoMaisCurto(Grafo g, Vertice origem, Vertice destino) throws Exception {
        CaminhoMinimo cm = new CaminhoMinimo();
        return new ArrayList<>(cm.calcularCaminhoMinimo(g, origem, destino));
    }
    

    @Override
    public double custoDoCaminho(Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino) throws Exception {
        return 0;
    }

    @Override
    public boolean ehCaminho(ArrayList<Aresta> arestas, Vertice origem, Vertice destino) {
        return false;
    }

    @Override
    public Collection<Aresta> arestasDeArvore(Grafo g) {
        return buscaEmProfundidade(g);
    }

    @Override
    public Collection<Aresta> arestasDeRetorno(Grafo g) {
        try {
            BuscaEmProfundidade dfs = new BuscaEmProfundidade();
            dfs.executar(g, g.vertices().get(0));
            return dfs.getAresta_retorno();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Collection<Aresta> arestasDeAvanco(Grafo g) {
        try {
            BuscaEmProfundidade dfs = new BuscaEmProfundidade();
            dfs.executar(g, g.vertices().get(0));
            return dfs.getAresta_avanco();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Collection<Aresta> arestasDeCruzamento(Grafo g) {
        try {
            BuscaEmProfundidade dfs = new BuscaEmProfundidade();
            dfs.executar(g, g.vertices().get(0));
            return dfs.getAresta_cruzamento();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    //Métodos extras para busca em largura
    public Map<Vertice, Integer> distanciasEmLargura(Grafo g, Vertice inicial) {
        try {
            BuscaEmLargura bfs = new BuscaEmLargura();
            bfs.executar(g, inicial);
            return bfs.getDistancias();
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    public Map<Vertice, Vertice> predecessoresEmLargura(Grafo g, Vertice inicial) {
        try {
            BuscaEmLargura bfs = new BuscaEmLargura();
            bfs.executar(g, inicial);
            return bfs.getPredecessores();
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

}
