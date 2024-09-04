package Articulacao;

import MatrizAdjacencia.MatrizAdjacencia;

import java.util.ArrayList;
import java.util.List;

public class Articulacao {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private final int[] prenum;
    private final int[] menor;
    private final boolean[] visitado;
    private final List<String> pontosDeArticulacao;
    private final String[] vertices;
    private int contador;
    private final List<Integer>[] adj;

    public Articulacao(MatrizAdjacencia matriz) {
        int n = matriz.getVertices().length;
        prenum = new int[n];
        menor = new int[n];
        visitado = new boolean[n];
        pontosDeArticulacao = new ArrayList<>();
        vertices = matriz.getVertices();
        adj = new ArrayList[n];
        contador = 0;

        // Inicializa cada lista de adjacência
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        // Preenche a lista de adjacência a partir da matriz de adjacência
        int[][] matrizAdj = matriz.getMatrizAdjacencia();
        System.out.println(ANSI_CYAN + "Estrutura de conectividade inicial:" + ANSI_RESET);
        for (int i = 0; i < n; i++) {
            System.out.print(ANSI_PURPLE + "Vértice " + vertices[i].toUpperCase() + " está conectado a: " + ANSI_RESET);
            List<String> conexoes = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (matrizAdj[i][j] != 0) {
                    conexoes.add(vertices[j].toUpperCase());
                    adj[i].add(j);
                }
            }
            System.out.println(ANSI_YELLOW + conexoes + ANSI_RESET);
        }

        // Inicia a DFS para identificar os pontos críticos de conectividade
        for (int v = 0; v < n; v++) {
            if (!visitado[v]) {
                System.out.println(ANSI_GREEN + "\nIniciando DFS a partir do vértice " + vertices[v].toUpperCase() + ANSI_RESET);
                dfs(v, -1);
            }
        }

        // Exibe os pontos de articulação identificados
        if (pontosDeArticulacao.isEmpty()) {
            System.out.println(ANSI_GREEN + "\nO Grafo é BiConexo: não há pontos críticos de desconexão!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "\nPontos de Articulação identificados (nós de desconexão): " + pontosDeArticulacao + ANSI_RESET);
        }
    }

    private void dfs(int v, int pai) {
        visitado[v] = true;
        prenum[v] = menor[v] = contador++;
        int filhos = 0;
        boolean ehPontoDeArticulacao = false;

        System.out.println(ANSI_BLUE + "Visitando vértice: " + vertices[v].toUpperCase() + " (prenum=" + (prenum[v] + 1) + ", menor=" + (menor[v] + 1) + ")" + ANSI_RESET);

        for (int w : adj[v]) {
            if (!visitado[w]) {
                filhos++;
                dfs(w, v);

                // Atualiza menor[v] com o menor valor entre menor[v] e menor[w]
                menor[v] = Math.min(menor[v], menor[w]);
                System.out.println(ANSI_YELLOW + "Atualizado menor[" + vertices[v].toUpperCase() + "] = " + (menor[v] + 1) + ANSI_RESET);

                if (pai != -1 && menor[w] >= prenum[v]) {
                    ehPontoDeArticulacao = true;
                }
            } else if (w != pai) { // Se w já foi visitado e não é o pai de v
                menor[v] = Math.min(menor[v], prenum[w]);
                System.out.println(ANSI_YELLOW + "Aresta de retorno detectada para " + vertices[v].toUpperCase() + ": atualiza menor[" + vertices[v].toUpperCase() + "] = " + (menor[v] + 1) + ANSI_RESET);
            }
        }

        // Verifica se v é um ponto de articulação
        if ((pai == -1 && filhos > 1) || (pai != -1 && ehPontoDeArticulacao)) {
            pontosDeArticulacao.add(vertices[v].toUpperCase());
            System.out.println(ANSI_RED + "Vértice " + vertices[v].toUpperCase() + " é um ponto crítico de desconexão!" + ANSI_RESET);
        }
    }
}
