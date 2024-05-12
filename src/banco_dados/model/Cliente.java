package banco_dados.model;

public class Cliente {

    private int idCliente;
    private String nome;
    private int idade;
    private String sexo;
    private Cidade idCidade;

    public Cliente() {

    }

    public Cliente(int idCliente, String nome, int idade, String sexo, Cidade idCidade) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.idCidade = idCidade;
    }

    @Override
    public String toString() {
        return getIdCliente() + " - " + getNome() + " - " + getIdade() + " - " + getSexo() + " - " + getCidade();
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Cidade getCidade() {
        return idCidade;
    }

    public void setCidade(Cidade cidade) {
        this.idCidade = cidade;
    }



}
