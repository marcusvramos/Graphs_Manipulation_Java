package Lista;

public class Lista {
    private No inicio;

    public Lista() {
        this.inicio = null;
    }

    public void inserir(char info, int valor) {
        No novoNo = new No(info, valor);
        if (inicio == null) {
            inicio = novoNo;
        } else {
            No atual = inicio;
            while (atual.getProx() != null) {
                atual = atual.getProx();
            }
            atual.setProx(novoNo);
        }
    }

    public No getInicio() {
        return inicio;
    }

    public void setInicio(char info, int valor) {
        inicio = new No(info, valor);
    }

    public void imprimir() {
        No atual = inicio;
        while (atual != null) {
            System.out.print(atual.getInfo() + "(" + atual.getValor() + ")");
            if (atual.getProx() != null) {
                System.out.print(" -> ");
            }
            atual = atual.getProx();
        }
        System.out.println();
    }
}
