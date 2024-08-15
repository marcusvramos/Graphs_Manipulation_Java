import Arquivo.Arquivo;
import Arquivo.Registro;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Registro registro = Arquivo.lerArquivo("C:/Users/Aluno/IdeaProjects/Grafos/src/matriz.txt");
        System.out.println("Matriz de Adjacência");
        System.out.println(registro.toString());

        registro.analise();

    }
}