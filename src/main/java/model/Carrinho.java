package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")  // A coluna no banco de dados que faz referência ao usuário
    private Usuario usuario;  // A relação com a classe Usuario

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemCarrinho> itens;

    public Carrinho() {
        this.itens = new ArrayList<>();
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao")
    private Date dataCriacao = new Date(); // ou defina via construtor


    public void adicionar(Produto produto, int quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getNome().equals(produto.getNome())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }
        ItemCarrinho novoItem = new ItemCarrinho(produto, this, quantidade);
        itens.add(novoItem);
    }

    public void remover(String nomeProduto) {
        itens.removeIf(item -> item.getProduto().getNome().equals(nomeProduto));
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public double getTotal() {
        return itens.stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
    }

    public String exibirTotal() {
        double total = getTotal();
        return String.format("%.2f", total);
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
