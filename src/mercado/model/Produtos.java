package mercado.model;

public class Produtos {

    private int id;
    private String nome;
    private int estoque;
    private double preco;

    public Produtos() {
    }

    public Produtos(int id,String nome, int estoque, double preco) {
        this.id = id;
        this.nome = nome;
        this.estoque = estoque;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return getId() + ", " + getNome() + ", " + getEstoque() + ", R$" + getPreco();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
