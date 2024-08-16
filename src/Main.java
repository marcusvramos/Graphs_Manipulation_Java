import Arquivo.Arquivo;
import Lista.Lista;
import MatrizAdjacencia.MatrizAdjacencia;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Arquivo arquivo = new Arquivo("D:/graphs_adjacency-matrix-master/graphs_adjacency-matrix-master/src/matriz.txt");
//        MatrizAdjacencia matriz = arquivo.lerArquivoParaMatriz();
//        System.out.println("Matriz de Adjacência");
//        System.out.println(matriz.toString());
//
//        matriz.analise();

        ArrayList<Lista> lista = arquivo.lerArquivoParaLista();
        lista.forEach((l) -> l.imprimir());
    }
}