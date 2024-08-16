package Arquivo;

import Lista.Lista;
import MatrizAdjacencia.MatrizAdjacencia;

import java.io.*;
import java.util.ArrayList;

public class Arquivo {
        private BufferedReader br;
        private String[] vertices;

        public Arquivo(String caminho) throws FileNotFoundException {
            this.br = new BufferedReader(new FileReader(caminho));
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
            MatrizAdjacencia matrizAdjacencia = lerArquivoParaMatriz();
            int[][] matrizLida = matrizAdjacencia.getMatrizAdjacencia();
            int numVertices = vertices.length;

            ArrayList<Lista> listaAdjacencia = new ArrayList<>();
            for (int i = 0; i < numVertices; i++) {
                listaAdjacencia.add(new Lista());
            }

            for (int iVertice = 0; iVertice < numVertices; iVertice++) {
                for (int jVertice = 0; jVertice < numVertices; jVertice++) {
                    if (matrizLida[iVertice][jVertice] == 1) {
                        listaAdjacencia.get(iVertice).inserir(vertices[jVertice].charAt(0));
                    }
                }
            }

            return listaAdjacencia;
        }

        private String[] lerVertices() throws IOException {
            String linhaVertices = br.readLine();
            if (linhaVertices == null) {
                throw new IOException("O arquivo está vazio ou a primeira linha está faltando.");
            }
            vertices = linhaVertices.split(",");
            return vertices;
        }
}