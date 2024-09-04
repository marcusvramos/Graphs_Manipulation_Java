package Articulacao;

import MatrizAdjacencia.MatrizAdjacencia;
import java.util.ArrayList;
import java.util.List;

public class Articulacao {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private final int[] prenum;
    private final int[] menor;
    private final boolean[] visitado;
    private final boolean[] ehArticulacao;
    private final String[] vertices;
    private int contador;
    private final List<Integer>[] adj;

    public Articulacao(MatrizAdjacencia matriz) {
        int n = matriz.getVertices().length;
        prenum = new int[n];
        menor = new int[n];
        visitado = new boolean[n];
        ehArticulacao = new boolean[n];
        vertices = matriz.getVertices();
        adj = new ArrayList[n];
        contador = 0;

        // Inicializa cada lista de adjacência
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
                dfs(v, -1);
            }
        }

        exibirTabelaResultados(n);
    }

    private void dfs(int v, int pai) {
        visitado[v] = true;
        prenum[v] = menor[v] = contador++;
        int filhos = 0;
        boolean ehPontoDeArticulacao = false;

        for (int w : adj[v]) {
            if (!visitado[w]) {
                filhos++;
                dfs(w, v);

                // Atualiza menor[v] com o menor valor entre menor[v] e menor[w]
                menor[v] = Math.min(menor[v], menor[w]);

                if (pai != -1 && menor[w] >= prenum[v]) {
                    ehPontoDeArticulacao = true;
                }
            } else if (w != pai) { // Se w já foi visitado e não é o pai de v
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
}
