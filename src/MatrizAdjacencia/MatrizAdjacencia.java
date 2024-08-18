package MatrizAdjacencia;

import Lista.Lista;
import java.util.ArrayList;

public class MatrizAdjacencia {

    private final String[] vertices;
    private final int[][] matrizAdjacencia;

    public MatrizAdjacencia(String[] vertices, int[][] matrizAdjacencia) {
        this.vertices = vertices;
        this.matrizAdjacencia = matrizAdjacencia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("    ");
        for (String vertice : vertices) {
            sb.append(String.format("%4s", vertice));
        }
        sb.append("\n");

        sb.append("   ");
        sb.append("----".repeat(vertices.length));
        sb.append("\n");

        for (int i = 0; i < matrizAdjacencia.length; i++) {
            sb.append(String.format("%s | ", vertices[i]));
            for (int j = 0; j < matrizAdjacencia[i].length; j++) {
                sb.append(String.format("%4d", matrizAdjacencia[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public ArrayList<Lista> transformarEmLista() {
        ArrayList<Lista> listaAdjacencia = new ArrayList<>();

        for (int i = 0; i < vertices.length; i++) {
            Lista lista = new Lista();
            for (int j = 0; j < vertices.length; j++) {
                if (matrizAdjacencia[i][j] == 1) {
                    if (lista.getInicio() == null) {
                        lista.setInicio(vertices[i].charAt(0));
                    }
                    lista.inserir(vertices[j].charAt(0));
                }
            }
            listaAdjacencia.add(lista);
        }

        return listaAdjacencia;
    }

    private boolean ehSimples(){
        for (int i = 0; i<matrizAdjacencia.length; i++){
            if(matrizAdjacencia[i][i] == 1){
                return false;
            }
        }
        return true;
    }

    private boolean ehCompleto(){
        int tamanho = matrizAdjacencia.length;
        if (!ehSimples()){
            return false;
        }
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j <tamanho; j++) {
                if(i!=j && matrizAdjacencia[i][j] != 1)
                    return false;
            }
        }
        return true;
    }

    private boolean ehRegularOrigem(){
        int soma = 0, comparacao = -1;
        for (int[] ints : matrizAdjacencia) {
            for (int j = 0; j < matrizAdjacencia.length; j++) {
                soma += ints[j];
            }
            if (comparacao == -1) {
                comparacao = soma;
            } else {
                if (soma != comparacao) {
                    return false;
                }
            }
            soma = 0;
        }
        return true;
    }

    private boolean ehRegularDestino(){
        int soma = 0, comparacao = -1;
        for (int i = 0; i < matrizAdjacencia.length; i++) {
            for (int[] ints : matrizAdjacencia) {
                soma += ints[i];
            }
            if (comparacao == -1) {
                comparacao = soma;
            } else {
                if (soma != comparacao) {
                    return false;
                }
            }
            soma = 0;
        }
        return true;
    }

    private boolean ehGrafo(){
        for (int i = 0; i < matrizAdjacencia.length; i++) {
            for (int j = 0; j < matrizAdjacencia.length; j++) {
                if(matrizAdjacencia[i][j] == 1){
                    if(matrizAdjacencia[j][i] == 0)
                        return false;
                }
            }
        }
        return true;
    }

    public void analise(){
        System.out.println("Análises: ");
        System.out.println("-------------------------------");
        System.out.println("É " + (ehGrafo() ? "grafo" : "dígrafo"));
        System.out.println("É simples: " + (ehSimples() ? "sim" : "não"));
        System.out.println("É completo: " + (ehCompleto() ? "sim" : "não"));
        if(ehGrafo()){
            System.out.println("É regular: " + (ehRegularOrigem() && ehRegularDestino() ? "sim" : "não"));
        }
        else {
            System.out.println("É regular de origem: " + (ehRegularOrigem() ? "sim" : "não"));
            System.out.println("É regular de destino: " + (ehRegularDestino() ? "sim" : "não"));
        }
    }
}
