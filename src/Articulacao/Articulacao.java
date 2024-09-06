package Articulacao;

import MatrizAdjacencia.MatrizAdjacencia;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Articulacao {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private final int[] prenum;
    private final int[] menor;
    private final boolean[] visitado;
    private final boolean[] ehArticulacao;
    private final String[] vertices;
    private int contador;
    private final List<Integer>[] adj;
    private final Map<String, List<String>> arvoreDFS;
    private final List<String> arestasRetorno; // pontilhadas

    public Articulacao(MatrizAdjacencia matriz) {
        int n = matriz.getVertices().length;
        prenum = new int[n];
        menor = new int[n];
        visitado = new boolean[n];
        ehArticulacao = new boolean[n];
        vertices = matriz.getVertices();
        adj = new ArrayList[n];
        arvoreDFS = new HashMap<>();
        arestasRetorno = new ArrayList<>();
        contador = 0;

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        // Preenche a lista de adjacência a partir da matriz de adjacência
        int[][] matrizAdj = matriz.getMatrizAdjacencia();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrizAdj[i][j] != 0) {
                    adj[i].add(j);
                }
            }
        }

        for (int v = 0; v < n; v++) {
            if (!visitado[v]) {
                arvoreDFS.put(vertices[v].toUpperCase(), new ArrayList<>());
                buscaEmProfundidade(v, -1);
            }
        }

        exibirArvoreDFS();
        exibirTabelaResultados(n);
    }

    private void buscaEmProfundidade(int v, int pai) {
        visitado[v] = true;
        prenum[v] = menor[v] = contador++;
        int filhos = 0;
        boolean ehPontoDeArticulacao = false;

       // Se não for raiz eu adiciono na árvore DFS
        if (pai != -1) {
            String paiStr = vertices[pai].toUpperCase();
            String verticeAtual = vertices[v].toUpperCase();

            // Verifica se a lista de filhos do pai já existe, se não, inicializa
            arvoreDFS.putIfAbsent(paiStr, new ArrayList<>());
            arvoreDFS.get(paiStr).add(verticeAtual);
        }

        for (int w : adj[v]) {
            if (!visitado[w]) {
                filhos++;
                buscaEmProfundidade(w, v);

                // Atualiza menor[v] com o menor valor entre menor[v] e menor[w]
                menor[v] = Math.min(menor[v], menor[w]);

                if (pai != -1 && menor[w] >= prenum[v]) {
                    ehPontoDeArticulacao = true;
                }
            } else if (w != pai) { // Se w já foi visitado e não é o pai de v
                String arestaRetorno = vertices[v].toUpperCase() + " -> " + vertices[w].toUpperCase();
                arestasRetorno.add(arestaRetorno);

                // Atualiza o menor valor
                menor[v] = Math.min(menor[v], prenum[w]);
            }
        }

        // Verifica se v é um ponto de articulação
        if ((pai == -1 && filhos > 1) || (pai != -1 && ehPontoDeArticulacao)) {
            ehArticulacao[v] = true;
        }
    }

    private void exibirTabelaResultados(int n) {
        System.out.println("\n" + ANSI_CYAN + "Tabela de Resultados:" + ANSI_RESET);
        System.out.printf("%-8s| %-8s| %-8s| %-24s\n", "Vértice", "Prenum", "Menor", "Ponto de Articulação");
        System.out.println("--------|---------|---------|------------------------");

        boolean temArticulacao = false;

        for (int i = 0; i < n; i++) {
            String ehPontoDeArticulacao = ehArticulacao[i] ? ANSI_RED + "Sim" + ANSI_RESET : ANSI_GREEN + "Não" + ANSI_RESET;
            if (ehArticulacao[i]) {
                temArticulacao = true;
            }
            System.out.printf(ANSI_RESET + "%-8s| %-8d| %-8d| %-24s\n" + ANSI_RESET, vertices[i].toUpperCase(), prenum[i] + 1, menor[i] + 1, ehPontoDeArticulacao);
        }

        if (!temArticulacao) {
            System.out.println(ANSI_GREEN + "\nO Grafo é Biconexo: não há pontos de articulação!" + ANSI_RESET);
        }
    }

    private void exibirArvoreDFS() {
        System.out.println("\n" + ANSI_CYAN + "Árvore de Busca em Profundidade (DFS):" + ANSI_RESET);
        for (String pai : arvoreDFS.keySet()) {
            List<String> filhos = arvoreDFS.get(pai);
            System.out.println(ANSI_GREEN + pai + ANSI_RESET + " -> " + filhos);
        }

        if (!arestasRetorno.isEmpty()) {
            System.out.println("\n" + ANSI_YELLOW + "Arestas de Retorno (linhas pontilhadas):" + ANSI_RESET);
            for (String aresta : arestasRetorno) {
                System.out.println(ANSI_YELLOW + aresta + ANSI_RESET);
            }
        }
    }
}
