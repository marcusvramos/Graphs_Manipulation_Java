package Arquivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
public class Arquivo {
    public static Registro lerArquivo(String caminhoArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linhaVertices = br.readLine();
            if (linhaVertices == null) {
                throw new IOException("O arquivo está vazio ou a primeira linha está faltando.");
            }
            String[] vertices = linhaVertices.split(",");

            String linha;
            int numVertices = vertices.length;
            int[][] matrizAdjacencia = new int[numVertices][numVertices];
            int i = 0;
            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(",");
                if (valores.length != numVertices) {
                    throw new IOException("O número de colunas na matriz de adjacência não corresponde ao número de vértices.");
                }
                for (int j = 0; j < numVertices; j++) {
                    matrizAdjacencia[i][j] = Integer.parseInt(valores[j].trim());
                }
                i++;
            }

            if (i != numVertices) {
                throw new IOException("O número de linhas na matriz de adjacência não corresponde ao número de vértices.");
            }

            return new Registro(vertices, matrizAdjacencia);
        }
    }
}