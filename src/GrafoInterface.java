import util.*;
import methods.*;
import models.Aresta;
import models.Vertice;
import interfaces.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.List;

public class GrafoInterface extends JFrame {

    private JTextField caminhoArquivo;
    private JComboBox<String> estruturaCombo;
    private JComboBox<String> buscaCombo;
    private JButton carregarArquivo;
    private JButton executar;
    private JTextArea resultado;
    private JTextField verticeInicialField;
    private JTextField verticeFinalField;

    private Grafo grafoCarregado;

    public GrafoInterface() {
        setTitle("Interface de Grafo");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    
        JPanel painelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbcSuperior = new GridBagConstraints();
        gbcSuperior.insets = new Insets(5, 5, 5, 5);
        gbcSuperior.fill = GridBagConstraints.HORIZONTAL;
    
        caminhoArquivo = new JTextField(30);
    
        JButton selecionarArquivo = new JButton("Selecionar Arquivo");
        selecionarArquivo.addActionListener(new SelecionarArquivoListener());
    
        gbcSuperior.gridx = 0;
        gbcSuperior.gridy = 0;
        gbcSuperior.weightx = 1.0;
        gbcSuperior.anchor = GridBagConstraints.WEST;
        painelSuperior.add(new JLabel("Caminho do arquivo:"), gbcSuperior);
    
        gbcSuperior.gridx = 1;
        gbcSuperior.gridy = 0;
        gbcSuperior.weightx = 2.0;
        painelSuperior.add(caminhoArquivo, gbcSuperior);
    
        gbcSuperior.gridx = 2;
        gbcSuperior.gridy = 0;
        gbcSuperior.weightx = 0.5;
        painelSuperior.add(selecionarArquivo, gbcSuperior);
    
        add(painelSuperior, BorderLayout.NORTH);
    
        JPanel painelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        gbc.gridx = 0; gbc.gridy = 0;
        painelCentral.add(new JLabel("Estrutura de Armazenamento:"), gbc);
        gbc.gridx = 1;
        estruturaCombo = new JComboBox<>(new String[] {
            "Matriz de Adjacência",
            "Matriz de Incidência",
            "Lista de Adjacência"
        });
        painelCentral.add(estruturaCombo, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        painelCentral.add(new JLabel("Tipo de Busca:"), gbc);
        gbc.gridx = 1;
        buscaCombo = new JComboBox<>(new String[] {
            "Busca em Profundidade",
            "Busca em Largura",
            "Árvore Geradora Mínima",
            "Caminho Mínimo"
        });
        painelCentral.add(buscaCombo, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2;
        painelCentral.add(new JLabel("Vértice Inicial:"), gbc);
        gbc.gridx = 1;
        verticeInicialField = new JTextField();
        painelCentral.add(verticeInicialField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 3;
        painelCentral.add(new JLabel("Vértice Final:"), gbc);
        gbc.gridx = 1;
        verticeFinalField = new JTextField();
        painelCentral.add(verticeFinalField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 4;
        carregarArquivo = new JButton("Carregar Grafo");
        painelCentral.add(carregarArquivo, gbc);
    
        gbc.gridx = 1;
        executar = new JButton("Executar Busca");
        painelCentral.add(executar, gbc);
    
        add(painelCentral, BorderLayout.CENTER);
    
        resultado = new JTextArea();
        resultado.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultado);
        add(scrollPane, BorderLayout.SOUTH);
    
        carregarArquivo.addActionListener(new CarregarGrafoListener());
        executar.addActionListener(new ExecutarBuscaListener());
    }
    
    

    private class SelecionarArquivoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                caminhoArquivo.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    private class CarregarGrafoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String caminho = caminhoArquivo.getText();
            String estrutura = (String) estruturaCombo.getSelectedItem();
            try {
                TipoDeRepresentacao tipo = switch (estrutura) {
                    case "Matriz de Adjacência" -> TipoDeRepresentacao.MATRIZ_DE_ADJACENCIA;
                    case "Matriz de Incidência" -> TipoDeRepresentacao.MATRIZ_DE_INCIDENCIA;
                    case "Lista de Adjacência" -> TipoDeRepresentacao.LISTA_DE_ADJACENCIA;
                    default -> throw new IllegalArgumentException("Tipo desconhecido");
                };

                grafoCarregado = new AlgoritmosImplementados().carregarGrafo(caminho, tipo);
                resultado.setText("Grafo carregado com sucesso usando " + estrutura);
                resultado.revalidate();
                resultado.repaint();

            } catch (Exception ex) {
                resultado.setText("Erro ao carregar o grafo: " + ex.getMessage());
            }
        }
    }

    private class ExecutarBuscaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (grafoCarregado == null) {
                resultado.setText("Carregue um grafo antes de executar a busca.");
                return;
            }
    
            String busca = (String) buscaCombo.getSelectedItem();
            String verticeInicialTexto = verticeInicialField.getText();
    
            Vertice verticeInicial = null;
            try {
                if (!busca.equals("Árvore Geradora Mínima")) {
                    int idInicial = Integer.parseInt(verticeInicialTexto);
                    verticeInicial = grafoCarregado.vertices()
                            .stream()
                            .filter(v -> v.getId() == idInicial)
                            .findFirst()
                            .orElseThrow(() -> new Exception("Vértice inicial não encontrado."));
                }
            } catch (Exception ex) {
                resultado.setText("Erro: " + ex.getMessage());
                return;
            }
    
            try {
                switch (busca) {
                    case "Busca em Profundidade": {
                        BuscaEmProfundidade dfs = new BuscaEmProfundidade();
                        dfs.executar(grafoCarregado, verticeInicial);
                        resultado.setText(
                                "Arestas de Árvore:\n" + formatarArestas(dfs.getAresta_arvore()) +
                                "\n\nArestas de Retorno:\n" + formatarArestas(dfs.getAresta_retorno()) +
                                "\n\nArestas de Avanço:\n" + formatarArestas(dfs.getAresta_avanco()) +
                                "\n\nArestas de Cruzamento:\n" + formatarArestas(dfs.getAresta_cruzamento()) +
                                "\n\nTempos de Descoberta:\n" + formatarMapas(dfs.getTempo_descoberta()) +
                                "\n\nTempos de Finalização:\n" + formatarMapas(dfs.getTempo_finalizacao()));
                                resultado.revalidate();
                                resultado.repaint();
                        break;
                    }
                    case "Busca em Largura": {
                        BuscaEmLargura bfs = new BuscaEmLargura();
                        bfs.executar(grafoCarregado, verticeInicial);
                        resultado.setText(
                                "Distâncias:\n" + formatarMapas(bfs.getDistancias()) +
                                "\n\nPredecessores:\n" + formatarMapas(bfs.getPredecessores()));
                                resultado.revalidate();
                                resultado.repaint();
                        break;
                    }
                    case "Árvore Geradora Mínima": {
                        try {
                            int idInicial = Integer.parseInt(verticeInicialField.getText());
                            Vertice inicial = grafoCarregado.vertices()
                                               .stream()
                                               .filter(v -> v.getId() == idInicial)
                                               .findFirst()
                                               .orElseThrow(() -> new Exception("Vértice inicial não encontrado."));
                    
                            AlgoritmosImplementados alg = new AlgoritmosImplementados();
                            Collection<Aresta> arestasAGM = alg.agmUsandoKruskall(grafoCarregado, inicial);
                    
                            StringBuilder resultadoAGM = new StringBuilder("Arestas da AGM:\n");
                            for (Aresta aresta : arestasAGM) {
                                resultadoAGM.append(aresta.origem().getId())
                                            .append(" -> ")
                                            .append(aresta.destino().getId())
                                            .append(" (peso: ")
                                            .append(aresta.peso())
                                            .append(")\n");
                            }
                            resultado.setText(resultadoAGM.toString());
                            resultado.revalidate();
                            resultado.repaint();
                        } catch (Exception ex) {
                            resultado.setText("Erro ao calcular a AGM: " + ex.getMessage());
                        }
                        break;
                    }
                    case "Caminho Mínimo": {
                        try {
                            int idInicial = Integer.parseInt(verticeInicialField.getText());
                            int idFinal = Integer.parseInt(verticeFinalField.getText());
                    
                            Vertice origem = grafoCarregado.vertices()
                                            .stream()
                                            .filter(v -> v.getId() == idInicial)
                                            .findFirst()
                                            .orElseThrow(() -> new Exception("Vértice inicial não encontrado."));
                    
                            Vertice destino = grafoCarregado.vertices()
                                            .stream()
                                            .filter(v -> v.getId() == idFinal)
                                            .findFirst()
                                            .orElseThrow(() -> new Exception("Vértice final não encontrado."));
                    
                            CaminhoMinimo cm = new CaminhoMinimo();
                            List<Aresta> caminho = cm.calcularCaminhoMinimo(grafoCarregado, origem, destino);
                    
                            StringBuilder caminhoResultado = new StringBuilder("Caminho Mínimo:\n");
                            for (Aresta aresta : caminho) {
                                caminhoResultado.append(aresta.origem().getId())
                                                 .append(" -> ")
                                                 .append(aresta.destino().getId())
                                                 .append(" (peso: ")
                                                 .append(aresta.peso())
                                                 .append(")\n");
                            }
                            resultado.setText(caminhoResultado.toString());
                        } catch (Exception ex) {
                            resultado.setText("Erro ao calcular o caminho mínimo: " + ex.getMessage());
                        }
                        break;
                    }
                    
                    default:
                        throw new IllegalArgumentException("Busca desconhecida");
                }
            } catch (Exception ex) {
                resultado.setText("Erro ao executar a busca: " + ex.getMessage());
            }
        }
    }
    

    private String formatarArestas(Collection<Aresta> arestas) {
        if (arestas.isEmpty()) {
            return "Nenhuma aresta.";
        }

        StringBuilder sb = new StringBuilder();
        for (Aresta aresta : arestas) {
            sb.append(aresta.toString()).append("\n");
        }
        return sb.toString();
    }

    private String formatarMapas(Map<Vertice, ?> mapa) {
        StringBuilder sb = new StringBuilder();
        mapa.entrySet().stream()
                .sorted(Map.Entry.comparingByKey((v1, v2) -> Integer.compare(v1.getId(), v2.getId())))
                .forEach(entry -> {
                    sb.append("Vértice ").append(entry.getKey().getId())
                            .append(" -> ");
                    if (entry.getValue() != null) {
                        sb.append(entry.getValue().toString());
                    } else {
                        sb.append("nenhum dado");
                    }
                    sb.append("\n");
                });
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GrafoInterface gui = new GrafoInterface();
            gui.setVisible(true);
        });
    }
}
