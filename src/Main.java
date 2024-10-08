import Arquivo.Arquivo;
import Articulacao.Articulacao;
import Lista.ConjuntoDeListas;
import Lista.Lista;
import MatrizAdjacencia.MatrizAdjacencia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Bem-vindo ao leitor de grafos!");
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            int escolha = lerEscolhaDoUsuario(scanner);

            switch (escolha) {
                case 1:
                    MatrizAdjacencia matriz = exibirMatrizDeAdjacencia(reabrirArquivo());
                    manipularMatriz(matriz, scanner);
                    break;
                case 2:
                    ArrayList<Lista> lista = exibirListaDeAdjacencia(reabrirArquivo());
                    manipularLista(lista, reabrirArquivo().lerVertices(), scanner);
                    break;
                case 3:
                    continuar = false;
                    System.out.println("Saindo do programa. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        }

        scanner.close();
    }

    private static Arquivo reabrirArquivo() throws FileNotFoundException {
        return new Arquivo(
            "/home/marcus/Documentos/Faculdade/Teoria dos Grafos/Trabalho de Grafos - MarcusRamos e GuilhermeCamargo/graphs_adjacency-matrix/src/resources/matriz.txt"
        );
    }

    private static int lerEscolhaDoUsuario(Scanner scanner) {
        System.out.println("\nSelecione uma opção:");
        System.out.println("(1) Ler grafo como Matriz de Adjacência");
        System.out.println("(2) Ler grafo como Lista de Adjacência");
        System.out.println("(3) Sair");
        System.out.print("Digite 1, 2 ou 3: ");
        return scanner.nextInt();
    }

    private static MatrizAdjacencia exibirMatrizDeAdjacencia(Arquivo arquivo) {
        MatrizAdjacencia matriz = arquivo.lerArquivoParaMatriz();
        System.out.println("Matriz de Adjacência:");
        System.out.println(matriz.toString());
        return matriz;
    }

    private static void manipularMatriz(MatrizAdjacencia matriz, Scanner scanner) {
        int escolha = lerEscolhaMatriz(scanner);
        switch (escolha) {
            case 1:
                analisarMatriz(matriz);
                break;
            case 2:
                ArrayList<Lista> lista = matriz.transformarEmLista();
                System.out.println("Lista de Adjacência gerada a partir da Matriz:");
                lista.forEach(Lista::imprimir);
                break;
            case 3:
                identificarArticulacaoMatriz(matriz);
                break;
            default:
                System.out.println("Opção inválida. Retornando ao menu principal.");
        }
    }

    private static int lerEscolhaMatriz(Scanner scanner) {
        System.out.print("Você deseja\n(1) Analisar a Matriz\n(2) Transformar a Matriz em Lista de Adjacência\n(3) Identificar Pontos de Articulação\nDigite 1, 2 ou 3: ");
        return scanner.nextInt();
    }

    private static ArrayList<Lista> exibirListaDeAdjacencia(Arquivo arquivo) throws IOException {
        ArrayList<Lista> lista = arquivo.lerArquivoParaLista();
        System.out.println("Lista de Adjacência:");
        lista.forEach(Lista::imprimir);
        return lista;
    }

    private static void manipularLista(ArrayList<Lista> lista, String[] vertices, Scanner scanner) {
        int escolha = lerEscolhaLista(scanner);
        ConjuntoDeListas conjunto = new ConjuntoDeListas(lista, vertices);
        switch (escolha) {
            case 1:
                analisarLista(conjunto);
                break;
            case 2:
                MatrizAdjacencia matriz = conjunto.transformarEmMatriz();
                System.out.println("Matriz de Adjacência gerada a partir da Lista:");
                System.out.println(matriz.toString());
                break;
            case 3:
                identificarArticulacaoLista(conjunto);
                break;
            default:
                System.out.println("Opção inválida. Retornando ao menu principal.");
        }
    }

    private static int lerEscolhaLista(Scanner scanner) {
        System.out.print("Você deseja:\n(1) Analisar a Lista\n(2) Transformar a Lista em Matriz de Adjacência\n(3) Identificar Pontos de Articulação\nDigite 1, 2 ou 3: ");
        return scanner.nextInt();
    }

    private static void analisarMatriz(MatrizAdjacencia matriz) {
        System.out.println("Análise da Matriz de Adjacência:");
        matriz.analise();
    }

    private static void analisarLista(ConjuntoDeListas conjunto) {
        System.out.println("Análise da Lista de Adjacência:");
        conjunto.analise();
    }

    private static void identificarArticulacaoMatriz(MatrizAdjacencia matriz) {
        Articulacao articulacao = new Articulacao(matriz);
    }

    private static void identificarArticulacaoLista(ConjuntoDeListas conjunto) {
        MatrizAdjacencia matriz = conjunto.transformarEmMatriz();
        identificarArticulacaoMatriz(matriz);
    }
}
