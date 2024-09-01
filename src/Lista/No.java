package Lista;

public class No {
    private char info; // Para qual vértice apont

    private int valor;
    private No prox;

    public No(char info, int valor) {
        this.info = info;
        this.valor = valor;
        this.prox = null;
    }

    public char getInfo() {
        return info;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
