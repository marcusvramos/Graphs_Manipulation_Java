package Articulacao;

import MatrizAdjacencia.MatrizAdjacencia;

import java.util.ArrayList;
import java.util.List;

public class Articulacao {
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
        adj = new List[n];
        contador = 0;

        // inicia cada lista de adjacência
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        // pega matriz do grafo
        int[][] matrizAdj = matriz.getMatrizAdjacencia();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Verifica se i ta ligado com j
                if (matrizAdj[i][j] != 0) {
                    // adiciona na lista
                    adj[i].add(j);
                }
            }
        }

        for (int v = 0; v < n; v++) {
            if (!visitado[v]) {
                // Inicia a DFS a partir de v, com -1 indicando que v é raiz
                dfs(v, -1);
            }
        }
    }

    private void dfs(int v, int pai) {
        visitado[v] = true;
        prenum[v] = menor[v] = contador++;
        int filhos = 0;
        boolean ehPontoDeArticulacao = false;

        // Percorrer todos os vértices adjacentes de v
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
            pontosDeArticulacao.add(vertices[v]);
        }
    }

    public List<String> getPontosDeArticulacao() {
        return pontosDeArticulacao;
    }
}
