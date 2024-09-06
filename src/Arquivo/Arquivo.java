package Arquivo;

import Lista.Lista;
import MatrizAdjacencia.MatrizAdjacencia;

import java.io.*;
import java.util.ArrayList;

public class Arquivo {
    private final BufferedReader br;
    private String[] vertices;

    public Arquivo(String caminho) throws FileNotFoundException {
        File file = new File(caminho);
        if (!file.exists()) {
            throw new RuntimeException("O arquivo não foi encontrado.");
        }
        FileInputStream fis = new FileInputStream(file);
        this.br = new BufferedReader(new InputStreamReader(fis));
    }

    public MatrizAdjacencia lerArquivoParaMatriz() {
        try {
            vertices = lerVertices();
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

            return new MatrizAdjacencia(vertices, matrizAdjacencia);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Lista> lerArquivoParaLista() throws IOException {
        vertices = lerVertices();
        int numVertices = vertices.length;
        int[][] matrizAdjacencia = lerMatrizAdjacencia(numVertices);

        ArrayList<Lista> listaAdjacencia = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            Lista lista = new Lista();
            lista.setInicio(vertices[i].charAt(0), matrizAdjacencia[i][i]);
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdjacencia[i][j] != 0) {
                    lista.inserir(vertices[j].charAt(0), matrizAdjacencia[i][j]);
                }
            }
            listaAdjacencia.add(lista);
        }

        return listaAdjacencia;
    }

    public String[] lerVertices() throws IOException {
        String linhaVertices = br.readLine();
        if (linhaVertices == null) {
            throw new IOException("O arquivo está vazio ou a primeira linha está faltando.");
        }
        vertices = linhaVertices.split(",");
        return vertices;
    }

    private int[][] lerMatrizAdjacencia(int numVertices) throws IOException {
        int[][] matrizAdjacencia = new int[numVertices][numVertices];
        String linha;
        int i = 0;
        while ((linha = br.readLine()) != null) {
            String[] valores = linha.split(",");
            for (int j = 0; j < numVertices; j++) {
                matrizAdjacencia[i][j] = Integer.parseInt(valores[j].trim());
            }
            i++;
        }
        return matrizAdjacencia;
    }
}