package model;

public class Produto {
    private String nome;
    private String descricao;
    private double preco;
    private int quantidade;

    public Produto(String nome, String descricao, double preco, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() {
        return preco;
    }
    public String exibirPreco() {
        return String.format("%.2f", getPreco());
    }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}