package methods;

import java.util.*;

import interfaces.*;
import models.*;

public class BuscaEmProfundidade {
    private Map<Vertice, Integer> tempo_descoberta;
    private Map<Vertice, Integer> tempo_finalizacao;
    private Map<Vertice, String> cores;
    private int tempo;
    private List<Aresta> aresta_arvore;
    private List<Aresta> aresta_retorno;
    private List<Aresta> aresta_avanco;
    private List<Aresta> aresta_cruzamento;

    public BuscaEmProfundidade() {
        this.tempo_descoberta = new HashMap<>();
        this.tempo_finalizacao = new HashMap<>();
        this.cores = new HashMap<>();
        this.aresta_arvore = new ArrayList<>();
        this.aresta_retorno = new ArrayList<>();
        this.aresta_avanco = new ArrayList<>();
        this.aresta_cruzamento = new ArrayList<>();
    }

    public void executar(Grafo grafo, Vertice inicial) throws Exception{
        for (Vertice vertice : grafo.vertices()){
            cores.put(vertice, "BRANCO");
            tempo_descoberta.put(vertice, -1);
            tempo_finalizacao.put(vertice, -1);
        }

        tempo = 0;
        visitar(grafo, inicial);

        for (Vertice vertice : grafo.vertices()){
            if (cores.get(vertice).equals("BRANCO")){
                visitar(grafo, vertice);
            }
        }
    }

    private void visitar(Grafo grafo, Vertice u) throws Exception {
        cores.put(u, "CINZA");
        tempo_descoberta.put(u, ++tempo);
    
        for (Vertice v : grafo.adjacentesDe(u)) {
            double peso = grafo.arestasEntre(u, v).get(0).peso();
    
            if (cores.get(v).equals("BRANCO")) {
                aresta_arvore.add(new Aresta(u, v, peso));
                visitar(grafo, v);
            } else if (cores.get(v).equals("CINZA")) {
                aresta_retorno.add(new Aresta(u, v, peso));
            } else if (cores.get(v).equals("PRETO") && tempo_descoberta.get(u) < tempo_descoberta.get(v)) {
                aresta_avanco.add(new Aresta(u, v, peso));
            } else {
                aresta_cruzamento.add(new Aresta(u, v, peso));
            }
        }
    
        cores.put(u, "PRETO");
        tempo_finalizacao.put(u, ++tempo);
    }
    


    public Map<Vertice, Integer> getTempo_descoberta(){
        return tempo_descoberta;
    }

    public Map<Vertice, Integer> getTempo_finalizacao(){
        return tempo_finalizacao;
    }

    public List<Aresta> getAresta_arvore(){
        return aresta_arvore;
    }

    public List<Aresta> getAresta_retorno(){
        return aresta_retorno;
    }

    public List<Aresta> getAresta_avanco(){
        return aresta_avanco;
    }

    public List<Aresta> getAresta_cruzamento(){
        return aresta_cruzamento;
    }
}
