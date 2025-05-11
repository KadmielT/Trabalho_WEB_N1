package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "total")
    private double total;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data")
    private Date data;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ItemCarrinho> itens;

    public Pedido() {
        this.data = new Date();
    }

    public Pedido(Usuario usuario, List<ItemCarrinho> itens, double total) {
        this.usuario = usuario;
        this.itens = new ArrayList<>(itens);
        this.total = total;
        this.data = new Date();
        for (ItemCarrinho item : this.itens) {
            item.setPedido(this);
        }
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
        for (ItemCarrinho item : this.itens) {
            item.setPedido(this);
        }
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String exibirTotal() {
        return String.format("%.2f", total);
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
