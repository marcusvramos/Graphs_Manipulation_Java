package Lista;

public class Lista {
    private No inicio;

    public Lista() {
        this.inicio = null;
    }

    public void inserir(char info) {
        No novoNo = new No(info);
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

    public void imprimir() {
        No atual = inicio;
        while (atual != null) {
            System.out.print(atual.getInfo());
            if (atual.getProx() != null) {
                System.out.print(" -> ");
            }
            atual = atual.getProx();
        }
        System.out.println();
    }
}
