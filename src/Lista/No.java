package Lista;

public class No {
    private char info;
    private No prox;

    public No(char info) {
        this.info = info;
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
}
