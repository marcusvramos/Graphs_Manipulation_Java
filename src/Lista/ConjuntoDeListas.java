package Lista;

import MatrizAdjacencia.MatrizAdjacencia;

import java.util.ArrayList;

public class ConjuntoDeListas {
    private final ArrayList<Lista> listas;
    private final String[] vertices;

    public ConjuntoDeListas(ArrayList<Lista> listas, String[] vertices) {
        this.listas = listas;
        this.vertices = vertices;
    }

    public MatrizAdjacencia transformarEmMatriz() {
        int numVertices = vertices.length;
        int[][] matrizAdjacencia = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            Lista lista = listas.get(i);
            No atual = lista.getInicio();
            while (atual.getProx() != null) {
                int j = encontrarIndiceVertice(atual.getProx().getInfo());
                matrizAdjacencia[i][j] = 1;
                atual = atual.getProx();
            }
        }

        return new MatrizAdjacencia(vertices, matrizAdjacencia);
    }

    private int encontrarIndiceVertice(char vertice) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].charAt(0) == vertice) {
                return i;
            }
        }
        throw new IllegalArgumentException("Vértice não encontrado: " + vertice);
    }

    private boolean ehSimples() {
        for (Lista lista : listas) {
            No atual = lista.getInicio();
            while (atual != null) {
                if (atual.getProx() != null && atual.getInfo() == atual.getProx().getInfo()) {
                    return false;
                }
                atual = atual.getProx();
            }
        }
        return true;
    }

    private boolean ehCompleto() {
        int numVertices = vertices.length;

        if (!ehSimples()) {
            return false;
        }

        for (int i = 0; i < numVertices; i++) {
            boolean[] conectados = new boolean[numVertices];
            Lista lista = listas.get(i);
            No atual = lista.getInicio();

            while (atual != null) {
                int j = encontrarIndiceVertice(atual.getInfo());
                conectados[j] = true;
                atual = atual.getProx();
            }

            for (int j = 0; j < numVertices; j++) {
                if (i != j && !conectados[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean ehRegularOrigem() {
        int grauPadrao = -1;
        for (Lista lista : listas) {
            int contador = 0;
            No atual = lista.getInicio();
            while (atual != null) {
                contador++;
                atual = atual.getProx();
            }
            if (grauPadrao == -1) {
                grauPadrao = contador;
            } else if (contador != grauPadrao) {
                return false;
            }
        }
        return true;
    }

    private boolean ehGrafo() {
        int numVertices = vertices.length;
        for (int i = 0; i < numVertices; i++) {
            Lista lista = listas.get(i);
            No atual = lista.getInicio();
            while (atual != null) {
                int j = encontrarIndiceVertice(atual.getInfo());
                if (!existeConexao(listas.get(j), vertices[i].charAt(0))) {
                    return false; // Dígrafo detectado
                }
                atual = atual.getProx();
            }
        }
        return true;
    }

    private boolean existeConexao(Lista lista, char vertice) {
        No atual = lista.getInicio();
        while (atual != null) {
            if (atual.getInfo() == vertice) {
                return true;
            }
            atual = atual.getProx();
        }
        return false;
    }

    public void analise() {
        System.out.println("Análises:");
        System.out.println("-------------------------------");
        System.out.println("É " + (ehGrafo() ? "grafo" : "dígrafo"));
        System.out.println("É simples: " + (ehSimples() ? "sim" : "não"));
        System.out.println("É completo: " + (ehCompleto() ? "sim" : "não"));
        System.out.println("É regular: " + (ehRegularOrigem() ? "sim" : "não"));
    }
}
